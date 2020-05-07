package com.io.bookstores.activity.homeActivity.ui.cart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.activity.checkoutActivity.CheckoutActivity;
import com.io.bookstores.adapter.basicAdapter.CartAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstores.model.deliveryPriceModel.DeliveryResponseModel;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.registerModel.RegisterModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CartFragment extends Fragment {
    private Button card_view;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    public static RecyclerView recyclerView;
    public static CartAdapter cartAdapter;
    public static ArrayList<CartLocalListResponseMode> list;
    LocalStorage localStorage;
    public static TextView tv_name_1, tv_name_2, no_text_found;
    public static RadioButton rb_1st,rb_2nd;
    public static NestedScrollView nested_sc_view;
    public static TextView delivery_type, deliv_charge, tv_gst, total_cost, totalAll_cost;
    private DeliveryResponseModel deliveryModel;
    private String deliveryType;
    public static int dilvery = 0;
    public static int price, Qty, gst = 0;
    private EditText et_g_addrss, et_g_name, et_g_email, et_g_phone, et_g_passowrd;
    private LinearLayout ll_deliveryAddressGuest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cart_fragment, container, false);

        initView(root);
        bindListner();
        return root;
    }

    /* -------------------------------intialize all views that are used in this fragment -------------------------------------*/

    private void initView(View root) {
        tv_name_1 =  root.findViewById(R.id.tv_name_1);
        total_cost =  root.findViewById(R.id.total_cost);
        tv_gst =  root.findViewById(R.id.tv_gst);
        delivery_type =  root.findViewById(R.id.delivery_type);
        deliv_charge =  root.findViewById(R.id.deliv_charge);
        totalAll_cost = root.findViewById(R.id.totalAll_cost);
        nested_sc_view =  root.findViewById(R.id.nested_sc_view);
        no_text_found =  root.findViewById(R.id.no_text_found);
        tv_name_2 =  root.findViewById(R.id.tv_name_2);
        rb_1st =  root.findViewById(R.id.rb_1st1);
        rb_2nd =  root.findViewById(R.id.rb_2nd2);
        et_g_addrss = root.findViewById(R.id.et_g_addrss);
        et_g_name = root.findViewById(R.id.et_g_name);
        et_g_email = root.findViewById(R.id.et_g_email);
        et_g_phone = root.findViewById(R.id.et_g_phone);
        et_g_passowrd = root.findViewById(R.id.et_g_passowrd);
        ll_deliveryAddressGuest = root.findViewById(R.id.ll_deliveryAddressGuest);
        localStorage = new LocalStorage(getActivity());
        localStorage.putBooleAan(LocalStorage.isCart,false);
        user = new userOnlineInfo();
        list = new ArrayList<CartLocalListResponseMode>();
        recyclerView = root.findViewById(R.id.recyclerView);
        card_view = root.findViewById(R.id.btnLoginToDashBoard);
        getSqliteData1();
        callApiToGetDeliveryCharge();
        deliv_charge.setText("0KD");
        if (!localStorage.getBoolean(LocalStorage.isLoggedIn)) {
            ll_deliveryAddressGuest.setVisibility(View.VISIBLE);
        }


    }

    /*-------------------------------------------------- get Delivery Price Api call ---------------------------------------*/

    private void callApiToGetDeliveryCharge() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getDileveryPrice(getActivity(), Config.Url.getDeliverCharge,
                    new FutureCallback<DeliveryResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, DeliveryResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                dialog.dismiss();
                                deliveryModel = result;
                                dialog.dismiss();
                                tv_name_1.setText(result.getData().get(0).getType() + " Delivery");
                                //  rb_1st.setText(result.getData().get(0).getPrice() + result.getData().get(0).getUnit());
                                //  rb_2nd.setText(result.getData().get(1).getPrice() + result.getData().get(1).getUnit());
                                tv_name_2.setText(result.getData().get(1).getType() + " Delivery");
                                // deliv_charge.setText(deliveryModel.getData().get(1).getPrice() + deliveryModel.getData().get(1).getUnit());

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }

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
                    dilvery = deliveryModel.getData().get(0).getPrice();
                    getSqliteData1();
                }
            }
        });
        rb_2nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_2nd.isChecked()){
                    rb_1st.setChecked(false);
                    Utils.showAlertDialog(getActivity(), "Standard delivery is depend on your address.procced to checkout");
                   /* delivery_type.setText(deliveryModel.getData().get(1).getType() + " Delivery");
                    deliv_charge.setText(deliveryModel.getData().get(1).getPrice() + deliveryModel.getData().get(1).getUnit());
                    dilvery = deliveryModel.getData().get(1).getPrice();*/
                    // delivery_type.setText(deliveryModel.getData().get(1).getType() + " Delivery");
                    deliv_charge.setText("Its Depend on your address");
                    dilvery = 0;
                    //  dilvery = deliveryModel.getData().get(1).getPrice();

                    getSqliteData1();
                }
            }
        });
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalStorage localStorage = new LocalStorage(getActivity());
                if(localStorage.getBoolean(LocalStorage.isLoggedIn)){

                    if (rb_1st.isChecked()== false && rb_2nd.isChecked()==false ) {
                        Toast.makeText(getActivity(), "please select delivery method", Toast.LENGTH_SHORT).show();
                    } else {
                        if (rb_1st.isChecked()) {
                            deliveryType = deliveryModel.getData().get(0).getType();
                        } else {
                            deliveryType = deliveryModel.getData().get(1).getType();
                        }
                        int total = price;
                        Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                        // intent.putExtra("deliveryType",deliveryType);
                        intent.putExtra("totalprice", total);

                        startActivity(intent);
                    }
                }else{

                    guestLoginWork();
                    /*localStorage.putBooleAan(LocalStorage.isCart,true);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);*/
                }
                localStorage.putString(LocalStorage.addressId,"");

            }
        });
    }

    private void guestLoginWork() {

        String address = et_g_addrss.getText().toString().trim();
        String name = et_g_name.getText().toString().trim();
        String email = et_g_email.getText().toString().trim();
        String phone = et_g_phone.getText().toString().trim();
        String password = et_g_passowrd.getText().toString().trim();
        if (address.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Utils.showAlertDialog(getActivity(), "Please Enter Your Information Properly");
            return;
        } else if (!isEmailValid(email)) {
            Utils.showAlertDialog(getActivity(), getResources().getString(R.string.email_not_valid));
            return;
        } else if (et_g_phone.length() != 8) {
            Utils.showAlertDialog(getActivity(), getResources().getString(R.string.phone_number_must_be_of_8));
            return;
        } else if (rb_1st.isChecked() == false && rb_2nd.isChecked() == false) {
            Toast.makeText(getActivity(), "please select delivery method", Toast.LENGTH_SHORT).show();
        } else {
            if (rb_1st.isChecked()) {
                deliveryType = deliveryModel.getData().get(0).getType();
            } else {
                deliveryType = deliveryModel.getData().get(1).getType();
            }
            registrationApi(name, email, phone, password, address);

        }

    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    /*----------------------------------------------- Registration Api------------------------------------------------------*/

    private void registrationApi(String f_name, final String u_emael, String et_number, final String password,
                                 String address) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.registerCustomer(getActivity(), Config.Url.registerCustomer
                    , u_emael, et_number, password, f_name, address,
                    new FutureCallback<RegisterModel>() {
                        @Override
                        public void onCompleted(Exception e, RegisterModel result) {
                            LocalStorage localStorage = new LocalStorage(getActivity());

                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                dialog.dismiss();
                                return;
                            }
                            if (result != null) {
                                if (result.getStatus() == true) {
                                    dialog.dismiss();
                                    loginApi(u_emael, password);
                                    // Toast.makeText(GuestLoginActivity.this, "" + result.getMessage(), Toast.LENGTH_LONG).show();
                                   /* Intent intent = new Intent(GuestLoginActivity.this, LoginActivity.class);
                                    startActivity(intent);*/
                                } else {
                                    dialog.dismiss();
                                    loginApi(u_emael, password);
                                }

                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }

    }

    private void loginApi(String u_emael, String password) {

        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.loginCustomer(getActivity(), Config.Url.login, u_emael, password,
                    new FutureCallback<LoginModel>() {
                        @Override
                        public void onCompleted(Exception e, LoginModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                dialog.dismiss();
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == true) {
                                    dialog.dismiss();
                                    saveLoginData(result);
                                    localStorage.putBooleAan(LocalStorage.isLoggedIn, true);
                                    navigateToHomeActivit(result);
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), "User have already register with this email-id, Please Login  ", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }


    }

    private void navigateToHomeActivit(LoginModel result) {
        localStorage.putInt(LocalStorage.role, result.getData().getRole());
        if (result.getData().getRole() == 1) {
         /*   Intent i = new Intent(getActivity() , BookStoreMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);*/
        } else {
            int total = price;
            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            // intent.putExtra("deliveryType",deliveryType);
            intent.putExtra("totalprice", total);

            startActivity(intent);
        }

    }

    private void saveLoginData(LoginModel result) {
        Gson gson = new Gson();
        String json = gson.toJson(result);
        localStorage.putDistributorProfile(result);
        localStorage.putString(LocalStorage.guestId, "");
        localStorage.putString(LocalStorage.token, result.getData().getToken());
        localStorage.putInt(LocalStorage.role, result.getData().getRole());
        if (result.getData().getRole() == 0) {
            localStorage.putInt(LocalStorage.userId, result.getData().getUser().getUserId());
        } else {
            localStorage.putInt(LocalStorage.userId, result.getData().getUser().getStoreId());
        }

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
            int price = 0;
            int gst = 0;
            int qty = 0;
            int sum = 0;
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
                    shoppingBagModel.setWishlist(json_data.getString("wishlist"));
                    shoppingBagModel.setSize(json_data.getString("size"));
                    shoppingBagModel.setType(json_data.getString("type"));
                    shoppingBagModel.setPID(json_data.getString("P_ID"));
                    shoppingBagModel.setSchoolStoreId(json_data.getString("schoolStoreId"));
                    shoppingBagModel.setCategory(json_data.getString("category"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));
                    price = Integer.parseInt(json_data.getString("Price"));
                    qty = Integer.parseInt(json_data.getString("Quantity"));
                   // gst = Integer.parseInt(gst + json_data.getString("gstPrice"));
                    sum += price * qty;

                    list.add(shoppingBagModel);

                }
                this.price = sum;
             //   this.gst = gst;
              //  tv_gst.setText(String.valueOf(gst) + "KD");
                total_cost.setText(String.valueOf(sum) + "KD");
                int alltotla = sum + dilvery;
                totalAll_cost.setText(alltotla + "KD");
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