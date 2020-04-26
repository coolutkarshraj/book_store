package com.io.bookstores.fragment.bookStoreFragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.bookStoreAdapter.BookListAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.BookListModel;
import com.io.bookstores.model.bookListModel.Datum;
import com.io.bookstores.model.bookListModel.WishListLocalResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;


public class BookListFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private RecyclerView recyclerView;
    private View root;
    private TextView notdata;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private SearchView searchView2;
    private List<Datum> data;
    private List<Datum> childdata = new ArrayList<>();
    private LocalStorage localStorage;
    private BookListAdapter categoryAdapter;
    private ItemClickListner itemClickListner;
    private ImageView iv_back;
    private List<WishListLocalResponseModel> lists = new ArrayList<>();

    public BookListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_category_list, container, false);
        initView();
        bindViews();
        startWorking();
        return root;
    }

    /*------------------------------------------- intalize all views in this fragment -----------------------------------------*/

    private void initView() {
        activity = getActivity();
        user = new userOnlineInfo();
        localStorage = new LocalStorage(getActivity());
        recyclerView = root.findViewById(R.id.rv_cateory_list);
        searchView2 = root.findViewById(R.id.searchView2);
        itemClickListner = (ItemClickListner) getActivity();
        iv_back = root.findViewById(R.id.iv_back);
        notdata = root.findViewById(R.id.notdata);
    }

    /*--------------------------------------- bind all views that are used in this fragment ----------------------------------*/
    private void bindViews() {
        iv_back.setOnClickListener(this);
    }

    /*---------------------------------------------------------- click listner ---------------------------------------------- */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                itemClickListner.onClick(6);
        }
    }

    /*--------------------------------------------------- start Working -----------------------------------------------------*/

    private void startWorking() {
        getAllBooksList();
        searchViewSetUp();
    }

    /*-------------------------------------------------- get all book list ------------------------------------------------*/

    private void getAllBooksList() {
        if (localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")) {
            getBookList("",
                    localStorage.getString(LocalStorage.StoreId),
                    localStorage.getString(LocalStorage.CategoryId), "");
        } else {
            getBookList("",
                    localStorage.getString(LocalStorage.StoreId),
                    localStorage.getString(LocalStorage.CategoryId), localStorage.getString(LocalStorage.token));
        }
    }

    /*------------------------------------------ get all book list api call ------------------------------------------------*/

    private void getBookList(String name, String sId, String Cid, String token) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getBookModel(getActivity(), Config.Url.getAllBook + sId + "/" + Cid + "/", sId, Cid, name, token,
                    new FutureCallback<BookListModel>() {

                        @Override
                        public void onCompleted(Exception e, BookListModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    if (result.getStatus() == true) {
                                        dialog.dismiss();
                                        data = result.getData();
                                        setRecyclerViewData(result.getData());
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*----------------------------- set up of book list recycler view and api data set into recyclerview ------------------------*/

    private void setRecyclerViewData(List<Datum> result) {
        if (result.isEmpty()) {
            notdata.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {
            searchView2.setVisibility(View.VISIBLE);
            notdata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            categoryAdapter = new BookListAdapter(getActivity(), result);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            childdata = (ArrayList<Datum>) result;
            recyclerView.setAdapter(categoryAdapter);
        }
    }

    /*---------------------------------------------------- set up of search view --------------------------------------------------*/

    private void searchViewSetUp() {
        searchView2.setQueryHint("Search Books...");
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (childdata == null || childdata.isEmpty()) {

                } else {
                    List<Datum> newlist = new ArrayList<>();
                    for (Datum productList : childdata) {
                        String name = productList.getName().toLowerCase();
                        if (name.contains(s))
                            newlist.add(productList);
                    }
                    categoryAdapter.setFilter(newlist);

                }
                return true;
            }

        });

    }
}
