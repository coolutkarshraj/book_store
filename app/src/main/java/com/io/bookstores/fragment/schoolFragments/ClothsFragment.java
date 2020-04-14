package com.io.bookstores.fragment.schoolFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.adapter.schoolAdapter.ClothRvAdapter;
import com.io.bookstores.adapter.schoolAdapter.StudentBooksRvAdapter;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.staticModel.ClothModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.userOnlineInfo;

import java.util.ArrayList;
import java.util.List;


public class ClothsFragment extends Fragment implements View.OnClickListener {


    private Activity activity;
    private RecyclerView recyclerView;
    private LocalStorage localStorage;
    private NewProgressBar dialog;
    private ImageView iv_back;
    private userOnlineInfo user;
    private LinearLayout ll_books_layout, ll_cloth_layout;
    private RecyclerView rv_books, rv_cloths;
    private ClothRvAdapter adapter;
    private StudentBooksRvAdapter studentBooksRvAdapter;
    private List<ClothModel> listCloth;
    private List<ClothModel> listStudentBooks;
    private ItemClickListner itemClickListner;

    public ClothsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cloths, container, false);
        intializeViews(view);
        bindAllViews();
        startWorking();
        return view;
    }

    /*----------------------------------------- intialize all views that are used in this fragment ----------------------------*/

    private void intializeViews(View view) {
        activity = getActivity();
        listCloth = new ArrayList<>();
        listStudentBooks = new ArrayList<>();
        localStorage = new LocalStorage(activity);
        user = new userOnlineInfo();
        dialog = new NewProgressBar(activity);
        itemClickListner = (ItemClickListner) activity;
        iv_back = view.findViewById(R.id.iv_back);
        ll_books_layout = view.findViewById(R.id.ll_books_layout);
        ll_cloth_layout = view.findViewById(R.id.ll_cloth_layout);
        rv_books = view.findViewById(R.id.rv_books);
        rv_cloths = view.findViewById(R.id.rv_cloths);
    }

    /*-------------------------------------------- bind all view that are used in this fragment ------------------------------*/

    private void bindAllViews() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                itemClickListner.onClick(6);
        }
    }

    /*-------------------------------------------------- start Working ------------------------------------------------------*/

    private void startWorking() {
        setUpofUi();


    }

    /*---------------------------------------------- set of ui cloths or book ui -----------------------------------------*/

    private void setUpofUi() {
        if (StaticData.type == 1) {
            ll_cloth_layout.setVisibility(View.VISIBLE);
            ll_books_layout.setVisibility(View.GONE);
            setUpOfCloths();
        } else {
            ll_cloth_layout.setVisibility(View.GONE);
            ll_books_layout.setVisibility(View.VISIBLE);
            setUpOfStudentBooks();
        }
    }

    /*----------------------------------------------- set up of cloth recycler view --------------------------------------*/

    private void setUpOfCloths() {
        listStudentBooks.clear();
        listCloth.clear();
        listCloth.add(new ClothModel(R.drawable.c1, "2 KD"));
        listCloth.add(new ClothModel(R.drawable.c2, "2 KD"));
        listCloth.add(new ClothModel(R.drawable.c3, "2 KD"));
        listCloth.add(new ClothModel(R.drawable.c3, "2 KD"));
        listCloth.add(new ClothModel(R.drawable.c1, "2 KD"));
        listCloth.add(new ClothModel(R.drawable.c2, "2 KD"));
        rv_cloths.setLayoutManager(new GridLayoutManager(activity, 3));
        adapter = new ClothRvAdapter(activity, listCloth);
        rv_cloths.setAdapter(adapter);
    }

    /*------------------------------------------ set Up of student books recycler view -----------------------------------*/

    private void setUpOfStudentBooks() {
        listCloth.clear();
        listStudentBooks.clear();
        listStudentBooks.add(new ClothModel(R.drawable.b1, "2KD"));
        listStudentBooks.add(new ClothModel(R.drawable.b2, "2KD"));
        listStudentBooks.add(new ClothModel(R.drawable.b2, "2KD"));
        listStudentBooks.add(new ClothModel(R.drawable.b1, "2KD"));
        listStudentBooks.add(new ClothModel(R.drawable.b2, "2KD"));
        listStudentBooks.add(new ClothModel(R.drawable.b1, "2KD"));
        rv_books.setLayoutManager(new GridLayoutManager(activity, 3));
        studentBooksRvAdapter = new StudentBooksRvAdapter(activity, listStudentBooks);
        rv_books.setAdapter(studentBooksRvAdapter);
    }


}
