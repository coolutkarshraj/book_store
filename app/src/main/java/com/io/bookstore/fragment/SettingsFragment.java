package com.io.bookstore.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.profile.EditProfileFragment;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

public class SettingsFragment extends Fragment {

    View view;
    TextView edit1, edit2, edit3, edit4,et_username,et_phone,et_email,et_address;
    EditProfileFragment editProfileFragment;
    userOnlineInfo user;
    NewProgressBar dialog;

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings,container,false);
        initView();
        user = new userOnlineInfo();
        getaddressListApi();
        return view;
    }



    private void initView() {
        et_username = view.findViewById(R.id.et_username);
        et_phone = view.findViewById(R.id.et_phone);
        et_email = view.findViewById(R.id.et_email);
        et_address = view.findViewById(R.id.et_address);
        edit1 = view.findViewById(R.id.edit1);
        edit2 = view.findViewById(R.id.edit2);
        edit3 = view.findViewById(R.id.edit3);
        edit4 = view.findViewById(R.id.edit4);
    }

    private void getaddressListApi() {
        final Activity activity = getActivity();
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            ApiCaller.getUserSavedAddressList(activity, Config.Url.getAddressList,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNTc4ODQ5NzQxLCJleHAiOjE1Nzg5MzYxNDF9.HiddwD9LwLH81wTxNycUnvQqAVMu7f7kepL2b2cYErg",
                    new FutureCallback<GetAddressListResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetAddressListResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            dialog.dismiss();
                            et_phone.setText(result.getData().getPhone());
                            et_email.setText(result.getData().getEmail());
                            et_address.setText(result.getData().getAddress());
                            et_username.setText(result.getData().getName());
                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }


}
