package com.io.bookstore.fragment.category;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.categoryAdapter.CategoryFragmentAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.fragment.CategoryListFragment;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.model.categoryModel.CategoryData;
import com.io.bookstore.model.categoryModel.CategoryModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.List;


public class CategoryGridFragment extends Fragment implements View.OnClickListener {


    public static ViewPager viewPager;
    private CategoryFragmentAdapter adapter;
    private Activity activity;
    private RelativeLayout rlViewAllCategory;
    private CategoryListFragment categoryListFragment;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    SpringDotsIndicator dotsIndicator;

    int sizee = 0;
    private ItemClickListner itemClickListner;
    private ImageView iv_image;
    public CategoryGridFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_grid, container, false);
        intializeViews(view);
        bindListner();
        getCategoryList();
        return view;
    }


    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        itemClickListner = (ItemClickListner)getActivity();
        iv_image =(ImageView)view.findViewById(R.id.iv_back);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        dotsIndicator = (SpringDotsIndicator) view.findViewById(R.id.dots_indicator);
        rlViewAllCategory = (RelativeLayout) view.findViewById(R.id.iv_view_all_category);

    }

    private void bindListner() {
        rlViewAllCategory.setOnClickListener(this);
        iv_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.iv_view_all_category:
                categoryListFragment = new CategoryListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, categoryListFragment)
                        .addToBackStack(null)
                        .commit();
                return;

            case R.id.iv_back:
                itemClickListner.onClick(6);
                return;
        }
    }

    private void getCategoryList() {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            ApiCaller.getCategoryModel(getActivity(), Config.Url.getCategoryModel, "",
                    new FutureCallback<CategoryModel>() {

                        @Override
                        public void onCompleted(Exception e, CategoryModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                dialog.dismiss();
                                recyclerViewData(result.getData());
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

    private void recyclerViewData(List<CategoryData> data) {
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(((FragmentActivity) activity).getSupportFragmentManager());
        if (data.size() % 10 == 0) {
            sizee = data.size() / 10;
        } else {
            sizee = data.size() / 10 + 1;
        }
        for (int i = 0; i < sizee; i++) {
            adapter.addFragment(new CategoryFirstFragment(data));
            adapter.notifyDataSetChanged();

        }
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        dotsIndicator.setViewPager(viewPager);
    }




}
