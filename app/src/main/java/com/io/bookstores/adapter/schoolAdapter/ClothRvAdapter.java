package com.io.bookstores.adapter.schoolAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.holder.ClothHolder;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstores.model.bookListModel.WishListLocalResponseModel;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.productModel.ProductDataModel;
import com.io.bookstores.model.wishlistModel.AddorRemoveWishlistResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ClothRvAdapter extends RecyclerView.Adapter<ClothHolder> {

    private Activity activity;
    public static ArrayList<WishListLocalResponseModel> lists = new ArrayList<>();
    public static ArrayList<CartLocalListResponseMode> list = new ArrayList<>();
    String name = " ";
    String type = "";
    String avalibaleQ = " ";
    String pSize = " ";
    userOnlineInfo user;
    NewProgressBar dialog;
    ItemClickListner itemClickListner;
    private List<ProductDataModel> data;
    private LoginModel loginModel;
    private DbHelper dbHelper;
    private String wishlistdata;
    private ItemSizeAdapter adapter;
    private RecyclerViewClickListener recyclerViewClickListener;
    private LocalStorage localStorage;


    public ClothRvAdapter(Activity activity, List<ProductDataModel> data) {
        this.activity = activity;
        this.data = data;
    }

    @NotNull
    @Override
    public ClothHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_cloths, parent, false);
        itemClickListner = (ItemClickListner) activity;
        getSqliteData1();
        getWishListStatus();
        dbHelper = new DbHelper(activity);
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
        loginModel = localStorage.getUserProfile();
        return new ClothHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ClothHolder holder, final int position) {
        getWishListStatus();
        final ProductDataModel model = data.get(position);
        holder.tv_price.setText(model.getPrice() + "K.D");
        Glide.with(activity).load(Config.imageUrl + model.getAvatarPath()).into(holder.ivClothImage);
        for (int i = 0; i < model.getProductSizes().size(); i++) {
            name += model.getProductSizes().get(i).getMinSize() + " - " + model.getProductSizes().get(i).getMaxSize();
        }

        if (loginModel == null) {
            getWishListStatus();
            for (int ii = 0; ii <= lists.size() - 1; ii++) {
                if (model.getClassProductId() == Long.parseLong(lists.get(ii).getpID())) {
                    holder.mark_fav.setVisibility(View.GONE);
                    holder.mark_fav_red.setVisibility(View.VISIBLE);
                } else {
                    holder.mark_fav.setVisibility(View.VISIBLE);
                    holder.mark_fav_red.setVisibility(View.GONE);
                }
            }
        } else {
            if (model.isIsWishlist() == true) {
                holder.mark_fav.setVisibility(View.GONE);
                holder.mark_fav_red.setVisibility(View.VISIBLE);
            } else {
                holder.mark_fav.setVisibility(View.VISIBLE);
                holder.mark_fav_red.setVisibility(View.GONE);
            }

        }
        holder.tv_cloth_size.setText(" Size " + name);
        holder.ivClothImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clothDialogOpen(model, data, position);
            }
        });
        holder.mark_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clothDialogOpen(model, data, position);
            }
        });
        holder.mark_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginModel == null) {
                    opendialogproductsize(model, holder, position);
                } else {
                    holder.mark_fav.setVisibility(View.GONE);
                    holder.mark_fav_red.setVisibility(View.VISIBLE);
                    updateQuantity(Long.valueOf(model.getClassProductId()), "true");
                    addorRemomoveWishlist(Long.valueOf(model.getClassProductId()));
                }
            }
        });
        holder.mark_fav_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginModel == null) {
                    DbHelper dbHelper;
                    dbHelper = new DbHelper(activity);
                    Cursor cursor = dbHelper.getData();
                    boolean isdeleted = false;
                    isdeleted = dbHelper.deleteOneWishList(String.valueOf(model.getClassProductId()));

                    if (isdeleted == true) {
                        holder.mark_fav.setVisibility(View.VISIBLE);
                        holder.mark_fav_red.setVisibility(View.GONE);
                        Toast.makeText(activity, "wishlist delete sucessfully", Toast.LENGTH_SHORT).show();
                        getWishListStatus();

                    } else {
                        Toast.makeText(activity, "Data is not deleted", Toast.LENGTH_SHORT).show();
                        getWishListStatus();
                    }
                } else {
                    holder.mark_fav.setVisibility(View.VISIBLE);
                    holder.mark_fav_red.setVisibility(View.GONE);
                    updateQuantity(Long.valueOf(model.getClassProductId()), "false");
                    addorRemomoveWishlist(Long.valueOf(model.getClassProductId()));
                }
            }
        });

    }

    private void opendialogproductsize(final ProductDataModel model, final ClothHolder holder, final int position) {
        final Dialog dialog = new Dialog(activity, R.style.dialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
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


        rv_itemsize.setLayoutManager(new GridLayoutManager(activity, 3));
        adapter = new ItemSizeAdapter(activity, model.getProductSizes(), itemClickListner);
        rv_itemsize.setAdapter(adapter);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("store")) {
                    if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                        Toast.makeText(activity, "Please Select Size", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        openDialogBoxWishList(model, position, holder);
                    }
                } else {
                    LocalStorage localStorage = new LocalStorage(activity);
                    if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                        Toast.makeText(activity, "Please Select Size", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        String dummyId = localStorage.getString(LocalStorage.Dummy_School_ID);
                        String storeId = localStorage.getString(LocalStorage.schoolId);

                        if (dummyId.equals(storeId) || dummyId.equals("")) {
                            localStorage.putString(LocalStorage.Dummy_School_ID, localStorage.getString(LocalStorage.schoolId));
                            DbHelper dbHelper = new DbHelper(activity);
                            Cursor cursor = dbHelper.getOneWishList(String.valueOf(model.getClassProductId()));
                            if (cursor.getCount() == 0) {
                                boolean isInserted = dbHelper.insertWishList(model.getName(),
                                        model.getAvatarPath(),
                                        Long.valueOf(model.getClassProductId()),
                                        1,
                                        Long.parseLong(model.getPrice()),
                                        model.getDescription(),
                                        "",
                                        Integer.parseInt(localStorage.getString(LocalStorage.PQUANTITY)),
                                        "true", "school", localStorage.getString(LocalStorage.SIZEEID));
                                if (isInserted) {
                                    holder.mark_fav.setVisibility(View.GONE);
                                    holder.mark_fav_red.setVisibility(View.VISIBLE);
                                    getWishListStatus();
                                    Toast.makeText(activity, "add item to wishlist sucessfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(activity, "You have already this item added into wishlist", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            openDialogBoxWishList(model, position, holder);
                        }
                    }
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


    @Override
    public int getItemCount() {
        return data.size();
    }

    /*---------------------------------------------- cloth detial dialog open -----------------------------------------------*/
    private void clothDialogOpen(final ProductDataModel model, List<ProductDataModel> data, final int position) {
        final Dialog dialog = new Dialog(activity, R.style.dialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_cloth);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.btn_add_To_cart);
        final Button No = (Button) dialog.findViewById(R.id.btn_dismiss);
        final TextView tv_item_name = (TextView) dialog.findViewById(R.id.tv_item_name);
        final RecyclerView rv_itemsize = (RecyclerView) dialog.findViewById(R.id.rv_itemsize);
        final TextView tv_description = (TextView) dialog.findViewById(R.id.tv_description);
        final TextView tv_price = (TextView) dialog.findViewById(R.id.tv_price);
        final ImageView iv_back = (ImageView) dialog.findViewById(R.id.iv_back);
        final ImageView iv_item_image = (ImageView) dialog.findViewById(R.id.iv_item_image);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.iv_cancel);

        tv_item_name.setText(model.getName());
        tv_description.setText(model.getDescription());
        tv_price.setText(model.getPrice() + "K.D.");
        Glide.with(activity).load(Config.imageUrl + model.getAvatarPath()).into(iv_item_image);
        rv_itemsize.setLayoutManager(new GridLayoutManager(activity, 3));
        adapter = new ItemSizeAdapter(activity, model.getProductSizes(), itemClickListner);
        rv_itemsize.setAdapter(adapter);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("store")) {
                    if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                        Toast.makeText(activity, "Please Select Size", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        openDialogBoxs(model, position);
                    }
                } else {
                    LocalStorage localStorage = new LocalStorage(activity);
                    if (localStorage.getString(LocalStorage.SIZEEID).equals("")) {
                        Toast.makeText(activity, "Please Select Size", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        String dummyId = localStorage.getString(LocalStorage.Dummy_School_ID);
                        String schoolId = localStorage.getString(LocalStorage.schoolId);
                        if (dummyId.equals(schoolId) || dummyId.equals("")) {
                            localStorage.putString(LocalStorage.Dummy_School_ID, localStorage.getString(LocalStorage.schoolId));
                            DbHelper dbHelper = new DbHelper(activity);
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
                                        String.valueOf(model.isIsWishlist()),
                                        localStorage.getString(LocalStorage.TYPE),
                                        localStorage.getString(LocalStorage.SIZEEID));
                                if (isInserted) {
                                    getSqliteData1();
                                    localStorage.putString(LocalStorage.SIZEEID, "");
                                    localStorage.putString(LocalStorage.PQUANTITY, "");
                                    Toast.makeText(activity, "Items Added Succesfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(activity, "You have already this item added into cart", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            openDialogBox(model, position);
                        }
                    }
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
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openDialogBoxs(final ProductDataModel model, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("you have already select books from another Store .Are you sure Want to delete existing cart item");
        builder.setTitle("Delete Cart Item");
        //Setting message manually and performing action on button click
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                LocalStorage localStorage = new LocalStorage(activity);
                if (model.getProductSizes().size() == 0 || model.getProductSizes().isEmpty()) {

                } else {
                    avalibaleQ = String.valueOf(model.getProductSizes().get(0).getQuantity());
                    pSize = String.valueOf(model.getProductSizes().get(0).getProductSizeId());
                }

                localStorage.putString(LocalStorage.Dummy_School_ID, localStorage.getString(LocalStorage.schoolId));
                DbHelper dbHelper = new DbHelper(activity);
                dbHelper.deleteAll();
                boolean isInserted = dbHelper.insertData(model.getName(),
                        model.getAvatarPath(),
                        Long.valueOf(model.getClassProductId()),
                        1,
                        Long.parseLong(model.getPrice()),
                        model.getDescription(),
                        "",
                        Integer.parseInt(localStorage.getString(LocalStorage.PQUANTITY)),
                        String.valueOf(model.isIsWishlist()),
                        localStorage.getString(LocalStorage.TYPE),
                        localStorage.getString(LocalStorage.SIZEEID));

                if (isInserted) {
                    getSqliteData1();
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

    private void openDialogBox(final ProductDataModel model, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("you have already select books from another school .Are you sure Want to delete existing cart item");
        builder.setTitle("Delete Cart Item");
        //Setting message manually and performing action on button click
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                LocalStorage localStorage = new LocalStorage(activity);
                if (model.getProductSizes().size() == 0 || model.getProductSizes().isEmpty()) {

                } else {
                    avalibaleQ = String.valueOf(model.getProductSizes().get(0).getQuantity());
                    pSize = String.valueOf(model.getProductSizes().get(0).getProductSizeId());
                }
                localStorage.putString(LocalStorage.Dummy_School_ID, localStorage.getString(LocalStorage.schoolId));
                DbHelper dbHelper = new DbHelper(activity);
                dbHelper.deleteAll();
                boolean isInserted = dbHelper.insertData(model.getName(),
                        model.getAvatarPath(),
                        Long.valueOf(model.getClassProductId()),
                        1,
                        Long.parseLong(model.getPrice()),
                        model.getDescription(),
                        "",
                        Integer.parseInt(localStorage.getString(LocalStorage.PQUANTITY)),
                        String.valueOf(model.isIsWishlist()),
                        localStorage.getString(LocalStorage.TYPE),
                        localStorage.getString(LocalStorage.SIZEEID));

                if (isInserted) {
                    getSqliteData1();
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

    private void openDialogBoxWishList(final ProductDataModel model, final int position, final ClothHolder holder) {
        localStorage.putString(LocalStorage.Dummy_School_ID, localStorage.getString(LocalStorage.schoolId));
        DbHelper dbHelper = new DbHelper(activity);
        Cursor cursor = dbHelper.getOneWishList(String.valueOf(model.getClassProductId()));
        if (cursor.getCount() == 0) {
            boolean isInserted = dbHelper.insertWishList(model.getName(),
                    model.getAvatarPath(),
                    Long.valueOf(model.getClassProductId()),
                    1,
                    Long.parseLong(model.getPrice()),
                    model.getDescription(),
                    "",
                    Integer.parseInt(localStorage.getString(LocalStorage.PQUANTITY)),
                    "true", "school", localStorage.getString(LocalStorage.SIZEEID));
            if (isInserted) {
                holder.mark_fav.setVisibility(View.GONE);
                holder.mark_fav_red.setVisibility(View.VISIBLE);
                getWishListStatus();
                Toast.makeText(activity, "add item to wishlist sucessfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getSqliteData1() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(activity);
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
                    shoppingBagModel.setSize(json_data.getString("size"));
                    shoppingBagModel.setType(json_data.getString("type"));
                    type = json_data.getString("type");
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

    /* ---------------------------------------------- add or remove wishlist api -----------------------------------------*/

    private void addorRemomoveWishlist(Long studentBookId) {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(activity);
            ApiCaller.addOrRemoveWishListschool((Activity) activity, Config.Url.addorremoveWishlistSchool + studentBookId, localStorage.getString(LocalStorage.token),
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
        boolean isupdated = dbHelper.updatewish(String.valueOf(pid), s);
        if (isupdated == true) {
            getSqliteData1();
        } else {
            Utils.showAlertDialog((Activity) activity, "something went wrong");
        }
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

    private void opendialogwish(final ProductDataModel model, int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("you have already select books from another school .Are you sure Want to delete existing wishlist item");
        builder.setTitle("Delete Wishlist Item");
        //Setting message manually and performing action on button click
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                LocalStorage localStorage = new LocalStorage(activity);
                if (model.getProductSizes().size() == 0 || model.getProductSizes().isEmpty()) {

                } else {
                    avalibaleQ = String.valueOf(model.getProductSizes().get(0).getQuantity());
                    pSize = String.valueOf(model.getProductSizes().get(0).getProductSizeId());
                }
                localStorage.putString(LocalStorage.Dummy_School_ID, localStorage.getString(LocalStorage.schoolId));
                DbHelper dbHelper = new DbHelper(activity);
                dbHelper.deleteAll();
                boolean isInserted = dbHelper.insertWishList(model.getName(),
                        model.getAvatarPath(),
                        Long.valueOf(model.getClassProductId()),
                        1,
                        Long.parseLong(model.getPrice()),
                        model.getDescription(),
                        "",
                        Integer.parseInt(avalibaleQ), "true", "school", pSize);

                if (isInserted) {
                    getWishListStatus();
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

}
