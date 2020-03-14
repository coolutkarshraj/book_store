package com.io.bookstores.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.InstitutesAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.model.insituteModel.InsituiteDataModel;
import com.io.bookstores.model.insituteModel.InsituiteResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituteFragment extends Fragment implements  RecyclerViewClickListener,View.OnClickListener {

    private Activity activity;
    private RecyclerView recyclerView;
    private InstitutesAdapter institutesAdapter;
    private CoursesFragment coursesFragment;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private ImageView iv_back;
    private SearchView searchView;
    private List<InsituiteDataModel> item = new ArrayList<>();
    RecyclerViewClickListener recyclerViewClickListener;
    private ItemClickListner itemClickListner;

    public InstituteFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_institute, container, false);
        intializeViews(root);
        startWorking();
        return root;
    }


    private void intializeViews(View root) {
        activity = getActivity();
        user = new userOnlineInfo();
        recyclerViewClickListener = this;
        itemClickListner =(ItemClickListner)getActivity();
        iv_back = root.findViewById(R.id.iv_back);
        searchView = root.findViewById(R.id.sv_institute);
        dialog = new NewProgressBar(getActivity());
        recyclerView = root.findViewById(R.id.recyclerView_institute);
        iv_back.setOnClickListener(this);
    }

    private void startWorking() {
        getInstituiteList();
        searchViewSetUp();
    }

    private void getInstituiteList() {
        if (user.isOnline(getActivity())) {
            dialog.show();
            ApiCaller.getInstiuiteList(getActivity(), Config.Url.insituitelist,
                    new FutureCallback<InsituiteResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, InsituiteResponseModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {
                                if (result.getStatus()) {
                                    setRecyclerView(result);
                                }
                            }
                            dialog.dismiss();


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setRecyclerView(InsituiteResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        institutesAdapter = new InstitutesAdapter(getActivity(), result.getData(),recyclerViewClickListener);
        item = result.getData();
        recyclerView.setAdapter(institutesAdapter);
    }

    private void searchViewSetUp() {
        searchView.setQueryHint("Search More Institue");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<InsituiteDataModel> newlist = new ArrayList<>();
                for (InsituiteDataModel productList : item) {
                    String name = productList.getInstituteName().toLowerCase();
                    if (name.contains(s))
                        newlist.add(productList);
                }
                institutesAdapter.setFilter(newlist);
                return true;
            }

        });


    }


    @Override
    public void onClickPosition(int position) {
        coursesFragment= new CoursesFragment(item.get(position).getInstituteId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, coursesFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                itemClickListner.onClick(6);
        }
    }
}