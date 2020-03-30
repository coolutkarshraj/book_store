package com.io.bookstores.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.BookListAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.BookListModel;
import com.io.bookstores.model.bookListModel.Datum;
import com.io.bookstores.model.bookListModel.WishListLocalResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment implements View.OnClickListener {


    private RecyclerView recyclerView;
    private View root;
    private TextView notdata;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private SearchView searchView2;
    private List<Datum> data;
    private List<Datum> childdata;
    private LocalStorage localStorage;
    private BookListAdapter categoryAdapter;
    private ItemClickListner itemClickListner;
    private ImageView iv_back;
    List<WishListLocalResponseModel> lists = new ArrayList<>();

    public BookListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_category_list, container, false);
        initView();

        if (localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")) {
            getBookList("",
                    localStorage.getString(LocalStorage.StoreId),
                    localStorage.getString(LocalStorage.CategoryId),"");
        }else {
            getBookList("",
                    localStorage.getString(LocalStorage.StoreId),
                    localStorage.getString(LocalStorage.CategoryId),localStorage.getString(LocalStorage.token));
        }

        startWorking();
        return root;
    }

    private void startWorking() {
        searchViewSetUp();
    }

    private void initView() {
        localStorage = new LocalStorage(getActivity());
        childdata = new ArrayList<>();
        recyclerView = root.findViewById(R.id.rv_cateory_list);
        searchView2 = root.findViewById(R.id.searchView2);
        user = new userOnlineInfo();
        itemClickListner = (ItemClickListner) getActivity();
        iv_back = root.findViewById(R.id.iv_back);
        notdata = root.findViewById(R.id.notdata);
        iv_back.setOnClickListener(this);
    }



    private void getBookList(String name, String sId, String Cid, String token) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getBookModel(getActivity(), Config.Url.getAllBook+sId+"/"+Cid+"/", sId, Cid, name,token,
                    new FutureCallback<BookListModel>() {

                        @Override
                        public void onCompleted(Exception e, BookListModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    if (result.getStatus() == true) {
                                        dialog.dismiss();
                                        data = result.getData();
                                        setRecyclerViewData(result.getData());
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }





                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }


    private void setRecyclerViewData(List<Datum> result) {
        if (result.isEmpty()) {
            notdata.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {
            searchView2.setVisibility(View.VISIBLE);
            notdata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            categoryAdapter = new BookListAdapter(getActivity(), result);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            childdata = (ArrayList<Datum>) result;
            recyclerView.setAdapter(categoryAdapter);
        }

    }

    private void searchViewSetUp() {
        searchView2.setQueryHint("Search Books...");
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (childdata == null || childdata.isEmpty()) {

                } else {
                    List<Datum> newlist = new ArrayList<>();
                    for (Datum productList : childdata) {
                        String name = productList.getName().toLowerCase();
                        if (name.contains(s))
                            newlist.add(productList);
                    }
                    categoryAdapter.setFilter(newlist);

                }
                return true;
            }

        });

    }

    private void getWishList() {
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
            lists.clear();
            jArray = new JSONArray(datajson);
            if (jArray == null || jArray.length() == 0) {

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

                    shoppingBagModel.setWishlist(json_data.getString("wishlist"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));

                    lists.add(shoppingBagModel);

                 /*   price = price + Integer.parseInt( json_data.getString("Price")) ;
                    gst = Integer.parseInt(gst + json_data.getString("gstPrice"));*/
                }

              /*  tv_gst.setText(String.valueOf(gst));
                total_cost.setText(String.valueOf(price));*/

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                itemClickListner.onClick(6);
        }
    }
}
