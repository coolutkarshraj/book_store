package com.io.bookstore.adapter.categoryAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragmentAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mfragments = new ArrayList<>();

    public CategoryFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment f1) {
        mfragments.add(f1);
    }

    @Override
    public Fragment getItem(int i) {
        return mfragments.get(i);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }
}
