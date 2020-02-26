package com.io.bookstore.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.BookstoresAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.model.storeModel.Datum;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
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
    private EditText searchView2;
    private ImageView iv_back;
    private BookstoresAdapter bookstoresAdapter;
    View root;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private StoreModel data;
    private ArrayList<Datum> datachild;
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
        searchView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
              if(searchView2.getText().toString().trim().length()>0){
                  datachild = new ArrayList<>();
                  for (int i =0;i< data.getData().size();i++){

                      if(data.getData().get(i).name.toLowerCase().indexOf(searchView2.getText().toString().toLowerCase()) != -1){
                          datachild.add(data.getData().get(i));
                      }
                  }
                  setRecyclerViewData(datachild);

              }else  {

                  getStoreList();
              }
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
        dialog.dismiss();
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookstoresAdapter = new BookstoresAdapter(getActivity(),result);
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