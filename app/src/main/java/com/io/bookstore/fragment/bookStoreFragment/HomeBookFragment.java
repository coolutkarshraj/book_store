package com.io.bookstore.fragment.bookStoreFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.AdminBookListAdapter;
import com.io.bookstore.adapter.BookListAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.model.adminResponseModel.AdminBookListResponseModel;
import com.io.bookstore.model.bookListModel.BookListModel;
import com.io.bookstore.model.bookListModel.Datum;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeBookFragment extends Fragment implements View.OnClickListener {


    Activity activity;
    RecyclerView rvBookStore;
    AdminBookListAdapter adapter;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private Dialog dialogs;
    private FloatingActionButton floatingActionButton;

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
        final Spinner spin = (Spinner) dialogs.findViewById(R.id.category_spinner);
        final ImageView imageView = (ImageView) dialogs.findViewById(R.id.image);


        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addBook(tvName, tvDesc, price, quantity, spin, imageView);
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

    private void addBook(EditText tvName, EditText tvDesc, EditText price,
                         EditText quantity, Spinner spin, ImageView imageView) {
        String strBookName = tvName.getText().toString().trim();
        String strDescrpition = tvDesc.getText().toString().trim();
        String strPrice = price.getText().toString().trim();
        String strQuantity = quantity.getText().toString().trim();

        if (strBookName.isEmpty() || strDescrpition.isEmpty() || strPrice.isEmpty() || strQuantity.isEmpty()) {
            tvName.setError("Please Enter Name");
            tvDesc.setError("Please Enter Descrption");
            price.setError("Please Enter price");
            quantity.setError("Please Enter quantity");
        } else {
            addDataIntoApi(strBookName, strDescrpition, strPrice, strQuantity);

        }
    }

    private void addDataIntoApi(String strBookName, String strDescrpition, String strPrice, String strQuantity) {
    }


}
