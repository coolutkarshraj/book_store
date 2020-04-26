package com.io.bookstores.fragment.bookStoreFragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.activity.homeActivity.ui.home.HomeFragment;
import com.io.bookstores.adapter.categoryAdapter.AddressSliderAdapter;
import com.io.bookstores.model.dilvery.DilveryAddressDataModel;

import java.util.ArrayList;
import java.util.List;


public class AddressSliderFragment extends Fragment {


    private Activity activity;
    private RecyclerView recyclerView;
    private int sizeo = 0;
    private int i = 0;
    private List<DilveryAddressDataModel> listd = new ArrayList<>();
    private List<DilveryAddressDataModel> item = new ArrayList<>();

    public AddressSliderFragment(List<DilveryAddressDataModel> listd) {
        this.listd = listd;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_slider, container, false);
        setRetainInstance(true);
        intailizeViews(view);
        recyclerViewSetUp();
        return view;
    }

    /*----------------------------------------------- intialize all views that are used ---------------------------------------*/

    private void intailizeViews(View view) {
        recyclerView = view.findViewById(R.id.rv_address);
    }

    /*---------------------------------------------- set up of recycler View -------------------------------------------------*/

    private void recyclerViewSetUp() {
        item.clear();
        if (HomeFragment.viewPager.getCurrentItem() == 0) {
            sizeo = 0;
            i = 0;
        } else if (HomeFragment.viewPager.getCurrentItem() > 0) {
            sizeo = 6 * HomeFragment.viewPager.getCurrentItem();
            i = 6 * HomeFragment.viewPager.getCurrentItem();
        }

        for (i = sizeo; i <= listd.size() - 1; i++) {
            if (i < sizeo + 6) {
                item.add(listd.get(i));
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        AddressSliderAdapter addressSliderAdapter = new AddressSliderAdapter(getActivity(), item);
        recyclerView.setAdapter(addressSliderAdapter);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
