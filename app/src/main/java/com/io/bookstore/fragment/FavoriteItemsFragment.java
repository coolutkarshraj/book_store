package com.io.bookstore.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.adapter.FavoriteItemsAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.BookModel;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.model.wishlistModel.AddorRemoveWishlistDataModel;
import com.io.bookstore.model.wishlistModel.AddorRemoveWishlistResponseModel;
import com.io.bookstore.model.wishlistModel.GetWishListDataModel;
import com.io.bookstore.model.wishlistModel.GetWishlistResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

public class FavoriteItemsFragment extends Fragment {
    private ArrayList lstBook;
    private RecyclerView recyclerView;
    private FavoriteItemsAdapter favoriteItemsAdapter;
    private LocalStorage localStorage;
    private TextView loggedih;
    private NestedScrollView nested_c_view;
    private userOnlineInfo user;
    private Activity activity;
    private NewProgressBar dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite_items, container, false);
        recyclerView = root.findViewById(R.id.fav_recyclerView);
        loggedih = root.findViewById(R.id.loggedih);
        nested_c_view = root.findViewById(R.id.nested_c_view);
        user = new userOnlineInfo();
        activity = getActivity();
        localStorage = new LocalStorage(getActivity());
        LoginModel loginModel = localStorage.getUserProfile();
        System.out.println(loginModel);
        if(localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")){
            nested_c_view.setVisibility(View.GONE);
            loggedih.setVisibility(View.VISIBLE);
        } else {
            nested_c_view.setVisibility(View.VISIBLE);
            loggedih.setVisibility(View.GONE);

            getWishListApiCall();
        }
        bindListner();
        return root;
    }

    private void getWishListApiCall() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.getWishList(activity, Config.Url.getWishList, localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetWishlistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetWishlistResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                setRecyclerViewData(result.getData());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void setRecyclerViewData(List<GetWishListDataModel> data) {


        FavoriteItemsAdapter myAdapter = new FavoriteItemsAdapter(activity, data);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(myAdapter);

    }

    private void bindListner() {
        loggedih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}