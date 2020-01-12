package com.io.bookstore.activity.homeActivity.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.adapter.CartAdapter;
import com.io.bookstore.adapter.OrderAdapter;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.loginModel.LoginModel;

import java.util.ArrayList;


public class OrderFragment extends Fragment {
    private ArrayList itemname;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private TextView loggedih;
    private LocalStorage localStorage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.order_fragment, container, false);
      craeteArray();

        recyclerView = root.findViewById(R.id.recyclerView);
        loggedih = root.findViewById(R.id.loggedih);
        localStorage = new LocalStorage(getActivity());
        LoginModel loginModel =  localStorage.getUserProfile() ;
        System.out.println(loginModel);
        if(loginModel == null){
            recyclerView.setVisibility(View.GONE);
            loggedih.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            loggedih.setVisibility(View.GONE);
            setRecyclerViewData();
        }

        bindListner();
        return root;
    }

    private void setRecyclerViewData() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        orderAdapter = new OrderAdapter(getActivity(),itemname);
        recyclerView.setAdapter(orderAdapter);
    }

    private void bindListner() {
        loggedih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void craeteArray() {
        itemname = new ArrayList();
        itemname.add("Product Code : 001245");
        itemname.add("Product Code : 001249");
        itemname.add("Product Code : 001288");
        itemname.add("Product Code : 001445");
    }


}