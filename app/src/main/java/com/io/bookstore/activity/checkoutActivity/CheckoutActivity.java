package com.io.bookstore.activity.checkoutActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.adapter.AddressAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.PlaceOrderModel.OrderModel;
import com.io.bookstore.model.addAddressResponseModel.AddAddressResponseModel;
import com.io.bookstore.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    Button btnLoginToDashBoard;
    Activity activity;
    ImageView iv_back;
    userOnlineInfo user;
    NewProgressBar dialog;
    private TextView tv_address;
    private ArrayList itemname;
    private RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private JsonArray jsonArray;
    private String deliveryType;
    int totalprice;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(StaticData.PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        intilizeView();
        bindListner();
        startWork();

    }


    private void intilizeView() {
        deliveryType = getIntent().getStringExtra("deliveryType");
        totalprice = getIntent().getIntExtra("totalprice", 0);
        activity = CheckoutActivity.this;
        user = new userOnlineInfo();
        recyclerView = findViewById(R.id.recyclerView);
        iv_back = findViewById(R.id.iv_back);
        tv_address = findViewById(R.id.tv_address);
        btnLoginToDashBoard = findViewById(R.id.btnLoginToDashBoard);
        startService();

    }


    private void bindListner() {
        btnLoginToDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypalPayment();


            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpen();
            }
        });
    }

    private void startService() {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    private void paypalPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal("2"), "USD", "hipster jeans",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    String paydetial = confirm.toJSONObject().toString(4);
                    JSONObject jsonObject;
                    jsonObject = new JSONObject(paydetial);
                    callApiToPlaceOrder();
                    // Toast.makeText(activity, "" + jsonObject.getJSONObject("response"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(activity, "The user canceled.", Toast.LENGTH_SHORT).show();

        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(activity, "An invalid Payment or PayPalConfiguration was submitted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void callApiToPlaceOrder() {
        LocalStorage localStorage = new LocalStorage(this);
        JsonObject jsonObject = new JsonObject();
        jsonArray = new JsonArray();
        jsonObject.addProperty("storeid", localStorage.getString(LocalStorage.Dummy_Store_ID));
        jsonObject.addProperty("deliverytype", deliveryType);
        jsonObject.addProperty("deliveryaddressid", localStorage.getString(LocalStorage.addressId));
        jsonObject.addProperty("totalprice", totalprice);
        getSqliteData1();
        jsonObject.add("items", jsonArray);


        if (user.isOnline(this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            ApiCaller.procedorder(this, Config.Url.placePrder, jsonObject, localStorage.getString(LocalStorage.token),
                    new FutureCallback<OrderModel>() {

                        @Override
                        public void onCompleted(Exception e, OrderModel result) {
                            dialog.dismiss();
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(CheckoutActivity.this, "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                Intent intent = new Intent(CheckoutActivity.this, ProcessingActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(CheckoutActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(this, "No Internet Connection");
        }
    }

    private void getSqliteData1() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(this);
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
        JSONArray jArray = null;

        try {
            jArray = new JSONArray(datajson);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("bookid", json_data.getString("P_ID"));
                jsonObject.addProperty("count", json_data.getString("Quantity"));
                jsonArray.add(jsonObject);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startWork() {
        getaddressListApi();

    }

    private void getaddressListApi() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.getUserSavedAddressList(activity, Config.Url.getAddressList, localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetAddressListResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetAddressListResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            setRecyclerViewData(result);
                            dialog.dismiss();
                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void setRecyclerViewData(GetAddressListResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(CheckoutActivity.this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        if (result.getData().getDeliveryAddresses() == null) {

        } else {
            for (int i = 0; i < result.getData().getDeliveryAddresses().size(); i++) {
                result.getData().getDeliveryAddresses().get(i).setChecked(false);

            }
            addressAdapter = new AddressAdapter(CheckoutActivity.this, result.getData().getDeliveryAddresses());
            recyclerView.setAdapter(addressAdapter);
        }


    }

    private void dialogOpen() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_add_adress);
        dialog.setTitle("");
        final Button btn_Add = (Button) dialog.findViewById(R.id.btn_Add);
        final Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText etAddress = (EditText) dialog.findViewById(R.id.et_address);
        final EditText etCity = (EditText) dialog.findViewById(R.id.et_city);
        final EditText etState = (EditText) dialog.findViewById(R.id.et_state);
        final EditText etPinCode = (EditText) dialog.findViewById(R.id.et_pincode);


        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAddressValidateData(etAddress, etCity, etState, etPinCode);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void addAddressValidateData(EditText etAddress, EditText etCity, EditText etState, EditText etPinCode) {
        String strAddress1 = etAddress.getText().toString().trim();
        String strCity = etCity.getText().toString().trim();
        String strState = etState.getText().toString().trim();
        String strPinCode = etPinCode.getText().toString().trim();
        if (strAddress1.isEmpty() || strCity.isEmpty() || strState.isEmpty() || strPinCode.isEmpty()) {
            etAddress.setError("Please Enter Address 1");

            etCity.setError("Please Enter City");
            etState.setError("Please Enter State");
            etPinCode.setError("Please Enter PinCode");
        } else {

            addDataIntoApi(strAddress1, strCity, strState, strPinCode);

        }
    }

    private void addDataIntoApi(String strAddress1, String strCity, String strState, String strPinCode) {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            LocalStorage localStorage = new LocalStorage(this);
            ApiCaller.addAddress(activity, Config.Url.addAddress, "Testing", strAddress1, "Home", strCity, "Test", Integer.valueOf(strPinCode), "Dubai",
                    "Kuwait", "+965", strState, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AddAddressResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddAddressResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }
                            if (e != null) {
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus()) {
                                    dialog.dismiss();
                                    Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    getaddressListApi();
                                }
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
