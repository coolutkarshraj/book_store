package com.io.bookstore.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.bookListModel.Datum;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Datum> mData;



    public BookListAdapter(Context mContext, List<Datum> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.list_book_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //  holder.tv_price.setText(mData.get(position).getName());
        Glide.with(mContext).load(Config.imageUrl + mData.get(position).getAvatarPath()).into(holder.iv_favorite);
        holder.mark_cart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                LocalStorage localStorage = new LocalStorage(mContext);
                String dummyId = localStorage.getString(LocalStorage.Dummy_Store_ID);
                String storeId = localStorage.getString(LocalStorage.StoreId);
                if (dummyId.equals(storeId)|| dummyId.equals("")){
                    localStorage.putString(LocalStorage.Dummy_Store_ID,localStorage.getString(LocalStorage.StoreId));
                    DbHelper dbHelper = new DbHelper(mContext);
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
                }else {
                    openDialogBox(mData,position);
                }



            }
        });

    }

    private void openDialogBox(final List<Datum> mData, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure Want to delete existing cart item");
        builder.setTitle("Delete Cart Item");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to delete the ?");
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
               dialog.cancel();
               LocalStorage localStorage = new LocalStorage(mContext);
               localStorage.putString(LocalStorage.Dummy_Store_ID,localStorage.getString(LocalStorage.StoreId));
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

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_price;
        ImageView iv_favorite, mark_fav, mark_cart, mark_setting;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            iv_favorite = (ImageView) itemView.findViewById(R.id.iv_favorite);
            mark_fav = (ImageView) itemView.findViewById(R.id.mark_fav);
            mark_cart = (ImageView) itemView.findViewById(R.id.mark_cart);
            mark_setting = (ImageView) itemView.findViewById(R.id.mark_setting);

        }
    }


}