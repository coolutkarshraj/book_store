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
import android.widget.LinearLayout;
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
import com.io.bookstores.listeners.RecyclerViewClickListener;
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

public class FavoriteItemsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {
    private ArrayList lstBook;
    private RecyclerView recyclerView, fav_recyclerView_schools, rv_local_wishlist;
    private FavoriteItemsAdapter favoriteItemsAdapter;
    private LocalStorage localStorage;
    private TextView loggedih, wishlist;
    private NestedScrollView nested_c_view;
    private userOnlineInfo user;
    private Activity activity;
    private NewProgressBar dialog;
    private LinearLayout ll_Shools, ll_books;
    private String isEmprty = "", isEmpty1 = "";
    private View root;
    private ImageView iv_back;
    private ItemClickListner itemClickListner;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerViewClickListener recyclerViewClickListener;
    List<WishListLocalResponseModel> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.fragment_favorite_items, container, false);
        intializeViews(root);
        return root;
    }

    private void intializeViews(View root) {
        recyclerViewClickListener = this;
        recyclerView = root.findViewById(R.id.fav_recyclerView);
        fav_recyclerView_schools = root.findViewById(R.id.fav_recyclerView_schools);
        rv_local_wishlist = root.findViewById(R.id.rv_local_wishlist);
        loggedih = root.findViewById(R.id.loggedih);
        ll_Shools = root.findViewById(R.id.ll_Shools);
        ll_books = root.findViewById(R.id.ll_books);
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
            nested_c_view.setVisibility(View.VISIBLE);
            loggedih.setVisibility(View.GONE);

            getWishListApiCall();


        }
        bindListner();
    }

    private void getWishListApiCall() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(activity);
            Log.e("token",localStorage.getString(localStorage.token));
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

                                        dialog.dismiss();

                                        setRecyclerViewData(result.getData());

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
            // recyclerView.setVisibility(View.GONE);
            //wishlist.setVisibility(View.VISIBLE);
            isEmprty = "empty";
            ll_books.setVisibility(View.GONE);
            getWishListApiCallSchool();
        } else {
            //recyclerView.setVisibility(View.VISIBLE);
            //wishlist.setVisibility(View.GONE);
            isEmprty = "";
            ll_books.setVisibility(View.VISIBLE);
            FavoriteItemsAdapter myAdapter = new FavoriteItemsAdapter(activity, data);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(myAdapter);
            getWishListApiCallSchool();
        }

    }

    private void getWishListApiCallSchool() {
        if (user.isOnline(activity)) {

            final LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.getWishListschool(activity, Config.Url.getWishListSchool, localStorage.getString(LocalStorage.token),
                    new FutureCallback<GetAllSchoolWishListResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetAllSchoolWishListResponseModel result) {
                            if (e != null) {

                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {

                                if (result.isStatus() == true) {

                                    setRecyclerViewDataSchool(result.getData());

                                } else {

                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                    } else {
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
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
           /* fav_recyclerView_schools.setVisibility(View.GONE);
            wishlist.setVisibility(View.VISIBLE);*/
            isEmpty1 = "empty";
            ll_Shools.setVisibility(View.GONE);
            if (isEmprty.equals("empty") && isEmpty1.equals("empty")) {
                wishlist.setVisibility(View.VISIBLE);
            }
        } else {
            isEmpty1 = "";
            ll_Shools.setVisibility(View.VISIBLE);

            FavoriteItemsSchoolAdapter schoolAdapter = new FavoriteItemsSchoolAdapter(activity, data,recyclerViewClickListener);
            fav_recyclerView_schools.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            fav_recyclerView_schools.setAdapter(schoolAdapter);
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
            rv_local_wishlist.setVisibility(View.GONE);
            wishlist.setVisibility(View.VISIBLE);
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
                    shoppingBagModel.setDescription(json_data.getString("description"));
                    shoppingBagModel.setCategory(json_data.getString("category"));
                    shoppingBagModel.setSize(json_data.getString("size"));
                    shoppingBagModel.setType(json_data.getString("type"));
                    shoppingBagModel.setSchoolStoreId(json_data.getString("schoolStoreId"));
                    shoppingBagModel.setWishlist(json_data.getString("wishlist"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));
                    list.add(shoppingBagModel);

                }

                if (list.isEmpty()) {
                    rv_local_wishlist.setVisibility(View.GONE);
                    wishlist.setVisibility(View.VISIBLE);
                } else {

                    rv_local_wishlist.setVisibility(View.VISIBLE);
                    wishlist.setVisibility(View.GONE);
                    FavoriteItemsLocalAdapter myAdapter = new FavoriteItemsLocalAdapter(activity, list, recyclerViewClickListener);
                    rv_local_wishlist.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    rv_local_wishlist.setAdapter(myAdapter);
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

    @Override
    public void onClickPosition(int position) {
        if (position == 80) {
            getWishListStatus();
        }else if(position==25){
            getWishListApiCall();
        }
    }
}