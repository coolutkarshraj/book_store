package com.io.bookstore.fragment.bookStoreFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.io.bookstore.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileAdminFragment extends Fragment {

    Activity activity;

    public ProfileAdminFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_book_layout, container, false);
        intializeViews(view);
        bindListner();
        return view;

    }

    private void intializeViews(View view) {
        activity = getActivity();
    }

    private void bindListner() {
    }
}
