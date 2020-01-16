package com.io.bookstore.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstore.model.adminResponseModel.AdminBookDataModel;
import com.io.bookstore.model.adminResponseModel.AdminBookListResponseModel;
import com.io.bookstore.model.adminResponseModel.DeleteBookResponseModel;
import com.io.bookstore.model.storeModel.Datum;
import com.io.bookstore.utility.ImageUtility;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.PermissionFile;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class AdminBookListAdapter extends RecyclerView.Adapter<AdminBookListAdapter.MyViewHolder> {

    private Context mContext;
    private List<AdminBookDataModel> mData;
    LocalStorage localStorage;
    userOnlineInfo user;
    NewProgressBar dialog;
    Dialog dialogs;
    private static final int REQUEST_WRITE_STORAGE = 1004;
    private static final int REQUEST_CODE_LOCATION = 1000;
    private static final int REQUEST_CODE_STORAGE = 1003;
    private static int GalleryPicker = 123;
    private PermissionFile permissionFile;
    private String licenseFile = "";
    private ImageUtility imageUtility;
    private File destination;
    private Uri outputFileUri;
    int CameraPicker = 124;
    ImageView imageView;
    Spinner spin;
    String spindata;
    private String[] items = {" --Select Category-- ", "Arabic Books", "English Books", "Computer Supplies", "Games toys", "School Supplies",
            "Kids", "Office", "Art", "Smartphones"};
    String[] categoryId = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private FloatingActionButton floatingActionButton;
    File imagefile;


    public AdminBookListAdapter(Context mContext, List<AdminBookDataModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag = false;
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.book_list_item, parent, false);
        localStorage = new LocalStorage(mContext);
        user = new userOnlineInfo();
        dialogs = new Dialog(mContext);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final AdminBookDataModel model = mData.get(position);

        holder.tv_BookName.setText(model.getName());
        holder.tv_book_desc.setText(model.getDescription());
        // holder.tv_bookstore_desc.setText(mData.get(position).getDescription());
        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_bookstore_thumbnail);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBookApiCall(localStorage.getString(LocalStorage.token), model.getBookId(), position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForAddBook(mData.get(position));
            }
        });
    }
    public void setFilter(List<AdminBookDataModel> newlist){
        mData=new ArrayList<>();
        mData.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_BookName, tv_book_desc;
        ImageView iv_bookstore_thumbnail;
        CardView cardView;
        Button btnEdit, btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_BookName = (TextView) itemView.findViewById(R.id.tv_bookName);
            tv_book_desc = (TextView) itemView.findViewById(R.id.tv_bookDesc);
            iv_bookstore_thumbnail = (ImageView) itemView.findViewById(R.id.iv_bookstore_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_bookstore);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.button4);

        }
    }

    private void deleteBookApiCall(String token, Integer bookId, final int position) {

        if (user.isOnline(mContext)) {
            dialog = new NewProgressBar(mContext);
            dialog.show();

            ApiCaller.deleteBook(mContext, Config.Url.adminDeleteBook + bookId, localStorage.getString(LocalStorage.token),
                    new FutureCallback<DeleteBookResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, DeleteBookResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                                return;
                            }

                            if (result.getStatus() == true) {
                                dialog.dismiss();
                                Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                mData.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeRemoved(position, mData.size());
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


    private void dialogOpenForAddBook(AdminBookDataModel adminBookDataModel) {
        dialogs = new Dialog(mContext);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialogs.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogs.setContentView(R.layout.edit_book);
        dialogs.setTitle("");
        final Button Yes = (Button) dialogs.findViewById(R.id.yes);
        final Button No = (Button) dialogs.findViewById(R.id.no);
        final EditText tvName = (EditText) dialogs.findViewById(R.id.tv_book_name);
        final EditText tvDesc = (EditText) dialogs.findViewById(R.id.tv_book_Descrption);
        final EditText price = (EditText) dialogs.findViewById(R.id.tv_book_price);
        final EditText quantity = (EditText) dialogs.findViewById(R.id.tv_book_quantity);
        spin = (Spinner) dialogs.findViewById(R.id.category_spinner);

        imageView = (ImageView) dialogs.findViewById(R.id.image);
        tvName.setText(adminBookDataModel.getName());
        tvDesc.setText(adminBookDataModel.getDescription());
      /*  price.setText(adminBookDataModel.getPrice());
        quantity.setText(adminBookDataModel.getQuantity());*/

        tvDesc.setText(adminBookDataModel.getDescription());
        Glide.with(mContext).load(Config.imageUrl + adminBookDataModel.getAvatarPath()).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readWritePermission();
                multiplePermission();
                galleryIntent();
            }
        });
        ArrayAdapter aa = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spindata = categoryId[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString().trim();
                String descrption = tvDesc.getText().toString().trim();
                String Price = price.getText().toString().trim();
                String Quantity = quantity.getText().toString().trim();
                addBook(name, descrption, Price, Quantity, imageView, licenseFile, spindata);
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });
        dialogs.show();


    }

    private void addBook(String strBookName, String strDescrpition, String strPrice, String strQuantity, ImageView imageView,
                         String licenseFile, String spindata) {

        if (strBookName.isEmpty() || strDescrpition.isEmpty() || strPrice.isEmpty() || strQuantity.isEmpty() || spindata.equals(" ")) {
            Toast.makeText(mContext, "please enter data", Toast.LENGTH_SHORT).show();
        } else {
            addDataIntoApi(strBookName, strDescrpition, strPrice, strQuantity, licenseFile, spindata);

        }
    }


    /* ------------------------------------------ read and write Permission for picture ---------------------------------------------*/

    private void readWritePermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    private void multiplePermission() {
        if (!permissionFile.checkLocStorgePermission(mContext)) {
            permissionFile.checkLocStorgePermission(mContext);
        }
    }


    /*---------------------------------------------- pick Image from the gallery using intent ---------------------------------------*/

    private void galleryIntent() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        ((Activity) mContext).startActivityForResult(pickIntent, GalleryPicker);

    }




    /* --------------------------------- get the actual storage path of image (Camera an dgallery) ----------------------------------*/

    void onCaptureImageResult(Intent data, String imageType) {
        if (imageType.equals("camera")) {
            licenseFile = imageUtility.compressImage(destination.getPath());
            Toast.makeText(mContext, "submit", Toast.LENGTH_SHORT).show();
            Log.e("camerapic", licenseFile);
            imagefile = new File(licenseFile);
            if (imagefile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }

        } else {
            licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI((Activity) mContext, data.getData()));
            Toast.makeText(mContext, "submit", Toast.LENGTH_SHORT).show();
            Log.e("gallerypic", licenseFile);

            File imgFile = new File(licenseFile);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }

        }
    }

    private void addDataIntoApi(String strBookName, String strDescrpition, String strPrice, String strQuantity, String licenseFile, String spindata) {
    }


}