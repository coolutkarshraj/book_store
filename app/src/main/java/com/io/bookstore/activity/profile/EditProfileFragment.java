package com.io.bookstore.activity.profile;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.SignUpActivity;
import com.io.bookstore.activity.authentication.SignupVerifyActivity;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.model.editProfileResponseModel.EditProfileResponseModel;
import com.io.bookstore.model.registerModel.RegisterModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    View view;
    EditText fNmae, lName, username, phone, email, address;
    CircleImageView avatar;
    private Bitmap bitmap;
    Button btnSave;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    ProfileFragment profileFragment;
    FloatingActionButton fabChooseImage;



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
        return view;
    }


    private void initView() {
        fNmae = view.findViewById(R.id.et_firstname);
        lName = view.findViewById(R.id.et_lastname);
        email = view.findViewById(R.id.et_email);
        username = view.findViewById(R.id.et_username);
        phone = view.findViewById(R.id.et_phone);
        address = view.findViewById(R.id.et_deliver_address);
        avatar = view.findViewById(R.id.iv_avatar);
        fabChooseImage = view.findViewById(R.id.fab_chooseimage);
        btnSave = view.findViewById(R.id.btn_save);
        user = new userOnlineInfo();
    }

    private void bindListner() {

        fabChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateProfile();
                profileFragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


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

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                avatar.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
