package com.io.bookstores.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.adapter.BookstoresFilterAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.model.filterStore.FilterStoreResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
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
    private ImageView iv_back;
    ItemClickListner itemClickListner;



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
        itemClickListner =(ItemClickListner)getActivity();
        dialog = new NewProgressBar(getActivity());
        iv_back = root.findViewById(R.id.iv_back);
        recyclerView = root.findViewById(R.id.recyclerView_bookstore);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner.onClick(6);
            }
        });
    }


    private void getStoreList() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getfilterData(getActivity(), Config.Url.getStoreApifilter, StaticData.address,
                    new FutureCallback<FilterStoreResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, FilterStoreResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                dialog.dismiss();
                                setRecyclerViewData(result);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }




    private void setRecyclerViewData(FilterStoreResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookstoresAdapter = new BookstoresFilterAdapter(getActivity(),result.getData());
        recyclerView.setAdapter(bookstoresAdapter);
    }




}