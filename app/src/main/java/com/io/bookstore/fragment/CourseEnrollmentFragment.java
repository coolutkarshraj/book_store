package com.io.bookstore.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.io.bookstore.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseEnrollmentFragment extends Fragment {

    public CourseEnrollmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_enrollment, container, false);
    }

}
