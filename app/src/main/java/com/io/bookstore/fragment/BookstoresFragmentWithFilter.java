package com.io.bookstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.adapter.BookstoresAdapter;
import com.io.bookstore.adapter.BookstoresFilterAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.model.filterByAddress.FilterAddressModel;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookstoresFragmentWithFilter extends Fragment {

    private ArrayList lstBook;
    private RecyclerView recyclerView;
    private BookstoresFilterAdapter bookstoresAdapter;
    View root;
    private userOnlineInfo user;
    private NewProgressBar dialog;



    public BookstoresFragmentWithFilter() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bookstores, container, false);
        initView();
        getStoreList();
        return root;
    }

    private void initView() {
        user = new userOnlineInfo();
        dialog = new NewProgressBar(getActivity());
        recyclerView = root.findViewById(R.id.recyclerView_bookstore);
    }


    private void getStoreList() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getfilterData(getActivity(), Config.Url.getStoreApifilter, StaticData.address,
                    new FutureCallback<FilterAddressModel>() {

                        @Override
                        public void onCompleted(Exception e, FilterAddressModel result) {
                            dialog.dismiss();
                            setRecyclerViewData(result);

                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }




    private void setRecyclerViewData(FilterAddressModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookstoresAdapter = new BookstoresFilterAdapter(getActivity(),result.getData());
        recyclerView.setAdapter(bookstoresAdapter);
    }




}