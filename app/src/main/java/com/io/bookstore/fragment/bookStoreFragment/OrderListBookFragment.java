package com.io.bookstore.fragment.bookStoreFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.admin.AdminOrderAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.GetAdminOrderListResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderListBookFragment extends Fragment {

    Activity activity;
    LocalStorage localStorage;
    public static RecyclerView recyclerView;
    public static AdminOrderAdapter orderAdapter;

    public OrderListBookFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_book_list_layout, container, false);
        intializeViews(view);
        bindListner();
        startWorking();
        return view;
    }

    private void intializeViews(View view) {
        activity = getActivity();
        localStorage = new LocalStorage(activity);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void bindListner() {
    }

    private void startWorking() {
        getOrderList();
    }

    private void getOrderList() {
        userOnlineInfo user;
        user = new userOnlineInfo();
        if (user.isOnline(getActivity())) {
            final NewProgressBar dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getAdminOrder(getActivity(), Config.Url.adminOrders, localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetAdminOrderListResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, GetAdminOrderListResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getInt(LocalStorage.userId));
                                        dialog.dismiss();
                                    }

                                } else {
                                    if (result.getData() == null || result.getData().size() == 0) {
                                        dialog.dismiss();
                                        Toast.makeText(activity, "your have no Item ", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.dismiss();
                                        setRecyclerViewData(result);
                                    }
                                }
                            }
                        }


                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setRecyclerViewData(GetAdminOrderListResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        orderAdapter = new AdminOrderAdapter(getActivity(), result.getData());
        recyclerView.setAdapter(orderAdapter);

    }
}
