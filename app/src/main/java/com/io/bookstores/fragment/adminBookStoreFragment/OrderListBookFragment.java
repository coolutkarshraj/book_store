package com.io.bookstores.fragment.adminBookStoreFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.adminAdapter.AdminOrderAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.addAddressResponseModel.GetAdminOrderListResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderListBookFragment extends Fragment {

    private Activity activity;
    private LocalStorage localStorage;
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

    /*----------------------------------- intialize all Views that are used in this fragment -----------------------------------*/

    private void intializeViews(View view) {
        activity = getActivity();
        localStorage = new LocalStorage(activity);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    /*------------------------------------------- bind all views that are used in this fragment ------------------------------*/

    private void bindListner() {
    }

    /*--------------------------------------------------- start Working -----------------------------------------------------*/

    private void startWorking() {
        getOrderList();
    }

    /*----------------------------------------------- get all order list api call -------------------------------------------*/

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
                                        Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getInt(LocalStorage.userId));
                                        dialog.dismiss();
                                    }else{
                                        dialog.dismiss();
                                        Utils.showAlertDialog(getActivity(), "Something Went Wrong");
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
                            }else {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                dialog.dismiss();
                            }
                        }


                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*---------------------------------------- all order list  stored into recycler view --------------------------------------*/
    private void setRecyclerViewData(GetAdminOrderListResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        orderAdapter = new AdminOrderAdapter(getActivity(), result.getData());
        recyclerView.setAdapter(orderAdapter);

    }
}
