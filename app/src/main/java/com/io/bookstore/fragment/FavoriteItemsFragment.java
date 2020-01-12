package com.io.bookstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.adapter.FavoriteItemsAdapter;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.BookModel;
import com.io.bookstore.model.loginModel.LoginModel;

import java.util.ArrayList;

public class FavoriteItemsFragment extends Fragment {
    private ArrayList lstBook;
    private RecyclerView recyclerView;
    private FavoriteItemsAdapter favoriteItemsAdapter;
    private LocalStorage localStorage;
    private TextView loggedih;
    private NestedScrollView nested_c_view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite_items, container, false);
        recyclerView = root.findViewById(R.id.fav_recyclerView);
        loggedih = root.findViewById(R.id.loggedih);
        nested_c_view = root.findViewById(R.id.nested_c_view);
        localStorage = new LocalStorage(getActivity());
      LoginModel loginModel =  localStorage.getUserProfile() ;
      System.out.println(loginModel);
      if(loginModel == null){
          nested_c_view.setVisibility(View.GONE);
          loggedih.setVisibility(View.VISIBLE);
      }else {
          nested_c_view.setVisibility(View.VISIBLE);
          loggedih.setVisibility(View.GONE);
          setRecyclerViewData();
      }
        bindListner();
        return root;
    }

    private void setRecyclerViewData() {

        lstBook = new ArrayList<>();
        lstBook.add(new BookModel(25,R.drawable.thevigitarian));
        lstBook.add(new BookModel(35,R.drawable.thewildrobot));
        lstBook.add(new BookModel(40,R.drawable.mariasemples));
        lstBook.add(new BookModel(20,R.drawable.themartian));
        lstBook.add(new BookModel(15,R.drawable.hediedwith));
        lstBook.add(new BookModel(25,R.drawable.thevigitarian));
        lstBook.add(new BookModel(35,R.drawable.thewildrobot));
        lstBook.add(new BookModel(40,R.drawable.mariasemples));
        lstBook.add(new BookModel(20,R.drawable.themartian));
        lstBook.add(new BookModel(15,R.drawable.hediedwith));

        lstBook.add(new BookModel(25,R.drawable.thevigitarian));
        lstBook.add(new BookModel(35,R.drawable.thewildrobot));
        lstBook.add(new BookModel(40,R.drawable.mariasemples));
        lstBook.add(new BookModel(20,R.drawable.themartian));
        lstBook.add(new BookModel(15,R.drawable.hediedwith));

        FavoriteItemsAdapter myAdapter = new FavoriteItemsAdapter(getActivity(),lstBook);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(myAdapter);

    }

    private void bindListner() {
        loggedih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }



}