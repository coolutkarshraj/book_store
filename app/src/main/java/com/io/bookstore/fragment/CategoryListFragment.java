package com.io.bookstore.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.CategoryAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.model.categoryModel.CategoryData;
import com.io.bookstore.model.categoryModel.CategoryModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {


    private ArrayList lstBook;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ItemClickListner itemClickListner;
    View root;
    private NewProgressBar dialog;
    private userOnlineInfo user;



    public CategoryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_category_list1, container, false);

        initView();
        getCategoryList();
        bindListner();



        return root;
    }

    private void initView() {
        recyclerView = root.findViewById(R.id.rv_cateory_list);
        user = new userOnlineInfo();
    }





    private void bindListner() {
    }



    private void getCategoryList() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getCategoryModel(getActivity(), Config.Url.getCategoryModel, "",
                    new FutureCallback<CategoryModel>() {

                        @Override
                        public void onCompleted(Exception e, CategoryModel result) {
                                dialog.dismiss();
                                setRecyclerViewData(result);

                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }







        private void setRecyclerViewData(CategoryModel result) {
        categoryAdapter = new CategoryAdapter(getActivity(),result.getData());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(categoryAdapter);

    }

}
