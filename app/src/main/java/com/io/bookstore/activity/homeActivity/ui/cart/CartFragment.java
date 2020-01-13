package com.io.bookstore.activity.homeActivity.ui.cart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.activity.checkoutActivity.CheckoutActivity;
import com.io.bookstore.adapter.CartAdapter;
import com.io.bookstore.adapter.CourseAdapter;
import com.io.bookstore.adapter.ItemAdapter;
import com.io.bookstore.adapter.SToreAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstore.model.categoryModel.CategoryModel;
import com.io.bookstore.model.deliveryPriceModel.DeliveryResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    Button card_view;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    public static RecyclerView recyclerView;
    public static CartAdapter cartAdapter;
    public static ArrayList<CartLocalListResponseMode> list;
    LocalStorage localStorage;
    TextView tv_name_1,tv_name_2,no_text_found;
    RadioButton rb_1st,rb_2nd;
    NestedScrollView nested_sc_view;
    TextView delivery_type,deliv_charge,tv_gst,total_cost;
    private DeliveryResponseModel deliveryModel;
    private String deliveryType;
    int price,gst = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cart_fragment, container, false);

        initView(root);
        bindListner();
        return root;
    }

    private void initView(View root) {
        tv_name_1 =  root.findViewById(R.id.tv_name_1);
        total_cost =  root.findViewById(R.id.total_cost);
        tv_gst =  root.findViewById(R.id.tv_gst);
        delivery_type =  root.findViewById(R.id.delivery_type);
        deliv_charge =  root.findViewById(R.id.deliv_charge);
        nested_sc_view =  root.findViewById(R.id.nested_sc_view);
        no_text_found =  root.findViewById(R.id.no_text_found);
        tv_name_2 =  root.findViewById(R.id.tv_name_2);
        rb_1st =  root.findViewById(R.id.rb_1st);
        rb_2nd =  root.findViewById(R.id.rb_2nd);
        localStorage = new LocalStorage(getActivity());
        localStorage.putBooleAan(LocalStorage.isCart,false);
        user = new userOnlineInfo();
        list = new ArrayList<CartLocalListResponseMode>();
        recyclerView = root.findViewById(R.id.recyclerView);
        card_view = root.findViewById(R.id.btnLoginToDashBoard);
        getSqliteData1();
        callApiToGetDeliveryCharge();
    }

    private void callApiToGetDeliveryCharge() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getDileveryPrice(getActivity(), Config.Url.getDeliverCharge,
                    new FutureCallback<DeliveryResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, DeliveryResponseModel result) {
                            deliveryModel = result;
                            dialog.dismiss();
                            tv_name_1.setText(result.getData().get(0).getType() + " Delivery");
                            rb_1st.setText(result.getData().get(0).getPrice() + result.getData().get(0).getUnit());
                            rb_2nd.setText(result.getData().get(1).getPrice() + result.getData().get(1).getUnit());
                            tv_name_2.setText(result.getData().get(1).getType() + " Delivery");
                            deliv_charge.setText(deliveryModel.getData().get(1).getPrice() + deliveryModel.getData().get(1).getUnit());
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }


    private void bindListner() {
        rb_1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rb_1st.isChecked()){
                    rb_2nd.setChecked(false);
                    delivery_type.setText(deliveryModel.getData().get(0).getType() + " Delivery");
                    deliv_charge.setText(deliveryModel.getData().get(0).getPrice() + deliveryModel.getData().get(0).getUnit());
                }
            }
        });
        rb_2nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_2nd.isChecked()){
                    rb_1st.setChecked(false);
                    delivery_type.setText(deliveryModel.getData().get(1).getType() + " Delivery");
                    deliv_charge.setText(deliveryModel.getData().get(1).getPrice() + deliveryModel.getData().get(1).getUnit());
                }
            }
        });
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalStorage localStorage = new LocalStorage(getActivity());
                if(localStorage.getBoolean(LocalStorage.isLoggedIn)){
                    if(rb_1st.isChecked()){
                        deliveryType =   deliveryModel.getData().get(0).getType();
                    }else {
                        deliveryType =   deliveryModel.getData().get(1).getType();

                    }
                    Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                    intent.putExtra("deliveryType",deliveryType);
                    intent.putExtra("totalprice",price +gst);
                    startActivity(intent);
                }else{
                    localStorage.putBooleAan(LocalStorage.isCart,true);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void getSqliteData1() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(getActivity());
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
            Log.e("Error", "no Data");
            return;
        }
        JSONArray resultSet = new JSONArray();
        JSONObject returnObj = new JSONObject();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME2", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME1", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        String datajson = resultSet.toString();
        datajson.replaceAll("\\\\", "");
        Log.d("datajson", datajson);
        StaticData.CartData = datajson;
        setRecyclerViewData(datajson);
    }

    private void setRecyclerViewData(String datajson) {
        JSONArray jArray = null;

        try {
            list.clear();
            jArray = new JSONArray(datajson);
            if(jArray == null || jArray.length() == 0){
                no_text_found.setVisibility(View.VISIBLE);
                nested_sc_view.setVisibility(View.GONE);
            }else {
                no_text_found.setVisibility(View.GONE);
                nested_sc_view.setVisibility(View.VISIBLE);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    CartLocalListResponseMode shoppingBagModel = new CartLocalListResponseMode();
                    shoppingBagModel.setId(json_data.getString("Id"));
                    shoppingBagModel.setName(json_data.getString("Name"));
                    shoppingBagModel.setQuantity(json_data.getString("Quantity"));
                    shoppingBagModel.setPrice(json_data.getString("Price"));
                    shoppingBagModel.setImage(json_data.getString("Image"));
                    shoppingBagModel.setAvailibleQty(json_data.getString("avalible"));
                    shoppingBagModel.setPID(json_data.getString("P_ID"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));
                    list.add(shoppingBagModel);
                    price = price + Integer.parseInt( json_data.getString("Price")) ;
                    gst = gst + Integer.parseInt( json_data.getString("gstPrice")) ;
                }

                tv_gst.setText(String.valueOf(gst));
                total_cost.setText(String.valueOf(price));
                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(gridLayoutManager);
                cartAdapter = new CartAdapter(getActivity(), list);
                recyclerView.setAdapter(cartAdapter);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }