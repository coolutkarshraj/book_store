package com.io.bookstores.adapter.bookStoreAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstores.model.bookListModel.Datum;
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
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Datum> mData;
    userOnlineInfo user;
    NewProgressBar dialog;
    String type = "";
    String schoolStoreId = "";
    private LocalStorage localStorage;
    private LoginModel loginModel;
    private DbHelper dbHelper;
    private String wishlistdata;
    public static ArrayList<WishListLocalResponseModel> lists = new ArrayList<>();
    public static ArrayList<CartLocalListResponseMode> list = new ArrayList<>();



    public BookListAdapter(Context mContext, List<Datum> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.list_book_item, parent, false);
        getSqliteData1();
        getWishListStatus();
        dbHelper =new DbHelper(mContext);
        user = new userOnlineInfo();
        localStorage = new LocalStorage(mContext);
        loginModel = localStorage.getUserProfile();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
         getWishListStatus();
         holder.tv_price.setText(""+mData.get(position).getPrice()+"KD");
         holder.tv_bookName.setText(""+mData.get(position).getName());
        Glide.with(mContext).load(Config.imageUrl + mData.get(position).getAvatarPath()).into(holder.iv_favorite);
        if (loginModel == null) {
            getWishListStatus();
            for (int ii = 0; ii <= lists.size() - 1; ii++) {
                if (Long.parseLong(lists.get(ii).getpID()) == mData.get(position).getBookId()) {
                    Log.e("matched", "" + mData.get(position).getBookId() + " =" + Long.parseLong(lists.get(ii).getpID()));
                    holder.mark_fav.setVisibility(View.GONE);
                    holder.mark_fav_red.setVisibility(View.VISIBLE);
                }/* else {
                    Log.e("nomatched", "" + mData.get(position).getBookId() + " =" + Long.parseLong(lists.get(ii).getpID()));
                    holder.mark_fav.setVisibility(View.VISIBLE);
                    holder.mark_fav_red.setVisibility(View.GONE);
                }*/
            }
        } else {
            if (mData.get(position).isWishlist() == true) {
                holder.mark_fav.setVisibility(View.GONE);
                holder.mark_fav_red.setVisibility(View.VISIBLE);
            } else {
                holder.mark_fav.setVisibility(View.VISIBLE);
                holder.mark_fav_red.setVisibility(View.GONE);
            }
        }

        holder.mark_cart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (type.equals("school")) {
                    openDialogBox(mData, position);
                } else if (type.equals("store") && schoolStoreId.equals(String.valueOf(mData.get(position).getStoreId()))) {
                    addtoCart(mData, position);
                } else if (type.isEmpty()) {
                    addtoCart(mData, position);
                } else {
                    openDialogBox(mData, position);
                }
            }
        });
        holder.mark_fav.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if (loginModel == null) {
                    LocalStorage localStorage = new LocalStorage(mContext);
                    String dummyId = localStorage.getString(LocalStorage.Dummy_Store_ID);
                    String storeId = localStorage.getString(LocalStorage.StoreId);
                   /* if (dummyId.equals(storeId) || dummyId.equals("")) {
                        localStorage.putString(LocalStorage.Dummy_Store_ID, localStorage.getString(LocalStorage.StoreId));*/
                        DbHelper dbHelper = new DbHelper(mContext);
                        Cursor cursor = dbHelper.getOneWishList(String.valueOf(mData.get(position).getBookId()));
                        if (cursor.getCount() == 0) {
                            boolean isInserted = dbHelper.insertWishList(mData.get(position).getName(),
                                    mData.get(position).getAvatarPath(),
                                    mData.get(position).getBookId(),
                                    1,
                                    mData.get(position).getPrice(),
                                    mData.get(position).getDescription(),
                                    String.valueOf(mData.get(position).getGstPrice()),
                                    mData.get(position).getQuantity(),
                                    "true", "store", "", Integer.parseInt(storeId), "book");
                            if (isInserted) {
                                holder.mark_fav.setVisibility(View.GONE);
                                holder.mark_fav_red.setVisibility(View.VISIBLE);
                                getWishListStatus();
                                Toast.makeText(mContext, "add item to wishlist sucessfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "You have already this item added into wishlist", Toast.LENGTH_SHORT).show();
                        }
/*
                    } else {
                        opendialogwish(mData, position);
                    }*/
                } else {
                    holder.mark_fav.setVisibility(View.GONE);
                    holder.mark_fav_red.setVisibility(View.VISIBLE);
                    updateQuantity(mData.get(position).getBookId(),"true");
                    addorRemomoveWishlist(mData.get(position).getBookId());
                }


            }
        });

        holder.mark_fav_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginModel == null) {
                    DbHelper dbHelper;
                    dbHelper = new DbHelper(mContext);
                    Cursor cursor = dbHelper.getData();
                    boolean isdeleted = false;
                    isdeleted = dbHelper.deleteOneWishList(String.valueOf(mData.get(position).getBookId()));

                    if (isdeleted == true) {
                        holder.mark_fav.setVisibility(View.VISIBLE);
                        holder.mark_fav_red.setVisibility(View.GONE);
                        Toast.makeText(mContext, "wishlist delete sucessfully", Toast.LENGTH_SHORT).show();
                        getWishList();

                    } else {
                        Toast.makeText(mContext, "Data is not deleted", Toast.LENGTH_SHORT).show();
                        getWishList();
                    }
                } else {
                    holder.mark_fav.setVisibility(View.VISIBLE);
                    holder.mark_fav_red.setVisibility(View.GONE);
                    updateQuantity(mData.get(position).getBookId(),"false");
                    addorRemomoveWishlist(mData.get(position).getBookId());
                }
            }
        });

        holder.mark_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookDetila(mData, position);
            }
        });

        holder.iv_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookDetila(mData, position);
            }
        });

    }





    /* ---------------------------------------------- add or remove wishlist api -----------------------------------------*/

    private void addorRemomoveWishlist(Long bookId) {
        if (user.isOnline(mContext)) {
            dialog = new NewProgressBar(mContext);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(mContext);
            ApiCaller.addOrRemoveWishList((Activity) mContext, Config.Url.addorremoveWishlist + bookId, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AddorRemoveWishlistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddorRemoveWishlistResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                                return;
                            }

                            if(result != null){
                                if(result.getStatus()== null){
                                    if(result.getMessage().equals("Unauthorized")){
                                        Utils.showAlertDialogLogout((Activity) mContext, "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }else {
                                    if (result.getStatus() == true) {
                                        Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }



                        }
                    });

        } else {
            Utils.showAlertDialog((Activity) mContext, "No Internet Connection");
        }
    }

    private void openDialogBox(final List<Datum> mData, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("you have already select books from another store .Are you sure Want to delete existing cart item");
        builder.setTitle("Delete Cart Item");
        //Setting message manually and performing action on button click
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                LocalStorage localStorage = new LocalStorage(mContext);
                localStorage.putString(LocalStorage.Dummy_Store_ID, localStorage.getString(LocalStorage.StoreId));
                DbHelper dbHelper = new DbHelper(mContext);
                dbHelper.deleteAll();
                boolean isInserted = dbHelper.insertData(mData.get(position).getName(),
                        mData.get(position).getAvatarPath(),
                        mData.get(position).getBookId(),
                        1,
                        mData.get(position).getPrice(),
                        mData.get(position).getDescription(),
                        String.valueOf(mData.get(position).getGstPrice()),
                        mData.get(position).getQuantity(), String.valueOf(mData.get(position).isWishlist()),
                        localStorage.getString(LocalStorage.TYPE),
                        "", Integer.parseInt(mData.get(position).getStoreId()),"book");

                if (isInserted) {
                    getSqliteData1();
                    Toast.makeText(mContext, "Items Added Succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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

   /* private void opendialogwish(final List<Datum> mData, final int position) {
        LocalStorage localStorage = new LocalStorage(mContext);
        localStorage.putString(LocalStorage.Dummy_Store_ID, localStorage.getString(LocalStorage.StoreId));
        DbHelper dbHelper = new DbHelper(mContext);
        dbHelper.deleteAll();
        @SuppressLint({"NewApi", "LocalSuppress"}) boolean isInserted = dbHelper.insertWishList(mData.get(position).getName(),
                mData.get(position).getAvatarPath(),
                mData.get(position).getBookId(),
                1,
                mData.get(position).getPrice(),
                mData.get(position).getDescription(),
                String.valueOf(mData.get(position).getGstPrice()),
                mData.get(position).getQuantity(), String.valueOf(mData.get(position).isWishlist()), "store", "");

        if (isInserted) {
            getWishList();
            Toast.makeText(mContext, "Items Added Succesfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void showBookDetila(final List<Datum> mData, final int position) {
        final Dialog dialog = new Dialog(mContext, R.style.dialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.book_detial_dialog);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.yes);
        final Button btn_add_To_cart = (Button) dialog.findViewById(R.id.btn_add_To_cart);
        final TextView oldPassword = (TextView) dialog.findViewById(R.id.et_old_password);
        final TextView newPassword = (TextView) dialog.findViewById(R.id.et_new_password);
        final TextView tv_book_price = (TextView) dialog.findViewById(R.id.tv_book_price);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);
        final ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);

        oldPassword.setText(mData.get(position).getName());
        newPassword.setText(mData.get(position).getDescription());
        tv_book_price.setText("" + mData.get(position).getPrice() + "K.D");
        Glide.with(mContext).load(Config.imageUrl + mData.get(position).getAvatarPath()).into(iv_image);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_add_To_cart.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (type.equals("school")) {
                    dialog.dismiss();
                    openDialogBox(mData, position);
                } else if (type.equals("store") && schoolStoreId.equals(String.valueOf(mData.get(position).getStoreId()))) {
                    addtoCart(mData, position);
                    dialog.dismiss();
                } else if (type.isEmpty()) {
                    addtoCart(mData, position);
                    dialog.dismiss();
                } else {
                    openDialogBox(mData, position);
                    dialog.dismiss();
                }
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setFilter(List<Datum> newlist){
        mData=new ArrayList<>();
        mData.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_price,tv_bookName;
        ImageView iv_favorite, mark_fav, mark_cart, mark_setting, mark_fav_red;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_bookName= (TextView) itemView.findViewById(R.id.bookName);
            iv_favorite = (ImageView) itemView.findViewById(R.id.iv_favorite);
            mark_fav = (ImageView) itemView.findViewById(R.id.mark_fav);
            mark_fav_red = (ImageView) itemView.findViewById(R.id.mark_fav_red);
            mark_cart = (ImageView) itemView.findViewById(R.id.mark_cart);
            mark_setting = (ImageView) itemView.findViewById(R.id.mark_setting);


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addtoCart(List<Datum> mData, int position) {
        LocalStorage localStorage = new LocalStorage(mContext);
                   /* String dummyId = localStorage.getString(LocalStorage.Dummy_Store_ID);
                    String storeId = localStorage.getString(LocalStorage.StoreId);
                    if (dummyId.equals(storeId) || dummyId.equals("")) {
                        localStorage.putString(LocalStorage.Dummy_Store_ID, localStorage.getString(LocalStorage.StoreId));
*/
        DbHelper dbHelper = new DbHelper(mContext);
        Cursor cursor = dbHelper.getDataq(String.valueOf(mData.get(position).getBookId()));
        if (cursor.getCount() == 0) {
            boolean isInserted = dbHelper.insertData(mData.get(position).getName(),
                    mData.get(position).getAvatarPath(),
                    mData.get(position).getBookId(),
                    1,
                    mData.get(position).getPrice(),
                    mData.get(position).getDescription(),
                    String.valueOf(mData.get(position).getGstPrice()),
                    mData.get(position).getQuantity(),
                    String.valueOf(mData.get(position).isWishlist()),
                    localStorage.getString(LocalStorage.TYPE),
                    "", Integer.parseInt(mData.get(position).getStoreId()),"book");
            if (isInserted) {
                getSqliteData1();
                Toast.makeText(mContext, "Items Added Succesfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "You have already this item added into cart", Toast.LENGTH_SHORT).show();
        }

    }

    private void getSqliteData1() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(mContext);
        Cursor cursor = dbHelper.getData();
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
        Log.d("datajson", datajson);
        StaticData.CartData = datajson;
        setRecyclerViewData(datajson);
    }

    private void setRecyclerViewData(String datajson) {
        JSONArray jArray = null;

        try {
            list.clear();
            jArray = new JSONArray(datajson);
            if (jArray == null || jArray.length() == 0) {
                MainActivity.tvcart.setVisibility(View.GONE);
            } else {
                MainActivity.tvcart.setVisibility(View.VISIBLE);
                MainActivity.tvcart.setText("" + jArray.length());
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
                    shoppingBagModel.setType(json_data.getString("size"));
                    shoppingBagModel.setType(json_data.getString("type"));
                    shoppingBagModel.setSchoolStoreId(json_data.getString("schoolStoreId"));
                    type = json_data.getString("type");
                    schoolStoreId = json_data.getString("schoolStoreId");
                    shoppingBagModel.setWishlist(json_data.getString("wishlist"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));
                    list.add(shoppingBagModel);

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

    private void updateQuantity(Long pid, String s) {
        dbHelper = new DbHelper(mContext);
        boolean isupdated = dbHelper.updatewish(String.valueOf(pid),s);
        if (isupdated == true) {
            getSqliteData1();
        } else {
            Utils.showAlertDialog((Activity) mContext, "something went wrong");
        }
    }

    private void getWishList() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(mContext);
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
        //   getWishListAllData(datajson);

    }

    private void getWishListStatus() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(mContext);
        Cursor cursor = dbHelper.getAllWishlist();
        if (cursor.getCount() == 0) {
            lists.clear();
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
                    shoppingBagModel.setSchoolStoreId(json_data.getString("schoolStoreId"));
                    shoppingBagModel.setCategory(json_data.getString("category"));
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