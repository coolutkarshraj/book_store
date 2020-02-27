package com.io.bookstore.activity.checkoutActivity;

import android.R.layout;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import com.io.bookstore.model.deliveryPriceModel.DeliveryResponseModel;
import com.io.bookstore.model.dilvery.DilveryAddressDataModel;
import com.io.bookstore.model.dilvery.DilveryAdressResponseModel;
import com.io.bookstore.model.dilvery.GetDPriceResponseModel;
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
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    Button btnLoginToDashBoard;
    Activity activity;
    ImageView iv_back;
    userOnlineInfo user;
    NewProgressBar dialog;
    private TextView tv_address;
    private ArrayList itemname;
    private RecyclerView recyclerView;
    public static TextView tv_name_1, tv_name_2, no_text_found;
    private AddressAdapter addressAdapter;
    private JsonArray jsonArray;
    public static TextView delivery_type, deliv_charge, tv_gst, total_cost, totalAll_cost;
    private String deliveryType;
    private DeliveryResponseModel deliveryModel;
    RadioButton rb_1st, rb_2nd;
    public static int dilvery = 2;
    LocalStorage localStorage;
    String message = "";
    int totalprice;
    int totalpricessss;
    String spindata, spindistict;

    private List<DilveryAddressDataModel> listdata = new ArrayList<>();
    List<String> listcity = new ArrayList<>();
    List<String> listDistict = new ArrayList<>();
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


        totalprice = getIntent().getIntExtra("totalprice", 0);
        activity = CheckoutActivity.this;
        user = new userOnlineInfo();
        tv_name_1 = findViewById(R.id.tv_name_1);
        total_cost = findViewById(R.id.total_cost);
        tv_gst = findViewById(R.id.tv_gst);
        delivery_type = findViewById(R.id.delivery_type);
        deliv_charge = findViewById(R.id.deliv_charge);
        totalAll_cost = findViewById(R.id.totalAll_cost);
        tv_name_2 = findViewById(R.id.tv_name_2);
        rb_1st = findViewById(R.id.rb_1st);
        rb_2nd = findViewById(R.id.rb_2nd);
        localStorage = new LocalStorage(activity);
        localStorage.putBooleAan(LocalStorage.isCart, false);
        recyclerView = findViewById(R.id.recyclerView);
        iv_back = findViewById(R.id.iv_back);
        tv_address = findViewById(R.id.tv_address);
        btnLoginToDashBoard = findViewById(R.id.btnLoginToDashBoard);
        total_cost.setText(String.valueOf(totalprice) + "KD");
        startService();
        callApiToGetDeliveryCharge();
        getAllCities();
    }

    private void callApiToGetDeliveryCharge() {
        if (user.isOnline(this)) {

            ApiCaller.getDileveryPrice(this, Config.Url.getDeliverCharge,
                    new FutureCallback<DeliveryResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, DeliveryResponseModel result) {
                            if (e != null) {

                                Utils.showAlertDialog(CheckoutActivity.this, "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {

                                deliveryModel = result;
                                rb_1st.setChecked(true);
                                tv_name_1.setText(result.getData().get(0).getType() + " Delivery");
                                rb_1st.setText(result.getData().get(0).getPrice() + result.getData().get(0).getUnit());
                                rb_2nd.setText("0KD");
                                tv_name_2.setText(result.getData().get(1).getType() + " Delivery");
                                delivery_type.setText(deliveryModel.getData().get(0).getType() + " Delivery");
                                deliveryType = deliveryModel.getData().get(0).getType();
                                deliv_charge.setText(deliveryModel.getData().get(0).getPrice() + deliveryModel.getData().get(0).getUnit());
                                totalpricessss = totalprice + deliveryModel.getData().get(0).getPrice();
                                totalAll_cost.setText(totalpricessss + "KD");
                            } else {

                                Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {

            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }


    private void bindListner() {
        btnLoginToDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localStorage.getString(LocalStorage.addressId).equals("")) {
                    Utils.showAlertDialog(activity, "please select delivery address");
                } else {
                    callApiToPlaceOrder();
                }

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
        rb_1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_1st.isChecked()) {
                    rb_2nd.setChecked(false);

                    delivery_type.setText(deliveryModel.getData().get(0).getType() + " Delivery");
                    deliveryType = deliveryModel.getData().get(0).getType();
                    deliv_charge.setText(deliveryModel.getData().get(0).getPrice() + deliveryModel.getData().get(0).getUnit());
                    dilvery = deliveryModel.getData().get(0).getPrice();


                    totalpricessss = totalprice + deliveryModel.getData().get(0).getPrice();
                    totalAll_cost.setText(totalpricessss + "KD");
                }
            }
        });
        rb_2nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_2nd.isChecked()) {
                    rb_1st.setChecked(false);

                    delivery_type.setText(deliveryModel.getData().get(1).getType() + " Delivery");
                    deliveryType = deliveryModel.getData().get(1).getType();
                    if (localStorage.getString(LocalStorage.addressId).equals("")) {
                        rb_2nd.setChecked(false);
                        rb_1st.setChecked(true);
                        Utils.showAlertDialog(activity, "please select delivery address");
                    } else {
                        getApiDPrice();
                    }


                    // getSqliteData1();
                }
            }
        });


    }

    private void getApiDPrice() {
        if (user.isOnline(activity)) {

            final LocalStorage localStorage = new LocalStorage(this);
            ApiCaller.orderPrice(activity, Config.Url.orderprice + "sId=" + localStorage.getString(LocalStorage.Dummy_Store_ID) + "&dId=" + localStorage.getString(LocalStorage.addressId), localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetDPriceResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetDPriceResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == true) {
                                    dialog.dismiss();
                                    deliv_charge.setText(deliveryModel.getData().get(1).getPrice() + deliveryModel.getData().get(1).getUnit());
                                    dilvery = result.getData().getPrice();
                                    rb_2nd.setText(result.getData().getPrice() + "KD");
                                    totalpricessss = totalprice + result.getData().getPrice();
                                    totalAll_cost.setText(totalpricessss + "KD");
                                } else {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(CheckoutActivity.this, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    } else if (result.getMessage().equals("District name issue")) {
                                        message = "District name issue";
                                     /*   rb_2nd.setChecked(false);
                                        rb_1st.setChecked(true);*/;
                                        deliv_charge.setText(2 + deliveryModel.getData().get(1).getUnit());
                                        dilvery = 2;
                                        rb_2nd.setText("2KD");
                                        totalpricessss = totalprice + 2;
                                        totalAll_cost.setText(totalpricessss + "KD");
                                        //Utils.showAlertDialog(CheckoutActivity.this, "These books are not deliverd to this address .please change Address.");
                                        dialog.dismiss();
                                    }
                                }
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }

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
        final LocalStorage localStorage = new LocalStorage(this);
        JsonObject jsonObject = new JsonObject();
        jsonArray = new JsonArray();
        jsonObject.addProperty("storeid", localStorage.getString(LocalStorage.Dummy_Store_ID));
        jsonObject.addProperty("deliverytype", deliveryType);
        jsonObject.addProperty("deliveryaddressid", localStorage.getString(LocalStorage.addressId));
        jsonObject.addProperty("totalprice", totalpricessss);
        getSqliteData1();
        jsonObject.add("items", jsonArray);

        Log.e("jsonob", "" + jsonObject);
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

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(activity, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {

                                    if (result.getStatus() == true) {
                                        Intent intent = new Intent(CheckoutActivity.this, ProcessingActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(CheckoutActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }


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
            final LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.getUserSavedAddressList(activity, Config.Url.getAddressList, localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetAddressListResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetAddressListResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(CheckoutActivity.this, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    setRecyclerViewData(result);
                                    dialog.dismiss();

                                }
                            }
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
        final Spinner etCity = (Spinner) dialog.findViewById(R.id.et_city);
        final Spinner et_distic = (Spinner) dialog.findViewById(R.id.et_distic);
        final EditText etState = (EditText) dialog.findViewById(R.id.et_state);
        final EditText etPinCode = (EditText) dialog.findViewById(R.id.et_pincode);


        ArrayAdapter aa2 = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, listDistict);
        et_distic.setAdapter(aa2);


        etCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (listcity.isEmpty() || listcity == null) {
                    Toast.makeText(activity, "please select District", Toast.LENGTH_SHORT).show();
                } else {
                    spindata = listcity.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        et_distic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spindistict = listDistict.get(i);
                listcity.clear();
                for (int j = 0; j < listdata.get(i).getCities().size(); j++) {

                    listcity.add(listdata.get(i).getCities().get(j).getName());
                }

                ArrayAdapter aa = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, listcity);
                etCity.setAdapter(aa);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  addAddressValidateData(etAddress, spindata, etState, etPinCode,dialog,spindistict);
                addAddressValidateData(etAddress, spindata, etState, etPinCode,dialog,spindistict);
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


    private void addAddressValidateData(EditText etAddress, String spindata, EditText etState, EditText etPinCode, Dialog dialog, String spindistict) {
        String strAddress1 = etAddress.getText().toString().trim();
       // String strCity = etCity.getText().toString().trim();
        String strState = etState.getText().toString().trim();
        String strPinCode = etPinCode.getText().toString().trim();
        if (strAddress1.isEmpty() || strState.isEmpty() || strPinCode.isEmpty()) {
            etAddress.setError("Please Enter Address 1");

           // etCity.setError("Please Enter City");
            etState.setError("Please Enter State");
            etPinCode.setError("Please Enter PinCode");
        } else {

            addDataIntoApi(strAddress1, spindata, strState, strPinCode,dialog,spindistict);

        }
    }

    private void addDataIntoApi(String strAddress1, String strCity, String strState, String strPinCode, final Dialog dialog, String spindistict) {
        if (user.isOnline(activity)) {
            this.dialog = new NewProgressBar(activity);
            this.dialog.show();
            final LocalStorage localStorage = new LocalStorage(this);
            ApiCaller.addAddress(activity, Config.Url.addAddress, " ", strAddress1, " ", strCity, strState, Integer.valueOf(strPinCode), " ",
                    " ", " ", " ", localStorage.getString(LocalStorage.token),spindata,
                    new FutureCallback<AddAddressResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddAddressResponseModel result) {
                            if (e != null) {
                                CheckoutActivity.this.dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }
                            if (e != null) {
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus()) {
                                    CheckoutActivity.this.dialog.dismiss();
                                    dialog.dismiss();
                                    Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    getaddressListApi();
                                } else {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(CheckoutActivity.this, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        CheckoutActivity.this.dialog.dismiss();
                                    }
                                }
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }


        private void getAllCities () {
            if (user.isOnline(activity)) {
                final LocalStorage localStorage = new LocalStorage(this);
                ApiCaller.getdistic(activity, Config.Url.disticGet,
                        new FutureCallback<DilveryAdressResponseModel>() {
                            @Override
                            public void onCompleted(Exception e, DilveryAdressResponseModel result) {
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
                                        listData(result.getData());
                                    } else {
                                        if (result.getMessage().equals("Unauthorized")) {
                                            Utils.showAlertDialogLogout(CheckoutActivity.this, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                            dialog.dismiss();
                                        }
                                    }
                                }

                            }
                        });

            } else {
                Utils.showAlertDialog(activity, "No Internet Connection");
            }

        }

        private void listData (List < DilveryAddressDataModel > data) {
            listdata = data;
            for (int i = 0; i < data.size(); i++) {

                listDistict.add(data.get(i).getName());

            }


            Log.e("city", "" + listcity.size());
        }


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
