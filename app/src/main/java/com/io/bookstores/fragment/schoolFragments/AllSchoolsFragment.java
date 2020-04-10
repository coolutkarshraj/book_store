package com.io.bookstores.fragment.schoolFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.io.bookstores.R;
import com.io.bookstores.adapter.schoolAdapter.AllSchoolsRvAdapter;
import com.io.bookstores.listeners.ItemClickListner;


public class AllSchoolsFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private RecyclerView rvAllSchools;
    private AllSchoolsRvAdapter adapter;
    private ImageView iv_back;
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
        itemClickListner = (ItemClickListner) getActivity();
        rvAllSchools = view.findViewById(R.id.rv_all_schools);
        iv_back = view.findViewById(R.id.iv_back);
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

    /*---------------------------------------------------- start Working -------------------------------------------------*/

    private void startWorking() {
        setUpofSchoolsRv();
    }

    private void setUpofSchoolsRv() {
        
    }
}
