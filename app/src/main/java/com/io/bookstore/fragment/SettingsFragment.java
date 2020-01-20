package com.io.bookstore.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.profile.EditProfileFragment;
import com.io.bookstore.activity.profile.ProfileFragment;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.model.updatePasswordModel.UpdatePasswordModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView edit1, edit2, edit3, edit4, et_username, et_phone, et_email, et_address, changepassword;
    EditProfileFragment editProfileFragment;
    LocalStorage localStorage;
    userOnlineInfo user;
    NewProgressBar dialog;
    String strOldPassword, strNewPassword;

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        initView();
        bindListner();

        getaddressListApi();
        return view;
    }


    private void initView() {
        user = new userOnlineInfo();
        localStorage = new LocalStorage(getActivity());
        et_username = view.findViewById(R.id.et_username);
        et_phone = view.findViewById(R.id.et_phone);
        et_email = view.findViewById(R.id.et_email);
        et_address = view.findViewById(R.id.et_address);
        edit1 = view.findViewById(R.id.edit1);
        edit2 = view.findViewById(R.id.edit2);
        edit3 = view.findViewById(R.id.edit3);
        edit4 = view.findViewById(R.id.edit4);
        changepassword = view.findViewById(R.id.changepassword);
    }

    private void bindListner() {
        changepassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changepassword:
                changePassword();
                return;
        }

    }

    private void getaddressListApi() {
        final Activity activity = getActivity();
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            LocalStorage localStorage = new LocalStorage(getActivity());
            ApiCaller.getUserSavedAddressList(activity, Config.Url.getAddressList, localStorage.getString(LocalStorage.token),
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
                         Glide.with(getActivity()).load(Config.imageUrl + result.getData().getAvatarPath()).into(ProfileFragment.iv_avatar);

                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    /*------------------------------------------------- change password dialog---------------------------------------------*/
    public void changePassword() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.change_password_dialog);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.yes);
        final Button No = (Button) dialog.findViewById(R.id.no);
        final EditText oldPassword = (EditText) dialog.findViewById(R.id.et_old_password);
        final EditText newPassword = (EditText) dialog.findViewById(R.id.et_new_password);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordValidateData(oldPassword, newPassword, dialog);
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
    /*--------------------------------------------------- Change Password validate data---------------------------------------*/

    private void changePasswordValidateData(EditText oldPassword, EditText newPassword, Dialog dialog) {
        strOldPassword = oldPassword.getText().toString().trim();
        strNewPassword = newPassword.getText().toString().trim();
        if (strOldPassword.equals("") || strNewPassword.equals("")) {
            oldPassword.setError("Please enter old password");
            newPassword.setError("Please enter new password");
        } else {
            changePasswordApi(dialog);
        }

    }

    private void changePasswordApi(final Dialog dialog) {
        if (user.isOnline(getActivity())) {
            this.dialog = new NewProgressBar(getActivity());
            this.dialog.show();
            ApiCaller.changePassword(getActivity(), Config.Url.changepassword, strOldPassword, strNewPassword, localStorage.getString(LocalStorage.token),
                    new FutureCallback<UpdatePasswordModel>() {
                        @Override
                        public void onCompleted(Exception e, UpdatePasswordModel result) {
                            if(e!=null){
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }

                            if(result != null){
                                if(result.getStatus()){
                                    SettingsFragment.this.dialog.dismiss();
                                    dialog.dismiss();
                                    changePasswordData(result);
                                }
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void changePasswordData(UpdatePasswordModel result) {
        if (result.getStatus() == true) {
            Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
