package com.io.bookstore.fragment.bookStoreFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.model.store.EditStoreDetialResponseModel;
import com.io.bookstore.model.store.StoreDetailResponseModel;
import com.io.bookstore.utility.ImageUtility;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.PermissionFile;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AdminEditStoreFragment extends Fragment implements View.OnClickListener {

    Activity activity;
    public static CircleImageView iv_avatar;
    private TextView loggedih, tvName, tvPhone, tvAddress, tvFirstName, tvEmail, tvEdit1, edit1, edit2, edit3, edit4, changepassword;
    private LinearLayout ll_main_view;
    private ImageView edi_profile;
    Button btn_save;
    FloatingActionButton fab_editpic;
    ProfileAdminFragment profileAdminFragment;
    private LocalStorage localStorage;
    private LoginModel loginModel;
    private int storeId,strAddressId,strZip;
    private String strStoreName, strDescrption, strPhone, strEmail, strAddrss,
            strImage,strAddresLocality,strAddCity,strLandarrk,strAddressJson;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private static final int REQUEST_WRITE_STORAGE = 1004;
    private static int GalleryPicker = 123;
    private PermissionFile permissionFile;
    private String licenseFile = "";
    private ImageUtility imageUtility;
    private File destination;
    private Uri outputFileUri;
    int CameraPicker = 124;
    File imagefile;
    private File imgFile;

    public AdminEditStoreFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_edit_store, container, false);
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
        profileAdminFragment = new ProfileAdminFragment();
        permissionFile = new PermissionFile(getActivity());
        imageUtility = new ImageUtility(getActivity());
        iv_avatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
        ll_main_view = (LinearLayout) view.findViewById(R.id.ll_main_view);
        tvName = (EditText) view.findViewById(R.id.et_username);
        tvEmail = (EditText) view.findViewById(R.id.et_email);
        tvPhone = (EditText) view.findViewById(R.id.et_phone);
        tvAddress = (EditText) view.findViewById(R.id.et_address);
        tvFirstName = (EditText) view.findViewById(R.id.et_firstname);
        edi_profile = (ImageView) view.findViewById(R.id.edi_profile);
        changepassword = view.findViewById(R.id.changepassword);
        btn_save = view.findViewById(R.id.btn_save);
        fab_editpic = view.findViewById(R.id.fab_editpic);

    }

    private void bindListner() {
        btn_save.setOnClickListener(this);
        fab_editpic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save: {
                validateData();

                return;
            }
            case R.id.fab_editpic:{
                galleryIntent();
                return;
            }
        }
    }



    private void startWorking() {
        readWritePermission();
        multiplePermission();
        getDataFromLocalStorage();
        getStoreDetialApiCall();
    }

    private void getDataFromLocalStorage() {
        storeId = loginModel.getData().getUser().getStoreId();

    }

    private void getStoreDetialApiCall() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            ApiCaller.getStoreDetial(activity, Config.Url.getStoreDetail + storeId,
                    new FutureCallback<StoreDetailResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, StoreDetailResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }
                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getInt(LocalStorage.userId));

                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
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
        if (result.getStatus() == true) {
            strPhone = result.getData().getPhone();
            strAddressId = result.getData().getAddress().getAddressId();
            strLandarrk = result.getData().getAddress().getLandmark();
            strAddresLocality = result.getData().getAddress().getLocality();
            strLandarrk = result.getData().getAddress().getLandmark();
            strAddCity = result.getData().getAddress().getCity();
            strZip = result.getData().getAddress().getZipcode();

            strAddrss = result.getData().getAddress().getAddress();
            strStoreName = result.getData().getName();
            strEmail = result.getData().getEmail();
            strDescrption = result.getData().getDescription();
            strImage = result.getData().getAvatarPath();
            dataSetEditText();

        } else {
            Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void dataSetEditText() {
        tvPhone.setText(strPhone);
        tvEmail.setText(strEmail);
        tvAddress.setText(strAddrss);
        tvFirstName.setText(strStoreName);
        tvName.setText(strDescrption);
        Glide.with(activity).load(Config.imageUrl + strImage).into(iv_avatar);

    }
    private void readWritePermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    private void multiplePermission() {
        if (!permissionFile.checkLocStorgePermission(getActivity())) {
            permissionFile.checkLocStorgePermission(getActivity());
        }
    }

    private void galleryIntent() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, GalleryPicker);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == GalleryPicker) {
                onCaptureImageResult(data, "gallery");
            }
        } else if (resultCode == RESULT_OK && requestCode == CameraPicker) {
            onCaptureImageResult(data, "camera");

        }
    }

    /* --------------------------------- get the actual storage path of image (Camera an dgallery) ----------------------------------*/

    void onCaptureImageResult(Intent data, String imageType) {
        if (imageType.equals("camera")) {
            licenseFile = imageUtility.compressImage(destination.getPath());
            Log.e("camerapic", licenseFile);
            imagefile = new File(licenseFile);
            if (imagefile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());
                iv_avatar.setImageBitmap(myBitmap);
            }

        } else {
            licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI(getActivity(), data.getData()));

            Log.e("gallerypic", licenseFile);

            imgFile = new File(licenseFile);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_avatar.setImageBitmap(myBitmap);
            }
        }
    }

    private void validateData() {
        strStoreName = tvFirstName.getText().toString();
        strDescrption = tvName.getText().toString();
        strPhone = tvPhone.getText().toString();
        strEmail = tvEmail.getText().toString();
        strAddrss = tvAddress.getText().toString();
        if(strStoreName.equals("") || strDescrption.equals("")||strEmail.equals("")|| strAddrss.equals("") || strPhone.equals("")){
            Toast.makeText(activity, "please enter all fields", Toast.LENGTH_SHORT).show();
        }else {
            getAddressJson();
            editProfile(strStoreName,strDescrption,strPhone,strAddressJson,imgFile);
        }

    }

    private void getAddressJson() {
        JSONObject postData = new JSONObject();
        try {
            postData.put("addressId",strAddressId );
            postData.put("address", strAddrss);
            postData.put("locality", strAddresLocality);
            postData.put("city",strAddCity);
            postData.put("zipcode", strZip);
            strAddressJson = postData.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void editProfile(String strStoreName, String strDescrption, String strPhone, String strAddressJson, File imgFile){
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(getActivity());
            ApiCaller.editstoreDetial(getActivity(),Config.Url.storeEdit,strStoreName,strDescrption,strPhone,localStorage.getString(LocalStorage.token),imgFile,strAddressJson,
                    new FutureCallback<EditStoreDetialResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, EditStoreDetialResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    if (result.getStatus() == true) {
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.content_view, profileAdminFragment)
                                                .addToBackStack(null)
                                                .commit();
                                        Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();

                                }
                            }




                        }
                    });

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }


}
