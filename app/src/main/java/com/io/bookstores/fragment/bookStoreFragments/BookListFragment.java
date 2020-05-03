package com.io.bookstores.fragment.bookStoreFragments;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.adapter.bookStoreAdapter.BookListAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.BookListModel;
import com.io.bookstores.model.bookListModel.Datum;
import com.io.bookstores.model.bookListModel.WishListLocalResponseModel;
import com.io.bookstores.model.categoryModel.CategoryModel;
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
    private ImageView iv_back, iv_filter;
    private Spinner spin;
    private String token = "";
    private String spindata = "-1";
    private List<String> items = new ArrayList<>();
    private List<String> categoryId = new ArrayList<>();
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
        iv_filter = root.findViewById(R.id.iv_filter);
        notdata = root.findViewById(R.id.notdata);
        if (StaticData.filter) {
            iv_filter.setVisibility(View.VISIBLE);
        } else {
            iv_filter.setVisibility(View.GONE);
        }
    }

    /*--------------------------------------- bind all views that are used in this fragment ----------------------------------*/
    private void bindViews() {
        iv_back.setOnClickListener(this);
        iv_filter.setOnClickListener(this);
    }

    /*---------------------------------------------------------- click listner ---------------------------------------------- */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                itemClickListner.onClick(6);
                return;
            case R.id.iv_filter:
                dialogOfFilter();
                return;
        }
    }

    /*--------------------------------------------------- start Working -----------------------------------------------------*/

    private void startWorking() {
        getCategoryList();
        getAllBooksList();
        searchViewSetUp();
    }

    /*-------------------------------------------------- get all book list ------------------------------------------------*/

    private void getAllBooksList() {
        if (localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")) {
            token = "";
            getBookList("",
                    localStorage.getString(LocalStorage.StoreId),
                    localStorage.getString(LocalStorage.CategoryId), "");
        } else {
            token = localStorage.getString(LocalStorage.token);
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
       // searchView2.setQueryHint("Search Books...");
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


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
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
        ArrayAdapter aa = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, items);
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
                dialog.dismiss();
                getBookList("",
                        localStorage.getString(LocalStorage.StoreId),
                        spindata, token);

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

}
