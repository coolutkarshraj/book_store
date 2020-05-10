package com.io.bookstores.fragment.bookStoreFragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.categoryAdapter.CategoryFragmentAdapter;
import com.io.bookstores.adapter.categoryAdapter.CategoryGridAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.categoryModel.CategoryData;
import com.io.bookstores.model.categoryModel.CategoryModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.List;


public class CategoryGridFragment extends Fragment implements View.OnClickListener {


    public static ViewPager viewPager;
    private CategoryFragmentAdapter adapter;
    private Activity activity;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private RelativeLayout rlViewAllCategory;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private SpringDotsIndicator dotsIndicator;
    List<CategoryData> data = new ArrayList<>();
    List<CategoryData> item1 = new ArrayList<>();
    List<CategoryData> item2 = new ArrayList<>();
    List<CategoryData> item3 = new ArrayList<>();
    private LocalStorage localStorage;

    private int sizee = 0;
    private BookListFragment bookListFragment;
    private ItemClickListner itemClickListner;
    private ImageView iv_image;

    public CategoryGridFragment() {

    }

    TextView booksHeading, officeSuppliesHeading, electronicsHeading, othersHeading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_grid, container, false);
        intializeViews(view);
        bindListner();
        getCategoryList();
        return view;
    }

    /*------------------------------------- intailize all Views that are used in this fragment ------------------------------*/

    private void intializeViews(View view) {
        booksHeading = view.findViewById(R.id.booksHeading);
        //booksHeading.setVisibility(View.GONE);
        electronicsHeading = view.findViewById(R.id.electronicsHeading);
        //electronicsHeading.setVisibility(View.GONE);
        officeSuppliesHeading = view.findViewById(R.id.officeSuppliesHeading);
        //officeSuppliesHeading.setVisibility(View.GONE);
        othersHeading = view.findViewById(R.id.othersHeading);

        activity = getActivity();
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
        recyclerView1 = view.findViewById(R.id.recycler1);
        recyclerView2 = view.findViewById(R.id.recycler2);
        recyclerView3 = view.findViewById(R.id.recycler3);
        itemClickListner = (ItemClickListner) getActivity();
        iv_image = (ImageView) view.findViewById(R.id.iv_back);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        dotsIndicator = (SpringDotsIndicator) view.findViewById(R.id.dots_indicator);
        rlViewAllCategory = (RelativeLayout) view.findViewById(R.id.iv_view_all_category);
    }

    /*---------------------------------------- bind all views that are used in this fragment --------------------------------*/

    private void bindListner() {
        rlViewAllCategory.setOnClickListener(this);
        iv_image.setOnClickListener(this);
    }

    /*------------------------------------------------ on click Listner ----------------------------------------------------*/

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.iv_view_all_category:
                localStorage.putString(LocalStorage.CategoryId, "-1");
                bookListFragment = new BookListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, bookListFragment)
                        .addToBackStack(null)
                        .commit();
                return;

            case R.id.iv_back:
                itemClickListner.onClick(6);
                return;
        }
    }

    /*---------------------------------------------- get All category api call -------------------------------------------- */

    private void getCategoryList() {
        if (user.isOnline(activity)) {
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
                            if(result!=null){
                            if (result.getStatus() == true) {
                                dialog.dismiss();
                                recyclerViewData(result.getData());
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            }else {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*------------------------------------------------ data set Into Recycler View ------------------------------------------*/

    private void recyclerViewData(List<CategoryData> data) {
        Log.v("Size__", "Size: " + data.size());

        for (int i = 0; i< data.size(); i++){
            CategoryData d = data.get(i);
            Log.v("Size__", "Item " + i + ":  " + d.getCategoryId() + "  " + d.getName());
        }

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
        item1.clear();
        item2.clear();
        for ( int i = 0; i <= data.size() - 1; i++) {
//            if (i <  3) {
//                item1.add(data.get(i));
//            } else if (i <  3 + 3) {
//                item2.add(data.get(i));
//            }
            CategoryData model = data.get(i);

            if (model.getName().equals("Arabic Books")){

                boolean isAlreadyPresent = false;

                for (CategoryData d: item1){
                    if (d.getName().equals(model.getName())){
                        isAlreadyPresent = true;
                    }
                }

                if (!isAlreadyPresent){
                    item1.add(model);
                }
            }

            if (model.getName().equals("English Books")){

                boolean isAlreadyPresent = false;

                for (CategoryData d: item1){
                    if (d.getName().equals(model.getName())) {
                        isAlreadyPresent = true;
                        break;
                    }
                }

                if (!isAlreadyPresent){
                    item1.add(model);
                }
            }

            if (model.getName().equals("School Supplies")){

                boolean isAlreadyPresent = false;

                for (CategoryData d: item2){
                    if (d.getName().equals(model.getName())) {
                        isAlreadyPresent = true;
                        break;
                    }
                }

                if (!isAlreadyPresent){
                    item2.add(model);
                }
            }

            if (model.getName().equals("Office")){

                boolean isAlreadyPresent = false;

                for (CategoryData d: item2){
                    if (d.getName().equals(model.getName())) {
                        isAlreadyPresent = true;
                        break;
                    }
                }

                if (!isAlreadyPresent){
                    item2.add(model);
                }
            }

            if (model.getName().equals("Games toys")){

                boolean isAlreadyPresent = false;

                for (CategoryData d: item3){
                    if (d.getName().equals(model.getName())) {
                        isAlreadyPresent = true;
                        break;
                    }
                }

                if (!isAlreadyPresent){
                    item3.add(model);
                }
            }

            if (model.getName().equals("Computer Supplies")){

                boolean isAlreadyPresent = false;

                for (CategoryData d: item3){
                    if (d.getName().equals(model.getName())) {
                        isAlreadyPresent = true;
                        break;
                    }
                }

                if (!isAlreadyPresent){
                    item3.add(model);
                }
            }

            if (model.getName().equals("Smartphone")){

                boolean isAlreadyPresent = false;

                for (CategoryData d: item3){
                    if (d.getName().equals(model.getName())) {
                        isAlreadyPresent = true;
                        break;
                    }
                }

                if (!isAlreadyPresent){
                    item3.add(model);
                }
            }
        }

        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        viewPager.setSaveEnabled(false);
        dotsIndicator.setViewPager(viewPager);

        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryGridAdapter adapter1 = new CategoryGridAdapter(getActivity(), item1);
        recyclerView1.setAdapter(adapter1);

        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryGridAdapter adapter2 = new CategoryGridAdapter(getActivity(), item2);
        recyclerView2.setAdapter(adapter2);

        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryGridAdapter adapter3 = new CategoryGridAdapter(getActivity(), item3);
        recyclerView3.setAdapter(adapter3);

        booksHeading.setVisibility(View.VISIBLE);
        electronicsHeading.setVisibility(View.VISIBLE);
        officeSuppliesHeading.setVisibility(View.VISIBLE);
        othersHeading.setVisibility(View.VISIBLE);
    }


}
