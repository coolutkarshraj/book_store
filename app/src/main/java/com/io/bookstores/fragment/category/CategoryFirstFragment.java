package com.io.bookstores.fragment.category;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.adapter.categoryAdapter.CategoryGridAdapter;
import com.io.bookstores.model.categoryModel.CategoryData;

import java.util.ArrayList;
import java.util.List;

public class CategoryFirstFragment extends Fragment {

    private Activity activity;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private int sizeo = 0;
    private int i = 0;
    private List<CategoryData> data = new ArrayList<>();
    private List<CategoryData> item1 = new ArrayList<>();
    private List<CategoryData> item3 = new ArrayList<>();

    public CategoryFirstFragment(List<CategoryData> data) {
        this.data = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_first, container, false);
        intializeViews(view);
        startWorking();
        return view;
    }

    /*-------------------------------------- intialize all views that are used in this fragment --------------------------------*/

    private void intializeViews(View view) {
        activity = getActivity();
        recyclerView3 = view.findViewById(R.id.recycler3);
    }

    /*-------------------------------------------------- start Working --------------------------------------------------------*/

    private void startWorking() {
        dataSetIntoRecyclerView();
    }

    /*--------------------------------------------- data set into Recycler View ---------------------------------------------*/

    private void dataSetIntoRecyclerView() {
        item3.clear();
        if (CategoryGridFragment.viewPager.getCurrentItem() == 0) {
            sizeo = 5;
            i = 5;
        } else if (CategoryGridFragment.viewPager.getCurrentItem() > 0) {
            sizeo = 10 * CategoryGridFragment.viewPager.getCurrentItem();
            i = 10 * CategoryGridFragment.viewPager.getCurrentItem();
        }

        for (i = sizeo; i <= data.size() - 1; i++) {
            if (i < sizeo + 10) {
                item3.add(data.get(i));
            }
        }

        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryGridAdapter adapter3 = new CategoryGridAdapter(getActivity(), item3);
        recyclerView3.setAdapter(adapter3);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
