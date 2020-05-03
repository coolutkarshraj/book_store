package com.io.bookstores.fragment.schoolFragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.schoolAdapter.ClassCategoryRvAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.classModel.ClassCategoryDataModel;
import com.io.bookstores.model.classModel.ClassCategoryModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;


public class ClassCategoryFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rv_school_Deatial;
    private ClassCategoryRvAdapter adapter;
    private Activity activity;
    private ImageView iv_back;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private TextView notdata;
    private SearchView searchView;
    private LocalStorage localStorage;
    private ItemClickListner itemClickListner;
    private List<ClassCategoryDataModel> listData;

    public ClassCategoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_category, container, false);
        intializeViews(view);
        bindListner();
        startWorking();
        return view;
    }




    /*------------------------------------------------------ intialize Views --------------------------------------------------*/

    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        dialog = new NewProgressBar(activity);
        localStorage = new LocalStorage(activity);
        listData = new ArrayList<>();
        itemClickListner = (ItemClickListner) getActivity();
        rv_school_Deatial = view.findViewById(R.id.rv_school_Deatial);
        notdata = view.findViewById(R.id.notdata);
        searchView = view.findViewById(R.id.search_class_category);
        iv_back = view.findViewById(R.id.iv_back);
    }


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

    private void startWorking() {
        getAllClassCategoryApi();
        searchViewSetUp();
    }

    /*------------------------------------------------- get all class category Api -------------------------------------------*/

    private void getAllClassCategoryApi() {
        if (user.isOnline(activity)) {
            dialog.show();

            ApiCaller.getclassCategoryApi(getActivity(), Config.Url.getclassCategory,
                    localStorage.getString(LocalStorage.classId),
                    new FutureCallback<ClassCategoryModel>() {

                        @Override
                        public void onCompleted(Exception e, ClassCategoryModel result) {
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

    /*----------------------------------------- all data set into class class category Recycler View -------------------------- */

    private void setUpofClassesRv(List<ClassCategoryDataModel> data) {
        if (data.isEmpty()) {
            notdata.setVisibility(View.VISIBLE);
            rv_school_Deatial.setVisibility(View.VISIBLE);
        } else {
            rv_school_Deatial.setLayoutManager(new GridLayoutManager(activity,2));
            adapter = new ClassCategoryRvAdapter(activity, data);
            rv_school_Deatial.setAdapter(adapter);
            listData = data;
        }
    }
    /*------------------------------------------------ set up of search view -------------------------------------------------*/

    private void searchViewSetUp() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (listData == null || listData.isEmpty()) {

                } else {
                    List<ClassCategoryDataModel> newlist = new ArrayList<>();
                    for (ClassCategoryDataModel classCategoryDataModel : listData) {
                        String name = classCategoryDataModel.getName().toLowerCase();
                        if (name.contains(s))
                            newlist.add(classCategoryDataModel);
                    }
                    adapter.setFilter(newlist);

                }
                return true;
            }

        });

    }

}
