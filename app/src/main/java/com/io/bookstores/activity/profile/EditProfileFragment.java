package com.io.bookstores.activity.profile;


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
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstores.model.editProfileResponseModel.EditProfileResponseModel;
import com.io.bookstores.utility.ImageUtility;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.PermissionFile;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    View view;
    EditText fNmae, lName, username, phone, email, address;
     public static CircleImageView avatar;
    private Bitmap bitmap;
    Button btnSave;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    ProfileFragment profileFragment;
    FloatingActionButton fabChooseImage;
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
    LocalStorage localStorage;
    private ImageView iv_back;
    private ItemClickListner itemClickListner;




    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        initView();
        bindListner();
        getaddressListApi();
        return view;
    }

    private void getaddressListApi() {
        final Activity activity = getActivity();
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.getUserSavedAddressList(activity, Config.Url.getAddressList,localStorage.getString(LocalStorage.token),
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
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();
                                    phone.setText(result.getData().getPhone());
                                    email.setText(result.getData().getEmail());
                                    address.setText(result.getData().getAddress());
                                    username.setText(result.getData().getName());
                                    if (result.getData().getAvatarPath() == null) {
                                        Glide.with(getActivity()).load(R.drawable.person_logo).into(avatar);
                                    } else {
                                        Glide.with(getActivity()).load(Config.imageUrl + result.getData().getAvatarPath()).into(avatar);
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
    private void initView() {
        iv_back = view.findViewById(R.id.iv_back);
        itemClickListner = (ItemClickListner)getActivity();
        fNmae = view.findViewById(R.id.et_firstname);
        email = view.findViewById(R.id.et_email);
        username = view.findViewById(R.id.et_username);
        phone = view.findViewById(R.id.et_phone);
        address = view.findViewById(R.id.et_deliver_address);
        avatar = view.findViewById(R.id.iv_avatar);
        fabChooseImage = view.findViewById(R.id.fab_chooseimage);
        btnSave = view.findViewById(R.id.btn_save);
        user = new userOnlineInfo();
        localStorage = new LocalStorage(getActivity());
        permissionFile = new PermissionFile(getActivity());
        imageUtility = new ImageUtility(getActivity());
        readWritePermission();
        multiplePermission();
    }

    private void bindListner() {

        fabChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ediProilevalidate();
                profileFragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner.onClick(6);
            }
        });


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



    /*-----------------------------------------------------------------------API CALL------------------------------------------------------------------

    private void updateProfile() {
        final String f_name = fNmae.getText().toString().trim();
        String l_name = lName.getText().toString().trim();
        String u_emael = email.getText().toString().trim();
        String et_userName = username.getText().toString().trim();
        String et_number = phone.getText().toString().trim();
        String m_address = address.getText().toString().trim();

        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.editProfile(this, Config.Url.editProfile,et_userName
                    , u_emael, et_number, f_name,l_name,m_address,
                    new FutureCallback<EditProfileResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, EditProfileResponseModel result) {
                            if(e!=null){
                                return;
                            }

                            f_name="";

                        }
                    });
        }else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }





     */

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
                avatar.setImageBitmap(myBitmap);
            }

        } else {
            licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI(getActivity(), data.getData()));

            Log.e("gallerypic", licenseFile);

            imgFile = new File(licenseFile);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                avatar.setImageBitmap(myBitmap);
            }
        }
    }

    private void ediProilevalidate() {
        String Name = username.getText().toString();
        String Address = address.getText().toString();
        String Phone = phone.getText().toString();
        if(Phone.equals("") || Address.equals("")|| Name.equals("")){
            Toast.makeText(getActivity(), "please enter all fields", Toast.LENGTH_SHORT).show();
        }else {
            editProfile(Name,Address,Phone);
        }

    }

    private void editProfile(String name, String address, String phone){
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(getActivity());
            ApiCaller.editProfileUser(getActivity(), Config.Url.editProfile,name,address,phone, localStorage.getString(LocalStorage.token),imgFile,
                    new FutureCallback<EditProfileResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, EditProfileResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }

                                } else {
                                    if (result.getStatus() == true) {
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
