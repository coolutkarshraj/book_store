package com.io.bookstore.activity.homeActivity.ui.deliveryAddress;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.adapter.AddressAdapter;
import com.io.bookstore.adapter.AddressCheckoutAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.AddAddressResponseModel;
import com.io.bookstore.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstore.model.dilvery.DilveryAddressDataModel;
import com.io.bookstore.model.dilvery.DilveryAdressResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DeliveryAddressFragment extends Fragment {
    Activity activity;
    userOnlineInfo user;
    NewProgressBar dialog;
    private TextView tv_address;
    private RecyclerView recyclerView;
    private AddressCheckoutAdapter addressAdapter;
    private JsonArray jsonArray;
    LocalStorage localStorage;
     LinearLayout linearLayout;
    private TextView loggdin;
    String spindata;
    private  List<String> listcity = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        intilizeView(root);
        bindListner();
        startWork();
        return root;
    }

    private void intilizeView(View root) {
        activity = getActivity();
        user = new userOnlineInfo();
        localStorage = new LocalStorage(getActivity());
        linearLayout = root.findViewById(R.id.linearLayout);
        loggdin = root.findViewById(R.id.loggedih);
        recyclerView = root.findViewById(R.id.recyclerView);
        tv_address = root.findViewById(R.id.tv_address);

    }

    private void bindListner() {
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllCities();
                dialogOpen();
            }
        });
        loggdin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, LoginActivity.class);
                startActivity(i);
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
        checkLoggedin();


    }

    private void checkLoggedin() {
        if (localStorage.getString(LocalStorage.token).equals("") ||
                localStorage.getString(LocalStorage.token) == null) {
            loggdin.setVisibility(View.VISIBLE);

        } else {
            linearLayout.setVisibility(View.VISIBLE);
            getaddressListApi();
        }
    }

    private void getaddressListApi() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();

            ApiCaller.getUserSavedAddressList(activity, Config.Url.getAddressList, localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetAddressListResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetAddressListResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if(result != null){
                                if(result.getStatus()== null){
                                    if(result.getMessage().equals("Unauthorized")){
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }else {
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
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
     /*   for (int i = 0; i < result.getData().getDeliveryAddresses().size(); i++) {
            result.getData().getDeliveryAddresses().get(i).setChecked(false);
        }*/
        addressAdapter = new AddressCheckoutAdapter(getActivity(), result.getData().getDeliveryAddresses());
        recyclerView.setAdapter(addressAdapter);
    }


    private void dialogOpen() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_add_adress);
        dialog.setTitle("");
        final Button btn_Add = (Button) dialog.findViewById(R.id.btn_Add);
        final Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText etAddress = (EditText) dialog.findViewById(R.id.et_address);
        final Spinner etCity = (Spinner) dialog.findViewById(R.id.et_city);
        final EditText etState = (EditText) dialog.findViewById(R.id.et_state);
        final EditText etPinCode = (EditText) dialog.findViewById(R.id.et_pincode);

        ArrayAdapter aa = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, listcity);
        etCity.setAdapter(aa);

        etCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spindata = listcity.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAddressValidateData(etAddress, spindata, etState, etPinCode,dialog);
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

    private void addAddressValidateData(EditText etAddress, String etCity, EditText etState, EditText etPinCode, Dialog dialog) {
        String strAddress1 = etAddress.getText().toString().trim();
      //  String strCity = etCity.getText().toString().trim();
        String strState = etState.getText().toString().trim();
        String strPinCode = etPinCode.getText().toString().trim();
        if (strAddress1.isEmpty() ||  strState.isEmpty() || strPinCode.isEmpty()) {
            etAddress.setError("Please Enter Address");
            etState.setError("Please Enter Landmark");
            etPinCode.setError("Please Enter PinCode");
        } else {

            addDataIntoApi(strAddress1, etCity, strState, strPinCode,dialog);

        }
    }

    private void addDataIntoApi(String strAddress1, String strCity, String strState, String strPinCode, final Dialog dialog) {
        if (user.isOnline(activity)) {
            this.dialog = new NewProgressBar(activity);
            this.dialog.show();
            final LocalStorage localStorage = new LocalStorage(getActivity());
            ApiCaller.addAddress(activity, Config.Url.addAddress, " ", strAddress1, " ", strCity,
                    strState, Integer.valueOf(strPinCode), " ",
                    " ", " ", " ", localStorage.getString(LocalStorage.token),
                    new FutureCallback<AddAddressResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddAddressResponseModel result) {
                            if (e != null) {
                                DeliveryAddressFragment.this.dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                DeliveryAddressFragment.this.dialog.dismiss();
                                Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                getaddressListApi();
                            } else {
                                if (result.getMessage().equals("Unauthorized")) {
                                    Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                    DeliveryAddressFragment.this.dialog.dismiss();
                                }
                                DeliveryAddressFragment.this.dialog.dismiss();
                                Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }

    }

    private void getAllCities () {
        if (user.isOnline(activity)) {
            final LocalStorage localStorage = new LocalStorage(getActivity());
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
                                    dialog.dismiss();

                                }
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }

    }

    private void listData (List<DilveryAddressDataModel> data) {

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getCities().size(); j++) {

                listcity.add(data.get(i).getCities().get(j).getName());
            }
        }

        Log.e("city", "" + listcity.size());
    }

}