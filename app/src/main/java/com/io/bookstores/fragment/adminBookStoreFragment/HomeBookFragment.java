package com.io.bookstores.fragment.adminBookStoreFragment;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.adminAdapter.AdminBookListAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.addAddressResponseModel.EditBookResponseModel;
import com.io.bookstores.model.adminResponseModel.AddBookResponseModel;
import com.io.bookstores.model.adminResponseModel.AdminBookDataModel;
import com.io.bookstores.model.adminResponseModel.AdminBookListResponseModel;
import com.io.bookstores.model.categoryModel.CategoryModel;
import com.io.bookstores.model.contactUs.UpdateDeviceToken;
import com.io.bookstores.utility.ImageUtility;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.PermissionFile;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeBookFragment extends Fragment implements View.OnClickListener, RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {


    private Activity activity;
    private RecyclerView rvBookStore;
    private AdminBookListAdapter adapter;
    private List<AdminBookDataModel> item;
    private NewProgressBar dialog;
    private SearchView searchView;
    private userOnlineInfo user;
    private Dialog dialogs;
    private static final int REQUEST_WRITE_STORAGE = 1004;
    private static int GalleryPicker = 123;
    private PermissionFile permissionFile;
    private String licenseFile = "";
    private ImageUtility imageUtility;
    private File destination;
    private Uri outputFileUri;
    private int CameraPicker = 124;
    private ImageView imageView, ivFilter;
    private Spinner spin;
    private String strName, strDesc, strImage, strPrice, strQuantity, strCategory, strAuthor;
    private String spindata = "-1", deviceToken;
    private LocalStorage localStorage;
    private List<String> items = new ArrayList<>();
    private List<String> categoryId = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private File imagefile;
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

    /*-------------------------------------- intialize all views  that are used in this fragment -----------------------------*/

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
        FirebaseApp.initializeApp(activity);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        deviceToken = instanceIdResult.getToken();
                    }
                });
    }

    /*--------------------------------------- bind all views that are used in this fragment --------------------------------*/

    private void bindListner() {
        floatingActionButton.setOnClickListener(this);
        ivFilter.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    /*------------------------------------------------- click Listner ------------------------------------------------------*/

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

    /*----------------------------------------------------- start Working ------------------------------------------------*/

    private void startWorking() {
        updateDevceToken();
        getBookList();
        getCategoryList();
        searchViewSetUp();
    }

    /*------------------------------------------------ update Device Token ap call  ------------------------------------*/

    private void updateDevceToken() {
        if (user.isOnline(getActivity())) {
            ApiCaller.updateDevice(getActivity(), Config.Url.updateDeviceToken, localStorage.getString(LocalStorage.token), deviceToken,
                    new FutureCallback<UpdateDeviceToken>() {

                        @Override
                        public void onCompleted(Exception e, UpdateDeviceToken result) {
                            if (result != null) {

                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getInt(LocalStorage.userId));
                                    }

                                } else {

                                    // Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*----------------------------------------------- get all Book List Api Call --------------------------------------------*/
    private void getBookList() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getAdminBookList(getActivity(), Config.Url.getAllBook + localStorage.getInt(LocalStorage.userId) + "/" + spindata, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AdminBookListResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, AdminBookListResponseModel result) {
                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getInt(LocalStorage.userId));
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    setRecyclerViewData(result);
                                    dialog.dismiss();

                                }
                            } else {
                                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*----------------------------------------- all books set into the recycler View ------------------------------------------*/

    @SuppressLint("WrongConstant")
    private void setRecyclerViewData(AdminBookListResponseModel result) {
        rvBookStore.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        if (result.getData().size() == 0) {
            Toast.makeText(activity, "Data Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new AdminBookListAdapter(activity, result.getData(), this);
            item = result.getData();
            rvBookStore.setAdapter(adapter);
        }
    }

    /*-----------------------------------------------get all category list api call ---------------------------------------- */

    private void getCategoryList() {
        if (user.isOnline(getActivity())) {
            ApiCaller.getCategoryModel(getActivity(), Config.Url.getCategoryModel, "",
                    new FutureCallback<CategoryModel>() {

                        @Override
                        public void onCompleted(Exception e, CategoryModel result) {
                            if (e != null) {

                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if(result!=null) {
                                if (result.getStatus() == true) {

                                    items.clear();
                                    categoryId.clear();
                                    for (int i = 0; i < result.getData().size(); i++) {
                                        String name = result.getData().get(i).getName();
                                        items.add(name);
                                        categoryId.add(String.valueOf(result.getData().get(i).getCategoryId()));
                                    }

                                } else {

                                    Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }

                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*----------------------------------------------- create and  open dialog of add books ----------------------------------*/

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
                spindata = categoryId.get(i);
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

    /*-------------------------------------------------- check data is empty or not --------------------------------------------*/

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

    /*------------------------------------------------- check multiple permissions -------------------------------------------*/

    private void multiplePermission() {
        if (!permissionFile.checkLocStorgePermission(activity)) {
            permissionFile.checkLocStorgePermission(activity);
        }
    }

    /*------------------------------------- pick Image from the gallery using intent ---------------------------------------*/

    private void galleryIntent() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, GalleryPicker);
    }

    /*------------------------------------------- on activity result get Image Data ---------------------------------------*/

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

    /* --------------------------------- get the actual storage path of image (Camera an dgallery) -----------------------------*/

    public void onCaptureImageResult(Intent data, String imageType) {
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

    /*----------------------------------------------------- add book api call -----------------------------------------------*/

    private void addDataIntoApi(String strBookName, String strDescrpition, String strPrice,
                                String strQuantity, String licenseFile, String spindata,
                                String author, final Dialog dialogs) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            if (imagefile == null) {
                dialog.dismiss();
                Toast.makeText(activity, "please select Image", Toast.LENGTH_SHORT).show();
            } else {
                ApiCaller.upload(activity, Config.Url.addbook, author, strBookName, strDescrpition, spindata, strQuantity, strPrice, localStorage.getString(LocalStorage.token), imgFile, new FutureCallback<AddBookResponseModel>() {
                    @Override
                    public void onCompleted(Exception e, AddBookResponseModel result) {
                        if (e != null) {
                            dialog.dismiss();
                            Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            return;
                        }

                        if (result != null) {
                            if (result.getStatus() == null) {
                                if (result.getMessage().equals("Unauthorized")) {
                                    Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getInt(LocalStorage.userId));
                                    dialog.dismiss();
                                }
                            } else {
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
                        }else {
                            dialogs.dismiss();
                            dialog.dismiss();
                            Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                        }
                    }
                });
            }

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*---------------------------------------------------- search view setup -------------------------------------------------*/

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
                    String name = productList.getName().toLowerCase();
                    if (name.contains(s))
                        newlist.add(productList);
                }
                adapter.setFilter(newlist);
                return true;
            }

        });
    }

    /*--------------------------------------------------- create dialog for filter ---------------------------------------------*/

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
                spindata = categoryId.get(i);
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

    /*------------------------------------------------ get book List According to filter -------------------------------------*/

    private void getBookListFilter(String spindata) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getAdminBookList(getActivity(), Config.Url.getAllBook + localStorage.getInt(LocalStorage.userId) + "/" + spindata, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AdminBookListResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, AdminBookListResponseModel result) {

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getInt(LocalStorage.userId));
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    setRecyclerViewData(result);
                                    dialog.dismiss();

                                }
                            }else {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*-------------------------------------------- create dialog for edit book detail -----------------------------------------*/

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
        tvHeading.setText(getResources().getString(R.string.editbook));
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
                spindata = categoryId.get(i);
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

                editBook(strName, strDesc, strPrice, strQuantity, imageView, licenseFile, spindata, item.get(position).getBookId(), dialogs, strAuthor);
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

    /*--------------------------------------------------- edit book detail validtae  -----------------------------------------*/

    private void editBook(String name, String descrption, String price, String quantity, ImageView imageView, String licenseFile, String spindata, Integer bookId, Dialog dialogs, String strAuthor) {
        if (name.isEmpty() || descrption.isEmpty() || price.isEmpty() || quantity.isEmpty() || spindata.equals(" ")) {
            Toast.makeText(activity, "please enter data" + bookId, Toast.LENGTH_SHORT).show();
        } else {
            editDataIntoApi(name, descrption, price, quantity, licenseFile, spindata, bookId, dialogs, strAuthor);

        }
    }

    /*--------------------------------------------------- edit book detial Api Caller ----------------------------------------*/

    private void editDataIntoApi(String name, String descrption, String price, String quantity, String licenseFile, String spindata, Integer bookId, final Dialog dialogs, String strAuthor) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.editBookDetial(activity, Config.Url.editBookDetial,
                    name, descrption, spindata, quantity, price, localStorage.getString(LocalStorage.token),
                    imgFile, bookId, strAuthor,
                    new FutureCallback<EditBookResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, EditBookResponseModel result) {

                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogAdminLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getInt(LocalStorage.userId));
                                        dialog.dismiss();
                                    }

                                } else {
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
                            }else {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                dialogs.dismiss();
                                dialog.dismiss();
                            }


                        }
                    });

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*------------------------------------------------ edit Button Clickable Recycler view -----------------------------------*/

    @Override
    public void onClickPosition(int position) {
        dialogOpenForAddBook1(position);
    }

    /*----------------------------------------------------- on Swipe refresh ------------------------------------------------*/
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
