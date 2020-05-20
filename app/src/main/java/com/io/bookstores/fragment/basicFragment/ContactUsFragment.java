package com.io.bookstores.fragment.basicFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.contactUs.ContactUsResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

public class ContactUsFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private EditText etEmail, etTitle, etMessage;
    private String strTitle, strEmail, strMessage;
    private Button submit;
    private ImageView iv_back;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private LocalStorage localStorage;
    private TextView tv_login;
    private NestedScrollView nestedScrollView;

    private ItemClickListner itemClickListner;

    public ContactUsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        intializeViews(view);
        bindListner();
        return view;
    }


    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        dialog = new NewProgressBar(activity);
        localStorage = new LocalStorage(activity);
        itemClickListner = (ItemClickListner) getActivity();
        etEmail = view.findViewById(R.id.etEmailc);
        etTitle = view.findViewById(R.id.etTitlec);
        etMessage = view.findViewById(R.id.etMessagec);
        submit = view.findViewById(R.id.btnSubmit);
        iv_back = view.findViewById(R.id.iv_back);
        tv_login = view.findViewById(R.id.tv_login);
        nestedScrollView = view.findViewById(R.id.nested);

        if (localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")) {
            nestedScrollView.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);
        } else {
            nestedScrollView.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.GONE);
        }
    }

    private void bindListner() {
        submit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                itemClickListner.onClick(6);
                return;

            case R.id.btnSubmit:
                validateData();
                return;
        }
    }

    private void validateData() {
        strTitle = etTitle.getText().toString().trim();
        strEmail = etEmail.getText().toString().trim();
        strMessage = etMessage.getText().toString().trim();
        if (strTitle.isEmpty() || strMessage.isEmpty() || strEmail.isEmpty()) {
            Toast.makeText(activity, "please enter data", Toast.LENGTH_SHORT).show();
        } else {
            apiCall(strEmail, strTitle, strMessage);
        }
    }

    private void apiCall(String strEmail, String strTitle, String strMessage) {
        if (user.isOnline(getActivity())) {
            dialog.show();
            ApiCaller.contactUs(activity, Config.Url.contactus, localStorage.getString(LocalStorage.token), strEmail, strTitle, strMessage, new FutureCallback<ContactUsResponseModel>() {
                @Override
                public void onCompleted(Exception e, ContactUsResponseModel result) {
                    if (e != null) {
                        dialog.dismiss();
                        Utils.showAlertDialog(activity, "Something Went Wrong");
                        return;
                    }

                    if (result != null) {
                        if (result.getStatus() == null) {
                            dialog.dismiss();
                            Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                        } else if (result.getStatus() == true) {
                            dialog.dismiss();
                            Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            etEmail.getText().clear();
                            etTitle.getText().clear();
                            etMessage.getText().clear();
                        } else {
                            if (result.getMessage().equals("Unauthorized")) {
                                Utils.showAlertDialogLogout(activity, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                dialog.dismiss();
                            }
                        }
                    }else {
                        dialog.dismiss();
                        Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                    }

                }
            });


        } else {

            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }
}
