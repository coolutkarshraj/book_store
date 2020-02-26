package com.io.bookstore.activity.homeActivity.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.adapter.OrderAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.fragment.TrackFragment;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.getAllOrder.Datum;
import com.io.bookstore.model.getAllOrder.GetAllOrder;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment implements RecyclerViewClickListener {
    private ArrayList itemname;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private TrackFragment track;
    private TextView loggedih;
    private LinearLayout hide;
    private LocalStorage localStorage;
    private RecyclerViewClickListener item;
    private List<Datum> courseicon;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.order_fragment, container, false);
        item = this;
        recyclerView = root.findViewById(R.id.recyclerView);
        loggedih = root.findViewById(R.id.loggedih);
        hide = root.findViewById(R.id.hide);
        localStorage = new LocalStorage(getActivity());
        LoginModel loginModel =  localStorage.getUserProfile() ;
        System.out.println(loginModel);
        if(localStorage.getString(LocalStorage.token) == null || localStorage.getString(LocalStorage.token).equals("")){
            hide.setVisibility(View.GONE);
            loggedih.setVisibility(View.VISIBLE);
        }else {
            loggedih.setVisibility(View.GONE);
            hide.setVisibility(View.VISIBLE);
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
                            if(e!=null){
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }

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
        orderAdapter = new OrderAdapter(getActivity(), order.getData(), item);
        courseicon = order.getData();
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

    @Override
    public void onClickPosition(int position) {
        if (courseicon.get(position).getOrderStatus().equals("Rejected")) {
            Toast.makeText(getActivity(), "your order was rejected and dont track", Toast.LENGTH_SHORT).show();
        } else {
            track = new TrackFragment(courseicon.get(position).getOrderStatus());
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, track)
                    .addToBackStack(null)
                    .commit();
        }
    }
}