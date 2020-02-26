package com.io.bookstore.adapter.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.fragment.bookStoreFragment.OrderListBookFragment;
import com.io.bookstore.holder.OrderHolder;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.GetAdminOrderListDataModel;
import com.io.bookstore.model.addAddressResponseModel.GetAdminOrderListResponseModel;
import com.io.bookstore.model.orderModel.OrderStatusChangeResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.List;


public class AdminOrderAdapter extends RecyclerView.Adapter<OrderHolder> {
    private Activity activity;
    private List<GetAdminOrderListDataModel> courseicon;
    RadioButton radioButton;
    LocalStorage localStorage;
    private NewProgressBar dialog;
    private userOnlineInfo user;

    public AdminOrderAdapter(Activity activity, List<GetAdminOrderListDataModel> courseicon) {
        this.activity = activity;
        this.courseicon = courseicon;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view_order, parent, false);
        localStorage = new LocalStorage(activity);
        user = new userOnlineInfo();
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        final GetAdminOrderListDataModel model = courseicon.get(position);
        holder.tv_product_id.setText(courseicon.get(position).toString());
        holder.textView11.setText(model.getCreatedDate());
        holder.textView12.setText("Status-" + " " + model.getOrderStatus());
        holder.textView9.setText("Order -#" + " " + model.getOrderId());
        Glide.with(activity).load(Config.imageUrl + model.getOrderItems().get(0).getBook().getAvatarPath()).into(holder.imageView10);

        holder.iv_editOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStatusChange(model.getOrderId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseicon.size();
    }

    /*------------------------------------------------- change password dialog---------------------------------------------*/
    public void dialogStatusChange(final Integer orderId) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.update_order_status_dialog);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.yes);
        final Button No = (Button) dialog.findViewById(R.id.no);
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);


        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) dialog.findViewById(selectedId);
                if (selectedId == -1) {

                } else {
                    orderUpdateApiCall(orderId, (String) radioButton.getText(),dialog);
                }
            }
        });
        No.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void orderUpdateApiCall(int orderId, String text, final Dialog dialog) {
        if (user.isOnline(activity)) {
            this.dialog = new NewProgressBar(activity);
            this.dialog.show();
            ApiCaller.updateOrderStaus(activity, Config.Url.statusUpdate, orderId, text, localStorage.getString(LocalStorage.token),
                    new FutureCallback<OrderStatusChangeResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, OrderStatusChangeResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogAdminLogout(activity, "Your Session was expire. please Logout!",localStorage.getInt(LocalStorage.userId));
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    if (result.getStatus() == true) {
                                        AdminOrderAdapter.this.dialog.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        getOrderList();
                                    } else {
                                        dialog.dismiss();
                                        AdminOrderAdapter.this.dialog.dismiss();
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }



                        }
                    });
        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void getOrderList() {

        if (user.isOnline(activity)) {
            final NewProgressBar dialog = new NewProgressBar(activity);
            dialog.show();
            ApiCaller.getAdminOrder(activity, Config.Url.adminOrders, localStorage.getString(LocalStorage.token),
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
                                        Utils.showAlertDialogLogout(activity, "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }

                                } else {
                                    if (result.getData() == null || result.getData().size() == 0) {
                                        dialog.dismiss();
                                        Toast.makeText(activity, "data empty", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.dismiss();
                                        setRecyclerViewData(result);
                                    }
                                }
                            }


                        }


                    });
        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void setRecyclerViewData(GetAdminOrderListResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(activity,
                RecyclerView.VERTICAL, false);
        OrderListBookFragment. recyclerView.setLayoutManager(gridLayoutManager);
        OrderListBookFragment.orderAdapter = new AdminOrderAdapter(activity, result.getData());
        OrderListBookFragment.recyclerView.setAdapter(OrderListBookFragment.orderAdapter);

    }

}
