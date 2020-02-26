package com.io.bookstore.fragment.category;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.adapter.categoryAdapter.CategoryGridAdapter;
import com.io.bookstore.model.categoryModel.CategoryData;

import java.util.ArrayList;
import java.util.List;

public class CategoryFirstFragment extends Fragment {

    private Activity activity;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    int sizeo = 0;
    int i = 0;
    List<CategoryData> data = new ArrayList<>();
    List<CategoryData> item1 = new ArrayList<>();
    List<CategoryData> item2 = new ArrayList<>();
    List<CategoryData> item3 = new ArrayList<>();
    List<CategoryData> item4 = new ArrayList<>();

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


    private void intializeViews(View view) {
        activity = getActivity();
        recyclerView1 = view.findViewById(R.id.recycler1);
        recyclerView2 = view.findViewById(R.id.recycler2);
        recyclerView3 = view.findViewById(R.id.recycler3);
        recyclerView4 = view.findViewById(R.id.recycler4);
    }

    private void startWorking() {
        dataSetIntoRecyclerView();
    }

    private void dataSetIntoRecyclerView() {
        item1.clear();
        item2.clear();
        item3.clear();
        item4.clear();
        if (CategoryGridFragment.viewPager.getCurrentItem() == 0) {
            sizeo = 0;
            i = 0;
        } else if (CategoryGridFragment.viewPager.getCurrentItem() > 0) {
            sizeo = 10 * CategoryGridFragment.viewPager.getCurrentItem();
            i = 10 * CategoryGridFragment.viewPager.getCurrentItem();
        }

        for (i = sizeo; i <= data.size() - 1; i++) {
            if (i < sizeo + 3) {
                item1.add(data.get(i));
            } else if (i < sizeo + 3 + 3) {
                item2.add(data.get(i));
            } else if (i < sizeo + 3 + 3 + 4) {
                item3.add(data.get(i));
            }/* else if (i < sizeo + 2 + 3 + 2 + 4) {
                item4.add(data.get(i));
            }*/
        }
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryGridAdapter adapter = new CategoryGridAdapter(getActivity(), item1);
        recyclerView1.setAdapter(adapter);

        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryGridAdapter adapter2 = new CategoryGridAdapter(getActivity(), item2);
        recyclerView2.setAdapter(adapter2);


        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryGridAdapter adapter3 = new CategoryGridAdapter(getActivity(), item3);
        recyclerView3.setAdapter(adapter3);


      /*  recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryGridAdapter adapter4 = new CategoryGridAdapter(getActivity(), item4);
        recyclerView4.setAdapter(adapter4);*/

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
