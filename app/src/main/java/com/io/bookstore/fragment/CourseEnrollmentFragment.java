package com.io.bookstore.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.io.bookstore.R;
import com.io.bookstore.model.courseModel.CourseDataModel;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CourseEnrollmentFragment extends Fragment {

    public CourseEnrollmentFragment(CourseDataModel courseDataModel) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater .inflate(R.layout.fragment_course_enrollment,container,false);
        return view;
    }

}
