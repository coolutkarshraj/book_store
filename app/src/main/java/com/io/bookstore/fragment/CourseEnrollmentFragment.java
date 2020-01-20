package com.io.bookstore.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.io.bookstore.R;
import com.io.bookstore.model.courseModel.CourseDataModel;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CourseEnrollmentFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private CourseDataModel courseDataModel;
    private TextView tvCourseTitile, tvCourseDescription;
    private Button btnEnroll;

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
                return;
        }

    }
    private void startWorking() {
       corseDetialSetIntViews();
    }

    private void corseDetialSetIntViews() {
        tvCourseTitile.setText(courseDataModel.getCourseName());
        tvCourseDescription.setText(courseDataModel.getCourseDescription());
    }

}
