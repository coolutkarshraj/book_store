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
import android.widget.Toast;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.schoolAdapter.AllSchoolsRvAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.model.schoolModel.GetAllSchoolDataModel;
import com.io.bookstores.model.schoolModel.GetAllSchoolResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;


public class AllSchoolsFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private RecyclerView rvAllSchools;
    private AllSchoolsRvAdapter adapter;
    private ImageView iv_back;
    private TextView notdata;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private SearchView search_all_Schools;
    private List<GetAllSchoolDataModel> listData;
    private ItemClickListner itemClickListner;

    public AllSchoolsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_schools, container, false);
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
        rvAllSchools = view.findViewById(R.id.rv_all_schools);
        iv_back = view.findViewById(R.id.iv_back);
        notdata = view.findViewById(R.id.notdata);
        search_all_Schools = view.findViewById(R.id.search_all_Schools);
        rvAllSchools.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
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

    /*---------------------------------------------------- start Working ---------------------------------------------------*/

    private void startWorking() {
        getAllSchoolsApiCall();
        searchViewSetUp();

    }

    /*------------------------------------------------ get all school list from api -------------------------------------------*/

    private void getAllSchoolsApiCall() {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.getSchoolApi(getActivity(), Config.Url.getSchools,
                    new FutureCallback<GetAllSchoolResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, GetAllSchoolResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {

                                if (result.isStatus()) {
                                    dialog.dismiss();
                                    setofSchoolRecyclerView(result.getData());
                                }
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*----------------------------------------- all schools data set into recycler View ----------------------------------------*/

    private void setofSchoolRecyclerView(List<GetAllSchoolDataModel> data) {
        if (data.isEmpty()) {
            notdata.setVisibility(View.VISIBLE);
            rvAllSchools.setVisibility(View.GONE);
        } else {
            notdata.setVisibility(View.GONE);
            rvAllSchools.setVisibility(View.VISIBLE);
            adapter = new AllSchoolsRvAdapter(activity, data);
            listData = data;
            rvAllSchools.setAdapter(adapter);
        }
    }

    /*---------------------------------------------------- set up of search view --------------------------------------------------*/

    private void searchViewSetUp() {
        search_all_Schools.setQueryHint("Search Schools...");
        search_all_Schools.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (listData == null || listData.isEmpty()) {
                    Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                } else {
                    List<GetAllSchoolDataModel> newlist = new ArrayList<>();
                    for (GetAllSchoolDataModel productList : listData) {
                        String name = productList.getSchoolName().toLowerCase();
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
