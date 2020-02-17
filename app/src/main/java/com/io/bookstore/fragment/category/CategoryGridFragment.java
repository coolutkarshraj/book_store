package com.io.bookstore.fragment.category;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.io.bookstore.R;
import com.io.bookstore.adapter.categoryAdapter.CategoryFragmentAdapter;
import com.io.bookstore.fragment.CategoryListFragment;


public class CategoryGridFragment extends Fragment implements View.OnClickListener {


    private ViewPager viewPager;
    private CategoryFragmentAdapter adapter;
    private Activity activity;
    private RelativeLayout rlViewAllCategory;
    private CategoryListFragment categoryListFragment;

    public CategoryGridFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_grid, container, false);
        intializeViews(view);
        bindListner();
        return view;
    }


    private void intializeViews(View view) {
        activity = getActivity();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        rlViewAllCategory = (RelativeLayout) view.findViewById(R.id.iv_view_all_category);
        setUpFragment(viewPager);
    }

    private void bindListner() {
        rlViewAllCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.iv_view_all_category:
                categoryListFragment = new CategoryListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, categoryListFragment)
                        .addToBackStack(null)
                        .commit();
                return;
        }
    }

    private void setUpFragment(ViewPager viewPager) {
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(((FragmentActivity) activity).getSupportFragmentManager());
        adapter.addFragment(new CategoryFirstFragment());
        adapter.addFragment(new CategorySecondFragment());
        adapter.addFragment(new CategoryThirdFragment());
        viewPager.setAdapter(adapter);
    }


}
