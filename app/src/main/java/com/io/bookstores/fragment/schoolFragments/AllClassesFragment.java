package com.io.bookstores.fragment.schoolFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.schoolAdapter.AllSchoolsRvAdapter;
import com.io.bookstores.adapter.schoolAdapter.ClassGroupRvAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.model.classModel.ClassDataModel;
import com.io.bookstores.model.classModel.ClassResponseModel;
import com.io.bookstores.model.schoolModel.GetAllSchollResponseModel;
import com.io.bookstores.model.schoolModel.GetAllSchoolDataModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;


public class AllClassesFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private RecyclerView rvAllClasses;
    private ClassGroupRvAdapter adapter;
    private ImageView iv_back;
    private TextView notdata;
    private userOnlineInfo user;
    private SearchView search_all_classes;
    private NewProgressBar dialog;
    private List<ClassDataModel> listData;
    private ItemClickListner itemClickListner;

    public AllClassesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_school_categories, container, false);
        intializeAllViews(view);
        bindListner();
        startWorking();
        return view;
    }

    /*-------------------------------------- intialize all views tha are used in this fragment -------------------------------*/

    private void intializeAllViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        dialog = new NewProgressBar(activity);
        listData = new ArrayList<>();
        itemClickListner = (ItemClickListner) getActivity();
        rvAllClasses = view.findViewById(R.id.rv_all_classes);
        iv_back = view.findViewById(R.id.iv_back);
        notdata = view.findViewById(R.id.notdata);
        search_all_classes = view.findViewById(R.id.search_all_classes);
        rvAllClasses.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
    }

    /*---------------------------------------- bind all views that are used in this fragment --------------------------------*/

    private void bindListner() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                itemClickListner.onClick(6);
                return;
        }

    }

    /*------------------------------------------------------- start Working ---------------------------------------------------*/

    private void startWorking() {
        allClassGroupApi();
        searchViewSetUp();
    }


    /*-------------------------------------------------- get all classes from Api --------------------------------------------*/
    private void allClassGroupApi() {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.getclassApi(getActivity(), Config.Url.getclass,
                    new FutureCallback<ClassResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, ClassResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {

                                if (result.isStatus()) {
                                    dialog.dismiss();
                                    setUpofClassesRv(result.getData());
                                }
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*------------------------------------------------ all classes data set into Recycler View ----------------------------------*/

    private void setUpofClassesRv(List<ClassDataModel> data) {
        if (data.isEmpty()) {
            notdata.setVisibility(View.VISIBLE);
            rvAllClasses.setVisibility(View.VISIBLE);
        } else {
            adapter = new ClassGroupRvAdapter(activity, data);
            rvAllClasses.setAdapter(adapter);
            listData = data;
        }

    }

    /*---------------------------------------------------- set up of search view --------------------------------------------------*/

    private void searchViewSetUp() {
        search_all_classes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (listData == null || listData.isEmpty()) {

                } else {
                    List<ClassDataModel> newlist = new ArrayList<>();
                    for (ClassDataModel productList : listData) {
                        String name = productList.getName().toLowerCase();
                        if (name.contains(s))
                            newlist.add(productList);
                    }
                    adapter.setFilter(newlist);

                }
                return true;
            }

        });

    }
}
