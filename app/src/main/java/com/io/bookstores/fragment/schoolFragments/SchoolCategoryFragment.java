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


public class SchoolCategoryFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private RecyclerView rvAllClasses;
    private AllSchoolsRvAdapter adapter;
    private ImageView iv_back;
    private ItemClickListner itemClickListner;

    public SchoolCategoryFragment() {
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
        itemClickListner = (ItemClickListner) getActivity();
        rvAllClasses = view.findViewById(R.id.rv_all_classes);
        iv_back = view.findViewById(R.id.iv_back);
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

    /*---------------------------------------------------- start Working -------------------------------------------------*/

    private void startWorking() {
        setUpofClassesRv();
    }

    private void setUpofClassesRv() {

    }
}
