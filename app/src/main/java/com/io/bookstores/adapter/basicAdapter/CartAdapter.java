package com.io.bookstores.adapter.basicAdapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.activity.homeActivity.ui.cart.CartFragment;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.holder.CartHolder;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstores.model.bookListModel.WishListLocalResponseModel;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.wishlistModel.AddorRemoveWishlistResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.io.bookstores.activity.homeActivity.ui.cart.CartFragment.list;
import static com.io.bookstores.activity.homeActivity.ui.cart.CartFragment.recyclerView;


public class CartAdapter extends RecyclerView.Adapter<CartHolder> {
    private Activity activity;
    private JSONArray courseicon;
    JSONObject json_data;
    NewProgressBar dialog;
    ArrayList<CartLocalListResponseMode> item;
    private String wishlistdata;
    public static ArrayList<WishListLocalResponseModel> lists = new ArrayList<>();
    LoginModel loginModel;
    int count;
    LocalStorage localStorage;
    DbHelper dbHelper;
    userOnlineInfo user;

    public CartAdapter(Activity activity, ArrayList<CartLocalListResponseMode> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View view = LayoutInflater.from(activity).inflate(R.layout.item_biew_cart, parent, false);
        View view = LayoutInflater.from(activity).inflate(R.layout.item_cart, parent, false);
        localStorage = new LocalStorage(activity);
        getWishList();
        getWishListStatus();
        loginModel = localStorage.getUserProfile();
        user = new userOnlineInfo();
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        getWishListStatus();

        final CartLocalListResponseMode model = item.get(position);
        holder.tv_product_name.setText(model.getName());
        holder.textView7.setText(model.getPrice() + "K.D");
        holder.textView8.setText(model.getQuantity());
        Glide.with(activity).load(Config.imageUrl + model.getImage()).into(holder.imageView5);

        if (loginModel == null) {
            getWishListStatus();
            for (int ii = 0; ii <= lists.size() - 1; ii++) {
                if (Long.parseLong(model.getpID()) == Long.parseLong(lists.get(ii).getpID())) {
                    holder.mark_fav.setVisibility(View.GONE);
                    holder.mark_fav_red.setVisibility(View.VISIBLE);
                } else {
                    holder.mark_fav.setVisibility(View.VISIBLE);
                    holder.mark_fav_red.setVisibility(View.GONE);
                }
            }
        } else {
            if (item.get(position).getWishlist().equals("true")) {
                holder.mark_fav_red.setVisibility(View.VISIBLE);
                holder.mark_fav.setVisibility(View.GONE);
            } else {
                holder.mark_fav_red.setVisibility(View.GONE);
                holder.mark_fav.setVisibility(View.VISIBLE);
            }
        }

        holder.imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper;
                dbHelper = new DbHelper(activity);
                Cursor cursor = dbHelper.getData();
                boolean isdeleted = false;
                isdeleted = dbHelper.deleteData(model.getPID());

                if (isdeleted == true) {
                    Utils.showAlertDialog(activity, "Data Deleted Sucessfully");
                    if(cursor.getCount() == 0){
                        MainActivity.tvcart.setVisibility(View.GONE);
                        CartFragment.no_text_found.setVisibility(View.VISIBLE);
                        CartFragment.nested_sc_view.setVisibility(View.GONE);
                    }

                    getSqliteData();

                } else {
                    Utils.showAlertDialog(activity, "Data is not deleted");
                    getSqliteData();
                }
            }
        });

        holder.ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper;
                dbHelper = new DbHelper(activity);
                Cursor cursor = dbHelper.getData();
                boolean isdeleted = false;
                isdeleted = dbHelper.deleteData(model.getPID());

                if (isdeleted == true) {
                    Utils.showAlertDialog(activity, "Data Deleted Sucessfully");
                    if(cursor.getCount() == 0){
                        MainActivity.tvcart.setVisibility(View.GONE);
                        CartFragment.no_text_found.setVisibility(View.VISIBLE);
                        CartFragment.nested_sc_view.setVisibility(View.GONE);
                    }
                    getSqliteData();

                } else {
                    Utils.showAlertDialog(activity, "Data is not deleted");
                    getSqliteData();
                }
            }
        });

        holder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(model.getQuantity());
                count--;
                if (count <= 0) {
                    Utils.showAlertDialog(activity, "You cannot decrease quantity less than one");
                    getSqliteData();
                } else {
                    holder.textView8.setText(String.valueOf(count));
                    updateQuantity(model.getName(), model.getImage(), count,
                            model.getPrice(), model.getPrice(), model.getPID());
                    getSqliteData();

                }

            }
        });
        holder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(model.getQuantity());
                count++;
                if (count >= Integer.parseInt(model.getAvailibleQty()) + 1) {
                    Utils.showAlertDialog(activity, "This much quantity is not availiable");
                    getSqliteData();
                } else {
                    holder.textView8.setText(String.valueOf(count));
                    updateQuantity(model.getName(), model.getImage(), count,
                            model.getPrice(), model.getPrice(), model.getPID());

                    getSqliteData();
                }

            }
        });

        holder.mark_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginModel == null) {
                    LocalStorage localStorage = new LocalStorage(activity);
                    String dummyId = localStorage.getString(LocalStorage.Dummy_Store_ID);
                    String storeId = localStorage.getString(LocalStorage.StoreId);
                    if (dummyId.equals(storeId) || dummyId.equals("")) {
                        localStorage.putString(LocalStorage.Dummy_Store_ID, localStorage.getString(LocalStorage.StoreId));
                        DbHelper dbHelper = new DbHelper(activity);

                        Cursor cursor = dbHelper.getOneWishList(String.valueOf(model.getpID()));
                        if (cursor.getCount() == 0) {
                            boolean isInserted = dbHelper.insertWishList(model.getName(),
                                    model.getImage(),
                                    Long.parseLong(model.getpID()),
                                    1,
                                    Long.parseLong(model.getPrice()),
                                    model.getName(),
                                    model.getGst(),
                                    Integer.parseInt(model.getAvailibleQty()),
                                    "true", "store", "");
                            if (isInserted) {
                                getWishListStatus();
                                holder.mark_fav.setVisibility(View.GONE);
                                holder.mark_fav_red.setVisibility(View.VISIBLE);
                                // Toast.makeText(activity, "add item to wishlist sucessFully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, "You have already this item added into wishlist", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        opendialogwish(model, position);
                    }
                } else {
                    if (localStorage.getString(LocalStorage.TYPE).equals("store")) {
                        holder.mark_fav.setVisibility(View.GONE);
                        holder.mark_fav_red.setVisibility(View.VISIBLE);
                        updateQuantity(Long.valueOf(item.get(position).getPID()), "true");
                        addorRemomoveWishlist(Long.valueOf(item.get(position).getPID()));
                    } else {
                        holder.mark_fav.setVisibility(View.GONE);
                        holder.mark_fav_red.setVisibility(View.VISIBLE);
                        updateQuantity(Long.valueOf(item.get(position).getPID()), "true");
                        addorRemomoveWishlistSchool(Long.valueOf(item.get(position).getPID()));
                    }

                }
            }
        });

        holder.mark_fav_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginModel == null) {
                    DbHelper dbHelper;
                    dbHelper = new DbHelper(activity);
                    Cursor cursor = dbHelper.getData();
                    boolean isdeleted = false;
                    isdeleted = dbHelper.deleteOneWishList(model.getpID());

                    if (isdeleted == true) {
                        holder.mark_fav.setVisibility(View.VISIBLE);
                        holder.mark_fav_red.setVisibility(View.GONE);
                        Toast.makeText(activity, "wishlist delete sucessfully", Toast.LENGTH_SHORT).show();
                        getWishListStatus();

                    } else {
                        Toast.makeText(activity, "Data is not deleted", Toast.LENGTH_SHORT).show();
                        getWishList();
                    }
                } else {
                    if (localStorage.getString(LocalStorage.TYPE).equals("store")) {
                        holder.mark_fav.setVisibility(View.VISIBLE);
                        holder.mark_fav_red.setVisibility(View.GONE);
                        updateQuantity(Long.valueOf(item.get(position).getPID()), "false");
                        addorRemomoveWishlist(Long.valueOf(item.get(position).getPID()));
                    } else {
                        holder.mark_fav.setVisibility(View.VISIBLE);
                        holder.mark_fav_red.setVisibility(View.GONE);
                        updateQuantity(Long.valueOf(item.get(position).getPID()), "false");
                        addorRemomoveWishlistSchool(Long.valueOf(item.get(position).getPID()));
                    }


                }
            }
        });


    }


    private void getSqliteData() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(activity);
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
            Log.e("Error", "no GuestDataModel");
            item = null;
            notifyDataSetChanged();
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
        Log.d("datajson", "data" + datajson);
        StaticData.CartData = datajson;

        JSONArray jArray = null;

        try {
            list.clear();
            jArray = new JSONArray(datajson);
            int price = 0;
            int gst = 0;
            int qty = 0;
            int sum = 0;
            MainActivity.tvcart.setText("" + jArray.length());

            if (jArray == null || jArray.length() == 0) {
                CartFragment.no_text_found.setVisibility(View.VISIBLE);
                CartFragment.nested_sc_view.setVisibility(View.GONE);
                MainActivity.tvcart.setText("" + jArray.length());
            } else {
                MainActivity.tvcart.setText("" + jArray.length());
                CartFragment.no_text_found.setVisibility(View.GONE);
                CartFragment. nested_sc_view.setVisibility(View.VISIBLE);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    CartLocalListResponseMode shoppingBagModel = new CartLocalListResponseMode();
                    shoppingBagModel.setId(json_data.getString("Id"));
                    shoppingBagModel.setName(json_data.getString("Name"));
                    shoppingBagModel.setQuantity(json_data.getString("Quantity"));
                    shoppingBagModel.setPrice(json_data.getString("Price"));
                    shoppingBagModel.setImage(json_data.getString("Image"));
                    shoppingBagModel.setAvailibleQty(json_data.getString("avalible"));
                    shoppingBagModel.setPID(json_data.getString("P_ID"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));
                    shoppingBagModel.setSize(json_data.getString("size"));
                    shoppingBagModel.setType(json_data.getString("type"));
                    shoppingBagModel.setWishlist(json_data.getString("wishlist"));
                    price = Integer.parseInt(json_data.getString("Price"));
                    qty = Integer.parseInt(json_data.getString("Quantity"));
                   /* if (json_data.getString("gstPrice") == null || json_data.getString("gstPrice").equals("")) {
                        gst = 0;
                    } else {

                    }
                    gst = Integer.parseInt(gst + json_data.getString("gstPrice"));
*/                    sum += price * qty;
                    list.add(shoppingBagModel);

                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    CartAdapter cartAdapter = new CartAdapter(activity, list);
                    recyclerView.setAdapter(cartAdapter);
                }
                CartFragment.price = sum;
                CartFragment.gst = gst;
                CartFragment.tv_gst.setText(String.valueOf(gst) + "KD");
                CartFragment.total_cost.setText(String.valueOf(sum) + "KD");
                int amt = sum + CartFragment.dilvery;
                CartFragment.totalAll_cost.setText(amt + "KD");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        int a;
        if(item == null){
            a = 0;
            LocalStorage localStorage = new LocalStorage(activity);
            localStorage.putString(LocalStorage.Dummy_Store_ID,"");
        }else {
            a = item.size();
        }
        return a;
    }


    private void updateQuantity(String name, String image, int count, String modelPrice, String price, String pid) {
        dbHelper = new DbHelper(activity);
        boolean isupdated = dbHelper.updateData(name, image, String.valueOf(count), price, pid,modelPrice);
        if (isupdated == true) {
            getSqliteData();
        } else {
            Utils.showAlertDialog(activity, "something went wrong");
        }
    }


    /* ---------------------------------------------- add or remove wishlist api -----------------------------------------*/

    private void addorRemomoveWishlist(Long bookId) {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.addOrRemoveWishList((Activity) activity, Config.Url.addorremoveWishlist + bookId, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AddorRemoveWishlistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddorRemoveWishlistResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog((Activity) activity, "Something Went Wrong");
                                return;
                            }

                            if(result != null){
                                if(result.getStatus()== null){
                                    if(result.getMessage().equals("Unauthorized")){
                                        Utils.showAlertDialogLogout((Activity) activity, "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }else {
                                    if (result.getStatus() == true) {
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        getSqliteData();
                                    } else {
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }



                        }
                    });

        } else {
            Utils.showAlertDialog((Activity) activity, "No Internet Connection");
        }
    }

    private void addorRemomoveWishlistSchool(Long bookId) {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.addOrRemoveWishListschool((Activity) activity, Config.Url.addorremoveWishlistSchool + bookId, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AddorRemoveWishlistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddorRemoveWishlistResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog((Activity) activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout((Activity) activity, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    if (result.getStatus() == true) {
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        getSqliteData();
                                    } else {
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }


                        }
                    });

        } else {
            Utils.showAlertDialog((Activity) activity, "No Internet Connection");
        }
    }

    private void updateQuantity(Long pid, String s) {
        dbHelper = new DbHelper(activity);
        boolean isupdated = dbHelper.updatewish(String.valueOf(pid),s);
        if (isupdated == true) {
            getSqliteData();
        } else {
            Utils.showAlertDialog(activity, "something went wrong");
        }
    }

    private void getWishList() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(activity);
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


    }

    private void opendialogwish(final CartLocalListResponseMode model, int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("you have already select books from another store .Are you sure Want to delete existing wishlist item");
        builder.setTitle("Delete Wishlist Item");
        //Setting message manually and performing action on button click
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.Dummy_Store_ID, localStorage.getString(LocalStorage.StoreId));
                DbHelper dbHelper = new DbHelper(activity);
                dbHelper.deleteAll();
                boolean isInserted = dbHelper.insertWishList(model.getName(),
                        model.getImage(),
                        Long.parseLong(model.getpID()),
                        1,
                        Long.parseLong(model.getPrice()),
                        model.getName(),
                        String.valueOf(model.getGst()),
                        Integer.parseInt(model.getAvailibleQty()),
                        String.valueOf(model.getWishlist()), "store", "");

                if (isInserted) {
                    Toast.makeText(activity, "Items Added Succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        //alert.setTitle("AlertDialogExample");
        alert.show();
    }


    private void getWishListStatus() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(activity);
        Cursor cursor = dbHelper.getAllWishlist();
        if (cursor.getCount() == 0) {
            lists.clear();
            Log.e("Error", "no Data");
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
        wishlistdata = resultSet.toString();
        wishlistdata.replaceAll("\\\\", "");
        Log.d("wishlistAll", wishlistdata);
        getWishListAllData(wishlistdata);

    }

    private void getWishListAllData(String wishlistdata) {
        JSONArray jArray = null;

        try {
            lists.clear();
            jArray = new JSONArray(wishlistdata);
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
                    shoppingBagModel.setSize(json_data.getString("size"));
                    shoppingBagModel.setType(json_data.getString("type"));
                    shoppingBagModel.setAvailibleQty(json_data.getString("avalible"));
                    shoppingBagModel.setpID(json_data.getString("P_ID"));
                    shoppingBagModel.setWishlist(json_data.getString("wishlist"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));
                    lists.add(shoppingBagModel);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
