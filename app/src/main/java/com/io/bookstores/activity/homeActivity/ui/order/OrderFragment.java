package com.io.bookstores.activity.homeActivity.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.authentication.LoginActivity;
import com.io.bookstores.adapter.basicAdapter.OrderAdapter;
import com.io.bookstores.adapter.basicAdapter.SchoolOrderRvAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.fragment.basicFragment.TrackFragment;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.myOrder.MyOrderResponseModel;
import com.io.bookstores.model.schoolOrderList.SchoolOrderResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment implements RecyclerViewClickListener {
    private ArrayList itemname;
    private RecyclerView recyclerView, rv_schoolOrder;
    private OrderAdapter orderAdapter;
    private TrackFragment track;
    private TextView loggedih;
    private LinearLayout hide;
    userOnlineInfo user;
    private LocalStorage localStorage;
    public static RelativeLayout rl_layout;
    private RecyclerViewClickListener item;
    private List<com.io.bookstores.model.myOrder.Datum> courseicon;
    private ImageView iv_back;
    String isEmpty = "", isEmpty1 = "";
    TextView tv_no_order;
    private LinearLayout ll_book_order, ll_school_order;
    private ItemClickListner itemClickListner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.order_fragment, container, false);
        item = this;
        iv_back = root.findViewById(R.id.iv_back);
        itemClickListner = (ItemClickListner) getActivity();
        recyclerView = root.findViewById(R.id.recyclerView);
        loggedih = root.findViewById(R.id.loggedih);
        hide = root.findViewById(R.id.hide);
        user = new userOnlineInfo();
        ll_book_order = root.findViewById(R.id.ll_book_order);
        rv_schoolOrder = root.findViewById(R.id.rv_schoolOrder);
        tv_no_order = root.findViewById(R.id.tv_no_order);
        ll_school_order = root.findViewById(R.id.ll_school_order);
        rl_layout = root.findViewById(R.id.rl_layout);
        localStorage = new LocalStorage(getActivity());
        LoginModel loginModel = localStorage.getUserProfile();
        System.out.println(loginModel);
        if (localStorage.getString(LocalStorage.token) == null || localStorage.getString(LocalStorage.token).equals("")) {
            hide.setVisibility(View.GONE);
            loggedih.setVisibility(View.VISIBLE);
        } else {
            loggedih.setVisibility(View.GONE);
            hide.setVisibility(View.VISIBLE);
            callApiToGetOrder();
            callOrderOfSchool();
            if (isEmpty.equals("empty") && isEmpty1.equals("empty")) {
                tv_no_order.setVisibility(View.VISIBLE);
            }
        }

        bindListner();
        return root;
    }


    private void callApiToGetOrder() {


        if (user.isOnline(getActivity())) {
            final NewProgressBar dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getOrder(getActivity(), Config.Url.getAllOrder, localStorage.getString(LocalStorage.token),
                    new FutureCallback<MyOrderResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, MyOrderResponseModel result) {
                            dialog.dismiss();
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }

                            if (result.getData() == null || result.getData().size() == 0 || result.getData().isEmpty()) {
                                ll_book_order.setVisibility(View.GONE);
                                isEmpty = "empty";
                            } else {
                                ll_book_order.setVisibility(View.VISIBLE);
                                setRecyclerViewData(result);
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setRecyclerViewData(MyOrderResponseModel order) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        orderAdapter = new OrderAdapter(getActivity(), order.getData(), item);
        courseicon = order.getData();
        recyclerView.setAdapter(orderAdapter);
    }

    private void callOrderOfSchool() {
        if (user.isOnline(getActivity())) {

            ApiCaller.getOrderSchool(getActivity(), Config.Url.getAllUserListSchool, localStorage.getString(LocalStorage.token),
                    new FutureCallback<SchoolOrderResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, SchoolOrderResponseModel result) {

                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result.getData() == null || result.getData().size() == 0 || result.getData().isEmpty()) {
                                ll_school_order.setVisibility(View.GONE);
                                isEmpty1 = "empty";
                            } else {

                                ll_school_order.setVisibility(View.VISIBLE);
                                setRecyclerViewDataSchool(result);
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setRecyclerViewDataSchool(SchoolOrderResponseModel result) {
        rv_schoolOrder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        SchoolOrderRvAdapter adapter = new SchoolOrderRvAdapter(getActivity(), result.getData(), item);
        rv_schoolOrder.setAdapter(adapter);
    }


    private void bindListner() {
        loggedih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner.onClick(6);
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