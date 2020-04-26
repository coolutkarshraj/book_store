package com.io.bookstores.fragment.basicFragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.authentication.LoginActivity;
import com.io.bookstores.adapter.basicAdapter.FavoriteItemsAdapter;
import com.io.bookstores.adapter.basicAdapter.FavoriteItemsLocalAdapter;
import com.io.bookstores.adapter.basicAdapter.FavoriteItemsSchoolAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.WishListLocalResponseModel;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.schoolWishlist.GetAllSchoolWishListDataModel;
import com.io.bookstores.model.schoolWishlist.GetAllSchoolWishListResponseModel;
import com.io.bookstores.model.wishlistModel.GetWishListDataModel;
import com.io.bookstores.model.wishlistModel.GetWishlistResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteItemsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList lstBook;
    private RecyclerView recyclerView;
    private FavoriteItemsAdapter favoriteItemsAdapter;
    private LocalStorage localStorage;
    private TextView loggedih, wishlist;
    private NestedScrollView nested_c_view;
    private userOnlineInfo user;
    private Activity activity;
    private NewProgressBar dialog;
    private View root;
    private ImageView iv_back;
    private ItemClickListner itemClickListner;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<WishListLocalResponseModel> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.fragment_favorite_items, container, false);
        intializeViews(root);
        return root;
    }

    private void intializeViews(View root) {
        recyclerView = root.findViewById(R.id.fav_recyclerView);
        loggedih = root.findViewById(R.id.loggedih);
        itemClickListner = (ItemClickListner)getActivity();
        iv_back = root.findViewById(R.id.iv_back);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        nested_c_view = root.findViewById(R.id.nested_c_view);
        wishlist = root.findViewById(R.id.wishlist);
        user = new userOnlineInfo();
        activity = getActivity();
        localStorage = new LocalStorage(getActivity());
        LoginModel loginModel = localStorage.getUserProfile();
        System.out.println(loginModel);
        if (localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")) {
            nested_c_view.setVisibility(View.VISIBLE);
            loggedih.setVisibility(View.GONE);
            getWishListStatus();
          /*  nested_c_view.setVisibility(View.GONE);
            loggedih.setVisibility(View.VISIBLE);*/
        } else {
            if (localStorage.getString(LocalStorage.TYPE).equals("store")) {
                nested_c_view.setVisibility(View.VISIBLE);
                loggedih.setVisibility(View.GONE);
                getWishListApiCall();
            } else {
                nested_c_view.setVisibility(View.VISIBLE);
                loggedih.setVisibility(View.GONE);
                getWishListApiCallSchool();
            }
        }
        bindListner();
    }

    private void getWishListApiCall() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.getWishList(activity, Config.Url.getWishList, localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetWishlistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetWishlistResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if(result != null){
                                if(result.getStatus()== null){
                                    if(result.getMessage().equals("Unauthorized")){
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }else {
                                    if (result.getStatus() == true) {
                                        setRecyclerViewData(result.getData());
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }



                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void setRecyclerViewData(List<GetWishListDataModel> data) {

        if (data.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            wishlist.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            wishlist.setVisibility(View.GONE);
            FavoriteItemsAdapter myAdapter = new FavoriteItemsAdapter(activity, data);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(myAdapter);
        }

    }

    private void getWishListApiCallSchool() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.getWishListschool(activity, Config.Url.getWishListSchool, localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetAllSchoolWishListResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetAllSchoolWishListResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {

                                if (result.isStatus() == true) {
                                    setRecyclerViewDataSchool(result.getData());
                                    dialog.dismiss();
                                } else {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                }

                            }


                        }
                    });

        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void setRecyclerViewDataSchool(List<GetAllSchoolWishListDataModel> data) {
        if (data.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            wishlist.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            wishlist.setVisibility(View.GONE);
            FavoriteItemsSchoolAdapter schoolAdapter = new FavoriteItemsSchoolAdapter(activity, data);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(schoolAdapter);
        }
    }


    private void bindListner() {
        loggedih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner.onClick(6);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    private void getWishListStatus() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(getActivity());
        Cursor cursor = dbHelper.getAllWishlist();
        if (cursor.getCount() == 0) {
            Log.e("Error", "no GuestDataModel");
            return;
        }
        JSONArray resultSet = new JSONArray();
        JSONObject returnObj = new JSONObject();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME2", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME1", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        String datajson = resultSet.toString();
        datajson.replaceAll("\\\\", "");
        Log.d("wishlistAll", datajson);
        getWishListAllData(datajson);

    }

    private void getWishListAllData(String datajson) {
        JSONArray jArray = null;

        try {
            list.clear();
            jArray = new JSONArray(datajson);
            if (jArray == null || jArray.length() == 0) {
                recyclerView.setVisibility(View.GONE);
                wishlist.setVisibility(View.VISIBLE);
            } else {

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    WishListLocalResponseModel shoppingBagModel = new WishListLocalResponseModel();
                    shoppingBagModel.setId(json_data.getString("Id"));
                    shoppingBagModel.setName(json_data.getString("Name"));
                    shoppingBagModel.setQuantity(json_data.getString("Quantity"));
                    shoppingBagModel.setPrice(json_data.getString("Price"));
                    shoppingBagModel.setImage(json_data.getString("Image"));
                    shoppingBagModel.setAvailibleQty(json_data.getString("avalible"));
                    shoppingBagModel.setpID(json_data.getString("P_ID"));
                    shoppingBagModel.setSize(json_data.getString("size"));
                    shoppingBagModel.setType(json_data.getString("type"));
                    shoppingBagModel.setWishlist(json_data.getString("wishlist"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));
                    list.add(shoppingBagModel);

                }
                if (list.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    wishlist.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    wishlist.setVisibility(View.GONE);
                    FavoriteItemsLocalAdapter myAdapter = new FavoriteItemsLocalAdapter(activity, list);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recyclerView.setAdapter(myAdapter);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intializeViews(root);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}