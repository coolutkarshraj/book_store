package com.io.bookstore.fragment.bookStoreFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.activity.profile.EditProfileFragment;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.fragment.SettingsFragment;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.model.store.StoreDetailResponseModel;
import com.io.bookstore.model.storeDetailsModel.StoreData;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.model.updatePasswordModel.UpdatePasswordModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.io.bookstore.activity.profile.EditProfileFragment.avatar;

public class ProfileAdminFragment extends Fragment implements View.OnClickListener {

    Activity activity;
    public static CircleImageView iv_avatar;
    private TextView loggedih, tvName, tvPhone, tvAddress,tvFirstName, tvEmail, tvEdit1, edit1, edit2, edit3, edit4, changepassword;
    private LinearLayout ll_main_view;
    private ImageView edi_profile;
    AdminEditStoreFragment adminEditStoreFragment;
    private LocalStorage localStorage;
    private LoginModel loginModel;
    private int storeId;
    private String strOldPassword,strNewPassword;
    private NewProgressBar dialog;
    private userOnlineInfo user;

    public ProfileAdminFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_profile_layout, container, false);
        intializeViews(view);
        bindListner();
        startWorking();
        return view;

    }


    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
        loginModel = localStorage.getUserProfile();
        adminEditStoreFragment = new AdminEditStoreFragment();
        iv_avatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
        loggedih = (TextView) view.findViewById(R.id.loggedih);
        ll_main_view = (LinearLayout) view.findViewById(R.id.ll_main_view);
        tvName = (TextView) view.findViewById(R.id.et_username);
        tvEmail = (TextView) view.findViewById(R.id.et_email);
        tvPhone = (TextView) view.findViewById(R.id.et_phone);
        tvAddress = (TextView) view.findViewById(R.id.et_address);
        tvFirstName = (TextView) view.findViewById(R.id.et_firstname);
        edi_profile = (ImageView) view.findViewById(R.id.edi_profile);
        edit1 = view.findViewById(R.id.edit1);
        edit2 = view.findViewById(R.id.edit2);
        edit3 = view.findViewById(R.id.edit3);
        edit4 = view.findViewById(R.id.edit4);
        changepassword = view.findViewById(R.id.changepassword);

    }

    private void bindListner() {
        loggedih.setOnClickListener(this);
        changepassword.setOnClickListener(this);
        edi_profile.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loggedih:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                return;
            case R.id.changepassword:
                changePassword();
                return;
            case R.id.edi_profile:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, adminEditStoreFragment)
                        .addToBackStack(null)
                        .commit();
                return;

        }

    }

    private void startWorking() {
        getDataFromLocalStorage();
        getStoreDetialApiCall();

    }



    private void getDataFromLocalStorage() {
        if (loginModel == null) {
            ll_main_view.setVisibility(View.GONE);
            loggedih.setVisibility(View.VISIBLE);
        } else {
            ll_main_view.setVisibility(View.VISIBLE);
            loggedih.setVisibility(View.GONE);
            storeId = loginModel.getData().getUser().getStoreId();

        }
    }
    private void getStoreDetialApiCall() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            ApiCaller.getStoreDetial(activity, Config.Url.getStoreDetail+storeId,
                    new FutureCallback<StoreDetailResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, StoreDetailResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if(result != null){
                                if(result.getStatus()== null){
                                    if(result.getMessage().equals("Unauthorized")){
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getInt(LocalStorage.userId));

                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }else {
                                    dialog.dismiss();
                                    getdataSetIntoViews(result);
                                }
                            }



                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void getdataSetIntoViews(StoreDetailResponseModel result) {
        if(result.getStatus() == true){
            tvPhone.setText(result.getData().getPhone());
            tvEmail.setText(result.getData().getEmail());
            tvAddress.setText(result.getData().getAddress().getAddress());
            tvFirstName.setText(result.getData().getName());
            tvName.setText(result.getData().getDescription());
            Glide.with(activity).load(Config.imageUrl +result.getData().getAvatarPath()).into(iv_avatar);
        }else {
            Toast.makeText(activity, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
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
        }else if(strNewPassword.length()<5){
            oldPassword.setError("Please Enter Minimum 6 Digit Password");
        } else {
            changePasswordApi(dialog);
        }

    }

    private void changePasswordApi(final Dialog dialog) {
        if (user.isOnline(getActivity())) {
            this.dialog = new NewProgressBar(getActivity());
            this.dialog.show();
            ApiCaller.changePassword(getActivity(), Config.Url.storePasswordChange, strOldPassword, strNewPassword, localStorage.getString(LocalStorage.token),
                    new FutureCallback<UpdatePasswordModel>() {
                        @Override
                        public void onCompleted(Exception e, UpdatePasswordModel result) {
                            if(e!=null){
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if(result != null){
                                if(result.getStatus()== null){
                                    if(result.getMessage().equals("Unauthorized")){
                                        Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getInt(LocalStorage.userId));
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }else {
                                    ProfileAdminFragment.this.dialog.dismiss();
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
