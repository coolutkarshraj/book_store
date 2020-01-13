package com.io.bookstore.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.adapter.BookListAdapter;
import com.io.bookstore.adapter.CategoryAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.BookModel;
import com.io.bookstore.model.bookListModel.BookListModel;
import com.io.bookstore.model.bookListModel.Datum;
import com.io.bookstore.model.categoryModel.CategoryModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {


    private RecyclerView recyclerView;
    private View root;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private EditText searchView2;
    private List<Datum> data;
    private ArrayList<Datum> childdata;
    private LocalStorage localStorage;

    public BookListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_category_list, container, false);
        initView();
        getBookList("",
                localStorage.getString(LocalStorage.StoreId),
                localStorage.getString(LocalStorage.CategoryId));
        startWorking();
        return root;
    }

    private void startWorking() {
        searchView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchView2.getText().toString().trim().length() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getName().toLowerCase().indexOf(searchView2.getText().toString().toLowerCase()) != -1) {
                            childdata.add(data.get(i));
                        }
                    }
                    setRecyclerViewData(childdata);
                } else {
                    getBookList(searchView2.getText().toString(),
                            localStorage.getString(LocalStorage.StoreId),
                            localStorage.getString(LocalStorage.CategoryId));
                }

            }
        });
    }

    private void initView() {
        localStorage = new LocalStorage(getActivity());
        childdata = new ArrayList<>();
        recyclerView = root.findViewById(R.id.rv_cateory_list);
        searchView2 = root.findViewById(R.id.searchView2);
        user = new userOnlineInfo();
    }



    private void getBookList(String name, String sId, String Cid) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getBookModel(getActivity(), Config.Url.getAllBook, sId, Cid, name,
                    new FutureCallback<BookListModel>() {

                        @Override
                        public void onCompleted(Exception e, BookListModel result) {
                            dialog.dismiss();
                            data = result.getData()
                            ;
                            setRecyclerViewData(result.getData());

                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }


    private void setRecyclerViewData(List<Datum> result) {
        BookListAdapter categoryAdapter = new BookListAdapter(getActivity(), result);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(categoryAdapter);

    }

}
