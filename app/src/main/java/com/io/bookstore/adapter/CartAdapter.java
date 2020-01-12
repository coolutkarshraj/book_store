package com.io.bookstore.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.holder.CartHolder;
import com.io.bookstore.holder.CoursesHolder;
import com.io.bookstore.localStorage.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartHolder> {
    private Activity activity;
    private JSONArray courseicon;
    JSONObject json_data;

    public CartAdapter(Activity activity, JSONArray courseicon) {
        this.activity = activity;
        this.courseicon = courseicon;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_biew_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {

        try {
             json_data = courseicon.getJSONObject(position);
            holder.tv_product_name.setText(json_data.getString("Name"));
            holder.textView7.setText(json_data.getString("Price")+ "K.D");
            Glide.with(activity).load(Config.imageUrl +json_data.getString("Image")).into(holder.imageView5);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper;
                dbHelper = new DbHelper(activity);

                boolean isdeleted = false;
                try {
                    isdeleted = dbHelper.deleteData(json_data.getString("P_ID"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (isdeleted == true) {
                    getSqliteData();
                Toast.makeText(activity, "Data Deleted Sucessfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(activity, "Data is not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getSqliteData() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(activity);
        Cursor cursor = dbHelper.getData();
        if(cursor.getCount() == 0){
            Log.e("Error","no Data");
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
        Log.d("datajson","data"+datajson);
        StaticData.CartData = datajson;
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(StaticData.CartData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        courseicon = jArray;
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return courseicon.length();
    }
}
