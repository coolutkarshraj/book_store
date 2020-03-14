package com.io.bookstores.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.BookstoresAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.model.storeModel.Datum;
import com.io.bookstores.model.storeModel.StoreModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookstoresFragment extends Fragment implements View.OnClickListener {

    private ArrayList lstBook;
    private RecyclerView recyclerView;
    private SearchView searchView2;
    private ImageView iv_back;
    private BookstoresAdapter bookstoresAdapter;
    View root;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private StoreModel data;
    private ArrayList<Datum> datachild = new ArrayList<>();
    private ItemClickListner itemClickListner;


    public BookstoresFragment() {
        // Required empty public constructor
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bookstores, container, false);
        initView();
        getStoreList();
        startWorking();
        return root;
    }

    private void startWorking() {

        searchView2.setQueryHint("Search Books...");
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (datachild == null || datachild.isEmpty()) {

                } else {
                    List<Datum> newlist = new ArrayList<>();
                    for (Datum productList : datachild) {
                        String name = productList.getName().toLowerCase();
                        if (name.contains(s))
                            newlist.add(productList);
                    }
                    bookstoresAdapter.setFilter(newlist);

                }
                return true;
            }

        });
    }

    private void initView() {
        user = new userOnlineInfo();
        dialog = new NewProgressBar(getActivity());
        itemClickListner = (ItemClickListner) getActivity();
        iv_back = root.findViewById(R.id.iv_back);
        recyclerView = root.findViewById(R.id.recyclerView_bookstore);
        searchView2 = root.findViewById(R.id.searchView2);
        iv_back.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getStoreList() {
        if (user.isOnline(Objects.requireNonNull(getActivity()))) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getStoreApi(getActivity(), Config.Url.getStoreApi,
                    new FutureCallback<StoreModel>() {

                        @Override
                        public void onCompleted(Exception e, StoreModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                dialog.dismiss();
                                data = result;
                                setRecyclerViewData(result.getData());
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }




    private void setRecyclerViewData(List<Datum> result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookstoresAdapter = new BookstoresAdapter(getActivity(),result);
        datachild = (ArrayList<Datum>) result;
        recyclerView.setAdapter(bookstoresAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                itemClickListner.onClick(6);
                return;

        }
    }
}