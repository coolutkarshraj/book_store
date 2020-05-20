package com.io.bookstores.adapter.basicAdapter;

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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.adapter.schoolAdapter.ItemSizessAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.schoolWishlist.GetAllSchoolWishListDataModel;
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

public class FavoriteItemsSchoolAdapter extends RecyclerView.Adapter<FavoriteItemsSchoolAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetAllSchoolWishListDataModel> mData;
    userOnlineInfo user;
    NewProgressBar dialog;
    private LocalStorage localStorage;
    private LoginModel loginModel;
    String avalibaleQ = "0";
    String type = "";
    String schoolStoreId = "";
    ItemClickListner itemClickListner;
    String pSize = "0";
    DbHelper dbHelper;
    Boolean open = false;
    RecyclerViewClickListener recyclerViewClickListener;
    ArrayList<CartLocalListResponseMode> list = new ArrayList<>();

    public FavoriteItemsSchoolAdapter(Context mContext, List<GetAllSchoolWishListDataModel> mData, RecyclerViewClickListener recyclerViewClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag = false;
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_favorite, parent, false);
        user = new userOnlineInfo();
        getSqliteData1();
        localStorage = new LocalStorage(mContext);
        itemClickListner = (ItemClickListner) mContext;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GetAllSchoolWishListDataModel model = mData.get(position);
        holder.textView31.setText(model.getName());
        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.img_book_thumbnail);
        holder.favouritePriceText.setText("Price : " + model.getPrice() + " KD");
        localStorage.putString(LocalStorage.Dummy_Store_ID, String.valueOf(model.getSchoolId()));
        holder.clayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookDetila(model, position);
            }
        });
        holder.imageView19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_remove_wishlist);
                dialog.setTitle("");
                final Button Yes = (Button) dialog.findViewById(R.id.yes);
                final Button No = (Button) dialog.findViewById(R.id.no);
                final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);

                Clear.setOnClickListener(new View.OnClickListener() {
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
                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        removeWishListItem(Long.valueOf(model.getClassProductId()), position);
                        updateQuantity(Long.valueOf(model.getClassProductId()), "false");
                    }
                });

                dialog.show();

            }
        });
        holder.imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getProductSizes().size() == 1) {
                    addToCArt(model, position, mData);
                } else {
                    opendialogproductsize(model, position, mData);
                }
            }
        });

    }

    private void removeWishListItem(Long bookId, final int position) {
        if (user.isOnline(mContext)) {
            dialog = new NewProgressBar(mContext);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(mContext);
            ApiCaller.addOrRemoveWishListschool((Activity) mContext, Config.Url.addorremoveWishlistSchool + bookId, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AddorRemoveWishlistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddorRemoveWishlistResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout((Activity) mContext, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    if (result.getStatus() == true) {
                                        Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        mData.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeRemoved(position, mData.size());
                                        dialog.dismiss();
                                        recyclerViewClickListener.onClickPosition(25);
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

    private void showBookDetila(final GetAllSchoolWishListDataModel model, final int position) {
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
        final TextView chatdelete = (TextView) dialog.findViewById(R.id.chatdelete);
        final TextView tv_heding_name = (TextView) dialog.findViewById(R.id.tv_heding_name);
        final TextView tv_heding_description = (TextView) dialog.findViewById(R.id.tv_heding_description);
        final TextView tv_heading_proce = (TextView) dialog.findViewById(R.id.tv_heading_proce);
        final TextView oldPassword = (TextView) dialog.findViewById(R.id.et_old_password);
        final TextView newPassword = (TextView) dialog.findViewById(R.id.et_new_password);
        final TextView tv_book_price = (TextView) dialog.findViewById(R.id.tv_book_price);
        final TextView tv_size = (TextView) dialog.findViewById(R.id.tv_size);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);
        final ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
        final RecyclerView rv_itemsize = (RecyclerView) dialog.findViewById(R.id.rv_itemsize);

        if(model.getClassCategory().getName().equals("clothes") ||
                model.getClassCategory().getName().equals("cloth") ||
                model.getClassCategory().getName().equals("Clothes")
        || model.getClassCategory().getName().equals("Cloth")){

            tv_size.setVisibility(View.VISIBLE);
            rv_itemsize.setVisibility(View.VISIBLE);
                chatdelete.setText(mContext.getResources().getString(R.string.clothedetial));
                tv_heding_name.setText(mContext.getResources().getString(R.string.name));
                tv_heding_description.setText(mContext.getResources().getString(R.string.description));
                tv_heading_proce.setText(mContext.getResources().getString(R.string.price));

        }
        oldPassword.setText(model.getName());
        newPassword.setText(model.getDescription());
        tv_book_price.setText("" + model.getPrice() + "K.D");
        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(iv_image);
        rv_itemsize.setLayoutManager(new GridLayoutManager(mContext, 4));
        ItemSizessAdapter adapter = new ItemSizessAdapter(mContext, model.getProductSizes());
        rv_itemsize.setAdapter(adapter);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_add_To_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (model.getClassCategory().getName().equals("clothes") ||
                        model.getClassCategory().getName().equals("cloth") ||
                        model.getClassCategory().getName().equals("Clothes")
                        || model.getClassCategory().getName().equals("Cloth")) {
                    if (type.equals("store")) {
                        if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                            Toast.makeText(mContext, "Please Select Size", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            openDialogBoxsize(model, position);
                        }
                    } else if (type.equals("school") && schoolStoreId.equals(String.valueOf(model.getSchool().getSchoolId()))) {

                        addClothesDataintoCart(model, position);
                        if (open) {
                        } else {
                            dialog.dismiss();
                        }
                    } else if (type.isEmpty()) {
                        addClothesDataintoCart(model, position);
                        if (open) {
                        } else {
                            dialog.dismiss();
                        }
                    } else {
                        if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                            Toast.makeText(mContext, "Please Select Size", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            openDialogBoxsize(model, position);
                        }
                    }
                } else {
                    if (type.equals("store")) {
                        openDialogBox(model, position);
                        dialog.dismiss();
                    } else if (type.equals("school") && schoolStoreId.equals(String.valueOf(model.getSchool().getSchoolId()))) {
                        addToCartSchoolBooks(mData, model, position);
                        dialog.dismiss();
                    } else if (type.isEmpty()) {
                        addToCartSchoolBooks(mData, model, position);
                        dialog.dismiss();
                    } else {
                        openDialogBox(model, position);
                        dialog.dismiss();
                    }
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

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView31,favouritePriceText;
        ImageView img_book_thumbnail, imageView19, imageView21;
        CardView cardView;
        ConstraintLayout clayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView31 = (TextView) itemView.findViewById(R.id.textView31);
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.iv_favorite);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id_item);
            clayout = (ConstraintLayout) itemView.findViewById(R.id.clayout);
            imageView19 = (ImageView) itemView.findViewById(R.id.imageView19);
            imageView21 = (ImageView) itemView.findViewById(R.id.imageView21);
            favouritePriceText = itemView.findViewById(R.id.bookPriceText);

        }
    }

    private void addToCArt(final GetAllSchoolWishListDataModel model, final int position, final List<GetAllSchoolWishListDataModel> mData) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.add_to_cart_dialog);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.yes);
        final Button No = (Button) dialog.findViewById(R.id.no);
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);


        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("store")) {
                    openDialogBox(model, position);
                    dialog.dismiss();
                } else if (type.equals("school") && schoolStoreId.equals(String.valueOf(model.getSchool().getSchoolId()))) {
                    addToCartSchoolBooks(mData, model, position);
                    dialog.dismiss();
                } else if (type.isEmpty()) {
                    addToCartSchoolBooks(mData, model, position);
                    dialog.dismiss();
                } else {
                    openDialogBox(model, position);
                    dialog.dismiss();
                }


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

    /* school  books add into the cart */
    private void addToCartSchoolBooks(List<GetAllSchoolWishListDataModel> mData, GetAllSchoolWishListDataModel model, int position) {
        DbHelper dbHelper = new DbHelper(mContext);
        if (model.getProductSizes().size() == 0 || model.getProductSizes().isEmpty()) {

        } else {
            avalibaleQ = String.valueOf(model.getProductSizes().get(0).getQuantity());
            pSize = String.valueOf(model.getProductSizes().get(0).getProductSizeId());
        }
        Cursor cursor = dbHelper.getDataq(String.valueOf(mData.get(position).getClassProductId()));
        if (cursor.getCount() == 0) {
            boolean isInserted = dbHelper.insertData(mData.get(position).getName(),
                    mData.get(position).getAvatarPath(),
                    (long) mData.get(position).getClassProductId(),
                    1,
                    Long.parseLong(mData.get(position).getPrice()),
                    mData.get(position).getDescription(),
                    "",
                    Integer.parseInt(avalibaleQ), "false",
                    "school",
                    pSize, Integer.parseInt(String.valueOf(mData.get(position).getSchool().getSchoolId())), "book");
            if (isInserted) {
                getSqliteData1();
                Toast.makeText(mContext, "Items Added Succesfully", Toast.LENGTH_SHORT).show();
                removeWishListItem((long) model.getClassProductId(), position);
                dialog.dismiss();
            } else {
                dialog.dismiss();
                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "You have already this item added into cart", Toast.LENGTH_SHORT).show();
            //  removeWishListItem(Long.valueOf(model.getClassProductId()), position);
            // dialog.dismiss();
        }
    }

    /* add colthes into the cart */
    public void addClothesDataintoCart(GetAllSchoolWishListDataModel model, int position) {
        LocalStorage localStorage = new LocalStorage(mContext);
        if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
            Toast.makeText(mContext, "Please Select Size", Toast.LENGTH_SHORT).show();
            open = true;

        } else {
            open = false;
//            dialog.dismiss();
                       /* String dummyId = localStorage.getString(LocalStorage.Dummy_School_ID);
                        String storeId = localStorage.getString(LocalStorage.schoolId);

                        if (dummyId.equals(storeId) || dummyId.equals("")) {
                            localStorage.putString(LocalStorage.Dummy_School_ID, localStorage.getString(LocalStorage.schoolId));*/
            DbHelper dbHelper = new DbHelper(mContext);
            Cursor cursor = dbHelper.getDataq(String.valueOf(model.getClassProductId()));
            if (cursor.getCount() == 0) {
                boolean isInserted = dbHelper.insertData(model.getName(),
                        model.getAvatarPath(),
                        Long.valueOf(model.getClassProductId()),
                        1,
                        Long.parseLong(model.getPrice()),
                        model.getDescription(),
                        "",
                        Integer.parseInt(localStorage.getString(LocalStorage.PQUANTITY)),
                        "false", "school", localStorage.getString(LocalStorage.SIZEEID), model.getSchool().getSchoolId(), "cloth");
                if (isInserted) {
                    getSqliteData1();
                    removeWishListItem(Long.valueOf(model.getClassProductId()), position);
                    localStorage.putString(LocalStorage.SIZEEID, "");
                    localStorage.putString(LocalStorage.PQUANTITY, "");

                    Toast.makeText(mContext, "add item to wishlist sucessfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "You have already this item added into wishlist", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openDialogBox(final GetAllSchoolWishListDataModel model, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("you have already select books from another store .Are you sure Want to delete existing cart item");
        builder.setTitle("Delete Cart Item ");
        //Setting message manually and performing action on button click
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                DbHelper dbHelper = new DbHelper(mContext);
                dbHelper.deleteAll();

                if (model.getProductSizes().size() == 0 || model.getProductSizes().isEmpty()) {

                } else {
                    avalibaleQ = String.valueOf(model.getProductSizes().get(0).getQuantity());
                    pSize = String.valueOf(model.getProductSizes().get(0).getProductSizeId());
                }
                boolean isInserted = dbHelper.insertData(model.getName(),
                        model.getAvatarPath(),
                        Long.valueOf(model.getClassProductId()),
                        1,
                        Long.parseLong(model.getPrice()),
                        model.getDescription(),
                        "",
                        Integer.parseInt(avalibaleQ), "false",
                       "school",
                        pSize,model.getSchool().getSchoolId(),"book");

                if (isInserted) {
                    getSqliteData1();

                    removeWishListItem(Long.valueOf(model.getClassProductId()), position);
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

    private void openDialogBoxsize(final GetAllSchoolWishListDataModel model, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("you have already select books from another store .Are you sure Want to delete existing cart item");
        builder.setTitle("Delete Cart Item ");
        //Setting message manually and performing action on button click
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                DbHelper dbHelper = new DbHelper(mContext);
                dbHelper.deleteAll();
                if (model.getProductSizes().size() == 0 || model.getProductSizes().isEmpty()) {
                } else {
                    avalibaleQ = String.valueOf(model.getProductSizes().get(0).getQuantity());
                    pSize = String.valueOf(model.getProductSizes().get(0).getProductSizeId());
                }
                boolean isInserted = dbHelper.insertData(model.getName(),
                        model.getAvatarPath(),
                        (long) model.getClassProductId(),
                        1,
                        Long.parseLong(model.getPrice()),
                        model.getDescription(),
                        "",
                        Integer.parseInt(localStorage.getString(LocalStorage.PQUANTITY)),
                        "false", "school",
                        localStorage.getString(LocalStorage.SIZEEID),
                        model.getSchool().getSchoolId(), "cloth");

                if (isInserted) {
                    getSqliteData1();
                    localStorage.putString(LocalStorage.SIZEEID, "");
                    localStorage.putString(LocalStorage.PQUANTITY, "");
                    removeWishListItem(Long.valueOf(model.getClassProductId()), position);
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
                    shoppingBagModel.setSize(json_data.getString("size"));
                    shoppingBagModel.setType(json_data.getString("type"));
                    shoppingBagModel.setSchoolStoreId(json_data.getString("schoolStoreId"));
                    type = json_data.getString("type");
                    schoolStoreId = json_data.getString("schoolStoreId");
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

    private void opendialogproductsize(final GetAllSchoolWishListDataModel model, final int position, final List<GetAllSchoolWishListDataModel> mData) {
        final Dialog dialog = new Dialog(mContext, R.style.dialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_size);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.yes);
        final Button No = (Button) dialog.findViewById(R.id.no);
        final RecyclerView rv_itemsize = (RecyclerView) dialog.findViewById(R.id.rv_p_size);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);


        rv_itemsize.setLayoutManager(new GridLayoutManager(mContext, 3));
        ItemSizessAdapter adapter = new ItemSizessAdapter(mContext, model.getProductSizes());
        rv_itemsize.setAdapter(adapter);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("store")) {
                    if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                        Toast.makeText(mContext, "Please Select Size", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        openDialogBoxsize(model, position);
                    }
                } else if (type.equals("school") && schoolStoreId.equals(String.valueOf(model.getSchool().getSchoolId()))) {

                    addClothesDataintoCart(model, position);
                    if (open) {
                    } else {
                        dialog.dismiss();
                    }
                } else if (type.isEmpty()) {
                    addClothesDataintoCart(model, position);
                    if (open) {
                    } else {
                        dialog.dismiss();
                    }
                } else {
                    if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                        Toast.makeText(mContext, "Please Select Size", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        openDialogBoxsize(model, position);
                    }
                }

              /*  if (type.equals("store")) {
                    if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                        Toast.makeText(mContext, "Please Select Size", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        openDialogBoxsize(model, position);
                    }
                } else {*/


                      /*  } else {
                            openDialogBoxsize(model, position);
                        }*/


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

    private void updateQuantity(Long pid, String s) {
        dbHelper = new DbHelper(mContext);
        boolean isupdated = dbHelper.updatewish(String.valueOf(pid), s);
        if (isupdated == true) {
            recyclerViewClickListener.onClickPosition(25);
            getSqliteData1();
        } else {
            Utils.showAlertDialog((Activity) mContext, "something went wrong");
        }
    }


}