package com.io.bookstores.fragment.schoolFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.io.bookstores.R;
import com.io.bookstores.listeners.ItemClickListner;


public class SchoolDetailFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rv_school_Deatial;
    private Activity activity;
    private ImageView iv_back;
    private ItemClickListner itemClickListner;

    public SchoolDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_school_detail, container, false);
        intializeViews(view);
        bindListner();
        return view;
    }


    /*------------------------------------------------------ intialize Views --------------------------------------------------*/

    private void intializeViews(View view) {
        activity = getActivity();
        itemClickListner = (ItemClickListner) getActivity();
        rv_school_Deatial = view.findViewById(R.id.rv_school_Deatial);
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
}
