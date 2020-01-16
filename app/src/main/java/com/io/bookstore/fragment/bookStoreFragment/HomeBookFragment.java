package com.io.bookstore.fragment.bookStoreFragment;

import android.Manifest;
import android.R.layout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.AdminBookListAdapter;
import com.io.bookstore.adapter.BookListAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.ApiImage;
import com.io.bookstore.listeners.ServiceGenerator;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.adminResponseModel.AddBookResponseModel;
import com.io.bookstore.model.adminResponseModel.AdminBookListResponseModel;
import com.io.bookstore.model.bookListModel.BookListModel;
import com.io.bookstore.model.bookListModel.Datum;
import com.io.bookstore.model.editProfileResponseModel.EditProfileResponseModel;
import com.io.bookstore.utility.ImageUtility;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.PermissionFile;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.R.layout.*;
import static android.app.Activity.RESULT_OK;

public class HomeBookFragment extends Fragment implements View.OnClickListener {


    private Activity activity;
    private RecyclerView rvBookStore;
    private AdminBookListAdapter adapter;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private Dialog dialogs;
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
    LocalStorage localStorage;
    private String[] items = {" --Select Category-- ", "Arabic Books", "English Books", "Computer Supplies", "Games toys", "School Supplies",
            "Kids", "Office", "Art", "Smartphones"};
    String[] categoryId = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private FloatingActionButton floatingActionButton;
    File imagefile;

    public HomeBookFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_book_layout, container, false);
        intializeViews(view);
        bindListner();
        startWorking();
        return view;

    }


    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
        permissionFile = new PermissionFile(activity);
        imageUtility = new ImageUtility(activity);
        rvBookStore = view.findViewById(R.id.recyclerView_bookstore);
        floatingActionButton = view.findViewById(R.id.floating);
    }

    private void bindListner() {
        floatingActionButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating:
                Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show();
                dialogOpenForAddBook();
                return;
        }

    }


    private void startWorking() {
        getBookList();

    }

    private void getBookList() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getAdminBookList(getActivity(), Config.Url.getAllBook, 1, 1, "",
                    new FutureCallback<AdminBookListResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, AdminBookListResponseModel result) {
                            dialog.dismiss();
                            setRecyclerViewData(result);

                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }


    @SuppressLint("WrongConstant")
    private void setRecyclerViewData(AdminBookListResponseModel result) {
        rvBookStore.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter = new AdminBookListAdapter(activity, result.getData());
        rvBookStore.setAdapter(adapter);

    }

    private void dialogOpenForAddBook() {
        dialogs = new Dialog(activity);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialogs.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogs.setContentView(R.layout.add_book_card_design);
        dialogs.setTitle("");
        final Button Yes = (Button) dialogs.findViewById(R.id.yes);
        final Button No = (Button) dialogs.findViewById(R.id.no);
        final EditText tvName = (EditText) dialogs.findViewById(R.id.tv_book_name);
        final EditText tvDesc = (EditText) dialogs.findViewById(R.id.tv_book_Descrption);
        final EditText price = (EditText) dialogs.findViewById(R.id.tv_book_price);
        final EditText quantity = (EditText) dialogs.findViewById(R.id.tv_book_quantity);
        spin = (Spinner) dialogs.findViewById(R.id.category_spinner);

        imageView = (ImageView) dialogs.findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readWritePermission();
                multiplePermission();
                galleryIntent();
            }
        });
        ArrayAdapter aa = new ArrayAdapter(activity, layout.simple_list_item_1, items);
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
                String  name = tvName.getText().toString().trim();
                String  descrption = tvDesc.getText().toString().trim();
                String  Price = price.getText().toString().trim();
                String  Quantity = quantity.getText().toString().trim();
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

        if (strBookName.isEmpty() || strDescrpition.isEmpty() || strPrice.isEmpty() || strQuantity.isEmpty() || spindata.equals(" ") ) {
            Toast.makeText(activity, "please enter data", Toast.LENGTH_SHORT).show();
        } else {
            addDataIntoApi(strBookName, strDescrpition, strPrice, strQuantity, licenseFile, spindata);

        }
    }

    /* ------------------------------------------ read and write Permission for picture ---------------------------------------------*/

    private void readWritePermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    private void multiplePermission() {
        if (!permissionFile.checkLocStorgePermission(activity)) {
            permissionFile.checkLocStorgePermission(activity);
        }
    }


    /*---------------------------------------------- pick Image from the gallery using intent ---------------------------------------*/

    private void galleryIntent() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, GalleryPicker);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == GalleryPicker) {
                onCaptureImageResult(data, "gallery");
            }
        } else if (resultCode == RESULT_OK && requestCode == CameraPicker) {
            onCaptureImageResult(data, "camera");

        }
    }

    /* --------------------------------- get the actual storage path of image (Camera an dgallery) ----------------------------------*/

    void onCaptureImageResult(Intent data, String imageType) {
        if (imageType.equals("camera")) {
            licenseFile = imageUtility.compressImage(destination.getPath());
            Toast.makeText(activity, "submit", Toast.LENGTH_SHORT).show();
            Log.e("camerapic", licenseFile);
             imagefile = new File(licenseFile);
            if (imagefile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }

        } else {
            licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI(activity, data.getData()));
            Toast.makeText(activity, "submit", Toast.LENGTH_SHORT).show();
            Log.e("gallerypic", licenseFile);

            File imgFile = new File(licenseFile);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }

        }
    }





    private void addDataIntoApi(String strBookName, String strDescrpition, String strPrice, String strQuantity, String licenseFile, String spindata) {
  /*      if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.upload(activity, Config.Url.addbook, strBookName, strDescrpition, spindata, strQuantity, strPrice, localStorage.getString(LocalStorage.token), licenseFile, new FutureCallback<AddBookResponseModel>() {
                @Override
                public void onCompleted(Exception e, AddBookResponseModel result) {
                    if (result.getStatus() == true) {
                        dialog.dismiss();
                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
*/
        ApiImage jsonPostService = ServiceGenerator.createService(ApiImage.class, "http://157.175.48.7/api/");

        RequestBody bookname = RequestBody.create(MediaType.parse("text/plain"), strBookName);
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), strDescrpition);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), strPrice);
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), strQuantity);
        RequestBody image = RequestBody.create(MediaType.parse("image/*"), licenseFile);
        RequestBody catId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(categoryId));


        Callback<AddBookResponseModel> call =
                jsonPostService.uploadImage("Bearer " + localStorage.getString(LocalStorage.token),
                "store",image,bookname,catId,desc,price,quantity);
        call.equals(new Callback<AddBookResponseModel>() {
            @Override
            public void onResponse(Call<AddBookResponseModel> call, Response<AddBookResponseModel> response) {
                Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddBookResponseModel> call, Throwable t) {

            }
        });
    }

}
