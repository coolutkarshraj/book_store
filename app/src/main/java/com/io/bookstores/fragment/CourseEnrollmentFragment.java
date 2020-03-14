package com.io.bookstores.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.authentication.GuestLoginActivity;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.courseModel.CourseDataModel;
import com.io.bookstores.model.courseModel.EnrollCourseResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CourseEnrollmentFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private CourseDataModel courseDataModel;
    private TextView tvCourseTitile, tvCourseDescription;
    private ImageView imageView;
    private Button btnEnroll;
    private String token;
    private LocalStorage localStorage;
    private userOnlineInfo user;
    private NewProgressBar dialog;

    public CourseEnrollmentFragment(CourseDataModel courseDataModel) {
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

    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        dialog = new NewProgressBar(activity);
        localStorage = new LocalStorage(activity);
        token = localStorage.getString(LocalStorage.token);
        imageView = (ImageView) view.findViewById(R.id.imageView21);
        tvCourseTitile = (TextView) view.findViewById(R.id.tv_bookstore_tilte);
        tvCourseDescription = (TextView) view.findViewById(R.id.textView36);
        btnEnroll = (Button) view.findViewById(R.id.btn_enrolll_now);
    }

    private void bindListner() {
        btnEnroll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enrolll_now:

                if (token.equals("") || token == null) {
                    localStorage.putBooleAan(LocalStorage.isEnroll, true);
                    localStorage.course(courseDataModel);
                    Intent i = new Intent(activity, GuestLoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    enrollApiCall();
                }

                return;
        }

    }


    private void startWorking() {


        corseDetialSetIntViews();
    }

    private void corseDetialSetIntViews() {
        tvCourseTitile.setText(courseDataModel.getCourseName());
        tvCourseDescription.setText(courseDataModel.getCourseDescription());
        Glide.with(activity).load(Config.imageUrl + courseDataModel.getAvatarPath()).into(imageView);
    }

    private void enrollApiCall() {
        if (user.isOnline(getActivity())) {
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

}
