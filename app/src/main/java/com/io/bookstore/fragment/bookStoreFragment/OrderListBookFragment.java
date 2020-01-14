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

public class OrderListBookFragment extends Fragment {

    Activity activity;

    public OrderListBookFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_book_list_layout, container, false);
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
