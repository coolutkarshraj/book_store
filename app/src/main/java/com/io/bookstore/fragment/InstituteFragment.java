package com.io.bookstore.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.InstitutesAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.model.BookstoreModel;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.model.insituteModel.InsituiteDataModel;
import com.io.bookstore.model.insituteModel.InsituiteResponseModel;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituteFragment extends Fragment implements  RecyclerViewClickListener {

    private Activity activity;
    private RecyclerView recyclerView;
    private InstitutesAdapter institutesAdapter;
    private CoursesFragment coursesFragment;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private List<InsituiteDataModel> item = new ArrayList<>();
    RecyclerViewClickListener recyclerViewClickListener;

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
        dialog = new NewProgressBar(getActivity());
        recyclerView = root.findViewById(R.id.recyclerView_institute);
    }

    private void startWorking() {
        getInstituiteList();
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


    @Override
    public void onClickPosition(int position) {
        coursesFragment= new CoursesFragment(item.get(position).getInstituteId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, coursesFragment)
                .addToBackStack(null)
                .commit();

    }
}