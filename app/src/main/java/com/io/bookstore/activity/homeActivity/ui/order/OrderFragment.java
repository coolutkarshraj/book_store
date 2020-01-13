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

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.activity.checkoutActivity.CheckoutActivity;
import com.io.bookstore.activity.checkoutActivity.ProcessingActivity;
import com.io.bookstore.adapter.CartAdapter;
import com.io.bookstore.adapter.OrderAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.PlaceOrderModel.OrderModel;
import com.io.bookstore.model.getAllOrder.GetAllOrder;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

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
        recyclerView = root.findViewById(R.id.recyclerView);
        loggedih = root.findViewById(R.id.loggedih);
        localStorage = new LocalStorage(getActivity());
        LoginModel loginModel =  localStorage.getUserProfile() ;
        System.out.println(loginModel);
        if(loginModel == null){
            recyclerView.setVisibility(View.GONE);
            loggedih.setVisibility(View.VISIBLE);
        }else {
            callApiToGetOrder();
        }

        bindListner();
        return root;
    }

    private void callApiToGetOrder() {
        userOnlineInfo user;
        user = new userOnlineInfo();
        if (user.isOnline(getActivity())) {
            final NewProgressBar dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getOrder(getActivity(), Config.Url.getAllOrder,localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetAllOrder>() {

                        @Override
                        public void onCompleted(Exception e, GetAllOrder result) {
                            dialog.dismiss();
                            if(result.getData() == null || result.getData().size() == 0){
                                recyclerView.setVisibility(View.GONE);
                                loggedih.setVisibility(View.VISIBLE);
                                loggedih.setText("You have not order any item till");
                            }else {
                                recyclerView.setVisibility(View.VISIBLE);
                                loggedih.setVisibility(View.GONE);
                                setRecyclerViewData(result);
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setRecyclerViewData(GetAllOrder order) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        orderAdapter = new OrderAdapter(getActivity(),order.getData());
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
}