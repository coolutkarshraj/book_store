package com.io.bookstore.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.activity.homeActivity.MainActivity;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstore.model.bookListModel.Datum;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.model.wishlistModel.AddorRemoveWishlistResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
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
    private LocalStorage localStorage;
    private LoginModel loginModel;
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
        user = new userOnlineInfo();
        localStorage = new LocalStorage(mContext);
        loginModel = localStorage.getUserProfile();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
         holder.tv_price.setText(""+mData.get(position).getPrice()+"KD");
         holder.tv_bookName.setText(""+mData.get(position).getName());
        Glide.with(mContext).load(Config.imageUrl + mData.get(position).getAvatarPath()).into(holder.iv_favorite);
        if(mData.get(position).isWishlist() == true){
            holder.mark_fav.setVisibility(View.GONE);
            holder.mark_fav_red.setVisibility(View.VISIBLE);
        }else {
            holder.mark_fav.setVisibility(View.VISIBLE);
            holder.mark_fav_red.setVisibility(View.GONE);
        }

        holder.mark_cart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                LocalStorage localStorage = new LocalStorage(mContext);
                String dummyId = localStorage.getString(LocalStorage.Dummy_Store_ID);
                String storeId = localStorage.getString(LocalStorage.StoreId);
                if (dummyId.equals(storeId) || dummyId.equals("")) {
                    localStorage.putString(LocalStorage.Dummy_Store_ID, localStorage.getString(LocalStorage.StoreId));
                    DbHelper dbHelper = new DbHelper(mContext);

                    Cursor cursor = dbHelper.getDataq(String.valueOf(mData.get(position).getBookId()));
                    if (cursor.getCount() == 0) {
                        boolean isInserted = dbHelper.insertData(mData.get(position).getName(),
                                mData.get(position).getAvatarPath(),
                                mData.get(position).getBookId(),
                                1,
                                mData.get(position).getPrice(),
                                mData.get(position).getDescription(),
                                mData.get(position).getGstPrice(),
                                mData.get(position).getQuantity());
                        if (isInserted) {
                            getSqliteData1();
                            Toast.makeText(mContext, "Items Added Succesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "You have already this item added into cart", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    openDialogBox(mData, position);
                }


            }
        });
        holder.mark_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginModel == null) {
                    Toast.makeText(mContext, "please login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                } else {
                    holder.mark_fav.setVisibility(View.GONE);
                    holder.mark_fav_red.setVisibility(View.VISIBLE);
                    addorRemomoveWishlist(mData.get(position).getBookId());
                }


            }
        });

        holder.mark_fav_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginModel == null) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                } else {
                    holder.mark_fav.setVisibility(View.VISIBLE);
                    holder.mark_fav_red.setVisibility(View.GONE);
                    addorRemomoveWishlist(mData.get(position).getBookId());
                }
            }
        });

        holder.mark_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             showBookDetila(mData.get(position));
            }
        });

    }



    /* ---------------------------------------------- add or remove wishlist api -----------------------------------------*/

    private void addorRemomoveWishlist(Long bookId) {
        if (user.isOnline(mContext)) {
            dialog = new NewProgressBar(mContext);
            dialog.show();
            LocalStorage localStorage = new LocalStorage(mContext);
            ApiCaller.addOrRemoveWishList((Activity) mContext, Config.Url.addorremoveWishlist + bookId, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AddorRemoveWishlistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddorRemoveWishlistResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
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
                        mData.get(position).getGstPrice(),
                        mData.get(position).getQuantity());

                if (isInserted) {
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

    private void showBookDetila(Datum datum) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.book_detial_dialog);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.yes);
        final Button No = (Button) dialog.findViewById(R.id.no);
        final TextView oldPassword = (TextView) dialog.findViewById(R.id.et_old_password);
        final TextView newPassword = (TextView) dialog.findViewById(R.id.et_new_password);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);

        oldPassword.setText(datum.getName());
        newPassword.setText(datum.getDescription());
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
            mark_cart = (ImageView) itemView.findViewById(R.id.mark_cart);
            mark_setting = (ImageView) itemView.findViewById(R.id.mark_setting);
            mark_fav_red = (ImageView) itemView.findViewById(R.id.mark_fav_red);

        }
    }

    private void getSqliteData1() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(mContext);
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
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


}