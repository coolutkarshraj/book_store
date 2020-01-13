package com.io.bookstore.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.holder.CartHolder;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstore.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.io.bookstore.activity.homeActivity.ui.cart.CartFragment.list;
import static com.io.bookstore.activity.homeActivity.ui.cart.CartFragment.recyclerView;


public class CartAdapter extends RecyclerView.Adapter<CartHolder> {
    private Activity activity;
    private JSONArray courseicon;
    JSONObject json_data;
    Dialog dialog;
    ArrayList<CartLocalListResponseMode> item;
    int count;
    DbHelper dbHelper;

    public CartAdapter(Activity activity, ArrayList<CartLocalListResponseMode> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_biew_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {


        final CartLocalListResponseMode model = item.get(position);
        holder.tv_product_name.setText(model.getName());
        holder.textView7.setText(model.getPrice() + "K.D");
        holder.textView8.setText(model.getQuantity());
        Glide.with(activity).load(Config.imageUrl + model.getImage()).into(holder.imageView5);


        holder.imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper;
                dbHelper = new DbHelper(activity);

                boolean isdeleted = false;

                isdeleted = dbHelper.deleteData(model.getPID());

                if (isdeleted == true) {
                    Utils.showAlertDialog(activity, "Data Deleted Sucessfully");
                    getSqliteData();

                } else {
                    Utils.showAlertDialog(activity, "Data is not deleted");
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
                } else {
                    holder.textView8.setText(String.valueOf(count));
                    updateQuantity(model.getName(), model.getImage(), count,
                            model.getPrice(), model.getPID());
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
                } else {
                    holder.textView8.setText(String.valueOf(count));
                    updateQuantity(model.getName(), model.getImage(), count,
                            model.getPrice(), model.getPID());

                    getSqliteData();
                }

            }
        });


    }


    private void getSqliteData() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(activity);
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
            Log.e("Error", "no Data");
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
                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(gridLayoutManager);
                CartAdapter cartAdapter = new CartAdapter(activity, list);
                recyclerView.setAdapter(cartAdapter);
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


    private void updateQuantity(String name, String image, int count, String price, String pid) {
        dbHelper = new DbHelper(activity);
        boolean isupdated = dbHelper.updateData(name, image, String.valueOf(count), price, pid);
        if (isupdated == true) {
            getSqliteData();
        } else {
            Utils.showAlertDialog(activity, "something went wrong");
        }
    }


}
