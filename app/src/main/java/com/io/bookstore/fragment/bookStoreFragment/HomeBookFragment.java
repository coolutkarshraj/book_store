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
import android.os.Handler;
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
import com.io.bookstore.adapter.AdminBookListAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.EditBookResponseModel;
import com.io.bookstore.model.adminResponseModel.AddBookResponseModel;
import com.io.bookstore.model.adminResponseModel.AdminBookDataModel;
import com.io.bookstore.model.adminResponseModel.AdminBookListResponseModel;
import com.io.bookstore.utility.ImageUtility;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.PermissionFile;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import static android.app.Activity.RESULT_OK;

public class HomeBookFragment extends Fragment implements View.OnClickListener, RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {


    private Activity activity;
    private RecyclerView rvBookStore;
    private AdminBookListAdapter adapter;
    private List<AdminBookDataModel> item;
    private NewProgressBar dialog;
    SearchView searchView;
    private userOnlineInfo user;
    private Dialog dialogs;
    private static final int REQUEST_WRITE_STORAGE = 1004;
    private static int GalleryPicker = 123;
    private PermissionFile permissionFile;
    private String licenseFile = "";
    private ImageUtility imageUtility;
    private File destination;
    private Uri outputFileUri;
    int CameraPicker = 124;
    ImageView imageView, ivFilter;
    Spinner spin;

    String strName, strDesc, strImage, strPrice, strQuantity, strCategory,strAuthor;
    String spindata = "-1";
    LocalStorage localStorage;
    private String[] items = {" Select Category ", "Arabic Books", "English Books", "Computer Supplies", "Games toys", "School Supplies",
            "Kids", "Office", "Art", "Smartphones"};
    String[] categoryId = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private FloatingActionButton floatingActionButton;
    File imagefile;
    private File imgFile;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeBookFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_book_layout, container, false);
        spindata = "-1";
        intializeViews(view);
        bindListner();
        startWorking();
        return view;

    }


    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        item = new ArrayList<>();
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        searchView = view.findViewById(R.id.searchView2);
        localStorage = new LocalStorage(activity);
        permissionFile = new PermissionFile(activity);
        imageUtility = new ImageUtility(activity);
        rvBookStore = view.findViewById(R.id.recyclerView_bookstore);
        floatingActionButton = view.findViewById(R.id.floating);
        ivFilter = view.findViewById(R.id.iv_filter);
    }

    private void bindListner() {
        floatingActionButton.setOnClickListener(this);
        ivFilter.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating:
                dialogOpenForAddBook();
                return;

            case R.id.iv_filter:
                dialogOfFilter();
                return;
        }

    }


    private void startWorking() {
        getBookList();
        searchViewSetUp();

    }

    private void getBookList() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getAdminBookList(getActivity(), Config.Url.getAllBook + localStorage.getInt(LocalStorage.userId) + "/" + spindata,localStorage.getString(LocalStorage.token),
                    new FutureCallback<AdminBookListResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, AdminBookListResponseModel result) {
                            dialog.dismiss();
                            if (result == null) {
                                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                                return;
                            }
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
        if(result.getData().size() == 0 ) {

        }else {
            adapter = new AdminBookListAdapter(activity, result.getData(), this);
            item = result.getData();
            rvBookStore.setAdapter(adapter);
        }


    }

    private void dialogOpenForAddBook() {
        dialogs = new Dialog(activity, R.style.dialogTheme);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialogs.getWindow().setLayout((6 * width), ViewGroup.LayoutParams.MATCH_PARENT);
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogs.setContentView(R.layout.add_book_card_design);
        final Button Yes = (Button) dialogs.findViewById(R.id.yes);
        final Button No = (Button) dialogs.findViewById(R.id.no);
        final EditText tvName = (EditText) dialogs.findViewById(R.id.tv_book_name);
        final EditText tvDesc = (EditText) dialogs.findViewById(R.id.tv_book_Descrption);
        final EditText price = (EditText) dialogs.findViewById(R.id.tv_book_price);
        final EditText quantity = (EditText) dialogs.findViewById(R.id.tv_book_quantity);
        final EditText tv_book_author = (EditText) dialogs.findViewById(R.id.tv_book_author);
        spin = (Spinner) dialogs.findViewById(R.id.category_spinner);

        imageView = (ImageView) dialogs.findViewById(R.id.image);
        ImageView clear = (ImageView) dialogs.findViewById(R.id.clear);
        readWritePermission();
        multiplePermission();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                String name = tvName.getText().toString().trim();
                String descrption = tvDesc.getText().toString().trim();
                String Price = price.getText().toString().trim();
                String Quantity = quantity.getText().toString().trim();
                String author = tv_book_author.getText().toString().trim();
                addBook(name, descrption, Price, Quantity, imageView, licenseFile, spindata, author, dialogs);

            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });
        dialogs.show();


    }

    private void addBook(String strBookName, String strDescrpition, String strPrice, String strQuantity, ImageView imageView,
                         String file, String licenseFile, String author, Dialog dialogs) {

        if (author.isEmpty() || strBookName.isEmpty() || strDescrpition.isEmpty() || strPrice.isEmpty() || strQuantity.isEmpty() || spindata.equals(" ")) {
            Toast.makeText(activity, "Please Fill All Information Properly", Toast.LENGTH_SHORT).show();
        } else {
            addDataIntoApi(strBookName, strDescrpition, strPrice, strQuantity, licenseFile, spindata, author, dialogs);

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

            imgFile = new File(licenseFile);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }

        }
    }


    private void addDataIntoApi(String strBookName, String strDescrpition, String strPrice,
                                String strQuantity, String licenseFile, String spindata,
                                String author, final Dialog dialogs) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.upload(activity, Config.Url.addbook, author, strBookName, strDescrpition, spindata, strQuantity, strPrice, localStorage.getString(LocalStorage.token), imgFile, new FutureCallback<AddBookResponseModel>() {
                @Override
                public void onCompleted(Exception e, AddBookResponseModel result) {
                    if (result.getStatus() == true) {
                        dialog.dismiss();
                        dialogs.dismiss();
                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        dialogs.dismiss();
                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }


    }

    private void searchViewSetUp() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<AdminBookDataModel> newlist = new ArrayList<>();
                for (AdminBookDataModel productList : item) {
                    String name = productList.getName();
                    if (name.contains(s))
                        newlist.add(productList);
                }
                adapter.setFilter(newlist);
                return true;
            }

        });


    }

    private void dialogOfFilter() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.admin_filter);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.yes);
        final Button No = (Button) dialog.findViewById(R.id.no);
        final ImageView image = (ImageView) dialog.findViewById(R.id.clear);
        spin = (Spinner) dialog.findViewById(R.id.category_spinner);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                getBookListFilter(spindata);
                dialog.dismiss();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void getBookListFilter(String spindata) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getAdminBookList(getActivity(), Config.Url.getAllBook + localStorage.getInt(LocalStorage.userId) + "/" + spindata,localStorage.getString(LocalStorage.token),
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

    private void dialogOpenForAddBook1(final int position) {
        dialogs = new Dialog(activity, R.style.dialogTheme);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialogs.getWindow().setLayout((6 * width), ViewGroup.LayoutParams.MATCH_PARENT);
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogs.setContentView(R.layout.add_book_card_design);
        final Button Yes = (Button) dialogs.findViewById(R.id.yes);
        final Button No = (Button) dialogs.findViewById(R.id.no);
        final TextView tvHeading = (TextView) dialogs.findViewById(R.id.heading);
        final EditText tvName = (EditText) dialogs.findViewById(R.id.tv_book_name);
        final EditText tvDesc = (EditText) dialogs.findViewById(R.id.tv_book_Descrption);
        final EditText price = (EditText) dialogs.findViewById(R.id.tv_book_price);
        final EditText quantity = (EditText) dialogs.findViewById(R.id.tv_book_quantity);
        final EditText author = (EditText) dialogs.findViewById(R.id.tv_book_author);
        spin = (Spinner) dialogs.findViewById(R.id.category_spinner);
        imageView = (ImageView) dialogs.findViewById(R.id.image);
        final ImageView clear = (ImageView) dialogs.findViewById(R.id.clear);
        readWritePermission();
        multiplePermission();
        tvHeading.setText("Edit Book Detial");
        strName = item.get(position).getName();
        strDesc = item.get(position).getDescription();
        strPrice = String.valueOf(item.get(position).getPrice());
        strQuantity = String.valueOf(item.get(position).getQuantity());
        strImage = item.get(position).getAvatarPath();
        strAuthor = item.get(position).getAuthor();
        strCategory = String.valueOf(item.get(position).getCategoryId());
        tvName.setText(strName);
        tvDesc.setText(strDesc);
        price.setText(strPrice);
        quantity.setText(strQuantity);
        author.setText(strAuthor);
        //
        Glide.with(activity).load(Config.imageUrl + item.get(position).getAvatarPath()).into(imageView);

        for (int i = 0; i < items.length; i++) {
            if (items[i].toLowerCase().equals(item.get(position).getCategory().getName().toLowerCase())) {
                spin.setSelection(i);
                break;
            }
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });
        ArrayAdapter aa = new ArrayAdapter(activity, layout.simple_list_item_1, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setSelection(item.get(position).getCategoryId());

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
                strName = tvName.getText().toString().trim();
                strDesc = tvDesc.getText().toString().trim();
                strPrice = price.getText().toString().trim();
                strQuantity = quantity.getText().toString().trim();
                strAuthor = author.getText().toString().trim();
                Toast.makeText(activity, "postionspiner " + spindata, Toast.LENGTH_SHORT).show();

                editBook(strName, strDesc, strPrice, strQuantity, imageView, licenseFile, spindata, item.get(position).getBookId(), dialogs,strAuthor);
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });
        dialogs.show();


    }

    private void editBook(String name, String descrption, String price, String quantity, ImageView imageView, String licenseFile, String spindata, Integer bookId, Dialog dialogs, String strAuthor) {
        if (name.isEmpty() || descrption.isEmpty() || price.isEmpty() || quantity.isEmpty() || spindata.equals(" ")) {
            Toast.makeText(activity, "please enter data" + bookId, Toast.LENGTH_SHORT).show();
        } else {
            editDataIntoApi(name, descrption, price, quantity, licenseFile, spindata, bookId, dialogs,strAuthor);

        }
    }

    private void editDataIntoApi(String name, String descrption, String price, String quantity, String licenseFile, String spindata, Integer bookId, final Dialog dialogs, String strAuthor) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.editBookDetial(activity, Config.Url.editBookDetial,
                    name, descrption, spindata, quantity, price, localStorage.getString(LocalStorage.token),
                    imgFile, bookId,strAuthor,
                    new FutureCallback<EditBookResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, EditBookResponseModel result) {
                            if (result.getStatus() == true) {
                                dialog.dismiss();
                                dialogs.dismiss();
                                Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                dialogs.dismiss();
                                Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    @Override
    public void onClickPosition(int position) {
        dialogOpenForAddBook1(position);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getBookList();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
