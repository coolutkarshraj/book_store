package com.io.bookstores.fragment.category;

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
import com.io.bookstores.model.getAddressResponseModel.Datum;

import java.util.ArrayList;
import java.util.List;


public class AddressSliderFragment extends Fragment {



    private Activity activity;
    private RecyclerView recyclerView;
    int sizeo = 0;
    int i = 0;
    private  List<Datum> listd = new ArrayList<>();
    private  List<Datum> item = new ArrayList<>();

    public AddressSliderFragment(List<Datum> listd) {
       this.listd = listd;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_slider, container, false);
        intailizeViews(view);
        recyclerViewSetUp();
        return view;
    }



    private void intailizeViews(View view) {
        recyclerView = view.findViewById(R.id.rv_address);

    }

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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        AddressSliderAdapter addressSliderAdapter = new AddressSliderAdapter(getActivity(),item);
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
