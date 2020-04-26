package com.io.bookstores.fragment.courseFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.courseModel.CourseDataModel;
import com.io.bookstores.model.courseModel.EnrollCourseResponseModel;
import com.io.bookstores.model.guestModel.GuestEnrollCourseResponseModel;
import com.io.bookstores.model.guestModel.GuestResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CourseEnrollFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private CourseDataModel courseDataModel;
    private TextView tvCourseTitile, tvCourseDescription;
    private ImageView imageView;
    private Button btnEnroll;
    private EditText etName, etEmail, etPhone;
    private String token;
    private LocalStorage localStorage;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private LinearLayout layout;

    public CourseEnrollFragment(CourseDataModel courseDataModel) {
        this.courseDataModel = courseDataModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course_enrollment, container, false);
        intializeViews(view);
        bindListner();
        startWorking();
        return view;
    }

    /*-------------------------------------- intilize all Views that are used in this fragment --------------------------------*/

    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        dialog = new NewProgressBar(activity);
        localStorage = new LocalStorage(activity);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        etName = (EditText) view.findViewById(R.id.et_name);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPhone = (EditText) view.findViewById(R.id.et_number);
        token = localStorage.getString(LocalStorage.token);
        imageView = (ImageView) view.findViewById(R.id.imageView21);
        tvCourseTitile = (TextView) view.findViewById(R.id.tv_bookstore_tilte);
        tvCourseDescription = (TextView) view.findViewById(R.id.textView36);
        btnEnroll = (Button) view.findViewById(R.id.btn_enrolll_now);
    }

    /*-------------------------------------------- bind all Views that are used in this fragment -------------------------------*/

    private void bindListner() {
        btnEnroll.setOnClickListener(this);
    }

    /*---------------------------------------------------- on click Listner ---------------------------------------------------*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enrolll_now:
                if (token.equals("") || token == null) {
                    validateData();
                } else {
                    enrollApiCall();
                }

        }

    }

    /*------------------------------------------------------- start Working --------------------------------------------------*/

    private void startWorking() {
        ifGusestOrUser();
        corseDetialSetIntViews();
    }

    /*--------------------------------------------- if user is guest or login set Up of ui ----------------------------------*/

    private void ifGusestOrUser() {

        if (token.equals("") || token == null) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    /*------------------------------------------- course detial display on Views ----------------------------------------------*/

    private void corseDetialSetIntViews() {
        tvCourseTitile.setText(courseDataModel.getCourseName());
        tvCourseDescription.setText(courseDataModel.getCourseDescription());
        Glide.with(activity).load(Config.imageUrl + courseDataModel.getAvatarPath()).into(imageView);
    }

    /*--------------------------------------------- enroll course Api Call ----------------------------------------------------*/

    private void enrollApiCall() {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.enrollCourse(getActivity(), Config.Url.courseEnroll + courseDataModel.getCourseId(), token,
                    new FutureCallback<EnrollCourseResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, EnrollCourseResponseModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }

                            if(result != null){
                                if(result.getStatus()== null){
                                    if(result.getMessage().equals("Unauthorized")){
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }else {
                                    if (result.getStatus()) {
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        localStorage.putBooleAan(LocalStorage.isEnroll, false);
                                        localStorage.course(null);
                                        dialog.dismiss();
                                    }
                                }
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }
    /*------------------------------------------- validate data is it empty or not ------------------------------------------*/

    private void validateData() {
        String strName = etName.getText().toString().trim();
        String strEmail = etEmail.getText().toString().trim();
        String strPhone = etPhone.getText().toString().trim();
        if (strName.isEmpty() || strEmail.isEmpty() || strPhone.isEmpty()) {
            Utils.showAlertDialog(activity, "please enter fields");
        }
        if (strPhone.length() != 8) {
            Utils.showAlertDialog(activity, getResources().getString(R.string.phone_number_must_be_of_8));
            return;
        }

        if (!isEmailValid(strEmail)) {
            Utils.showAlertDialog(activity, getResources().getString(R.string.email_not_valid));
            return;
        }
            guestEnrollCourse(strName, strEmail, strPhone);


    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }


    /*------------------------------------------------- guest create Api Caller ----------------------------------------------*/

    private void guestEnrollCourse(String strName, String strEmail, String strPhone) {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.guestApi(activity, Config.Url.guestCreate, strName, strEmail, strPhone, new FutureCallback<GuestResponseModel>() {
                @Override
                public void onCompleted(Exception e, GuestResponseModel result) {
                    if (e != null) {
                        Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                        dialog.dismiss();
                    }

                    if (result != null) {
                        if (result.getStatus()) {
                            dialog.dismiss();
                            enrollCourseGuest(result.getData().getGuestId(), courseDataModel.getCourseId());
                        }

                    }
                }
            });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }


    }

    /*-------------------------------------------------- guest enroll ap caller -----------------------------------------------*/

    private void enrollCourseGuest(Long guestId, Integer courseId) {
        if (user.isOnline(activity)) {

            ApiCaller.guestEnrollCourse(activity, Config.Url.guestEnrollCourse, courseId, guestId, new FutureCallback<GuestEnrollCourseResponseModel>() {
                @Override
                public void onCompleted(Exception e, GuestEnrollCourseResponseModel result) {
                    if (e != null) {
                        Utils.showAlertDialog(getActivity(), "Something Went Wrong");

                    }

                    if (result != null) {
                        if (result.getStatus()) {

                            Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(activity, MainActivity.class);
                            startActivity(i);
                        }

                    }
                }
            });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }

    }

}
