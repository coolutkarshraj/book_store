package com.io.bookstore.activity.homeActivity.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.adapter.AdSliderAdapter;
import com.io.bookstore.adapter.CourseAdapter;
import com.io.bookstore.adapter.SToreAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.fragment.BookstoresFragment;
import com.io.bookstore.fragment.CategoryListFragment;
import com.io.bookstore.fragment.CourseEnrollmentFragment;
import com.io.bookstore.fragment.CoursesFragment;
import com.io.bookstore.fragment.InstituteFragment;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.model.InstituteModel;
import com.io.bookstore.model.getAddressResponseModel.AddressResponseModel;
import com.io.bookstore.model.getAddressResponseModel.Datum;
import com.io.bookstore.model.insituteModel.InsituiteDataModel;
import com.io.bookstore.model.insituteModel.InsituiteResponseModel;
import com.io.bookstore.model.insituteModel.TrendingInstituteDataModel;
import com.io.bookstore.model.insituteModel.TrendingInstituteResponseModel;
import com.io.bookstore.model.sliderAdModel.AdModel;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Integer> item = new ArrayList<>();
    private ArrayList<String> item1 = new ArrayList<>();
    private ArrayList<String> staoreName = new ArrayList<>();
    private ArrayList<Integer> storeIcon = new ArrayList<>();
    private List<TrendingInstituteDataModel> list= new ArrayList<>();
    private ArrayList<String> coursename = new ArrayList<>();
    private ArrayList<Integer> courseicon = new ArrayList<>();
    private RecyclerView recycler_view_store, reccycler_ciew_course, recyclerView_courses;
    private CourseAdapter courseAdapter;
    private SToreAdapter sToreAdapter;
    private BookstoresFragment bookstoresFragment;
    private CoursesFragment coursesFragment;
    private CourseEnrollmentFragment courseEnrollmentFragment;
    InstituteFragment instituteFragment;
    private RelativeLayout iv_view_all_stores, iv_viewall_instutues;
    private ItemClickListner itemClickListner;
    RecyclerViewClickListener recyclerViewClickListener;
    AddressResponseModel result;
    SliderView sliderView;
    TextView tvSliderName;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private RelativeLayout rl_main_child;
    private LinearLayout ll_sub_child;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View root;
    private List<Datum> listd= new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        intializeViews(root);
        getStoreAddressList();
        return root;
    }

    private void intializeViews(View root) {
        recyclerViewClickListener = this;
        user = new userOnlineInfo();
        dialog = new NewProgressBar(getActivity());
        ll_sub_child = root.findViewById(R.id.ll_sub_child);
        sliderView = root.findViewById(R.id.imageSlider);
        getSliderAdList();

        iv_view_all_stores = root.findViewById(R.id.iv_view_all_stores);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        iv_viewall_instutues = root.findViewById(R.id.iv_viewall_instutues);
        recycler_view_store = root.findViewById(R.id.recycler_view_store);
        reccycler_ciew_course = root.findViewById(R.id.reccycler_ciew_course);
        recyclerView_courses = root.findViewById(R.id.recyclerView_courses);
        getStoreList();
        getInstituiteList();
        bindListner();

    }

    private void getSliderAdList() {

        if (user.isOnline(getActivity())) {
            ApiCaller.getAdModel(getActivity(), Config.Url.discountList,
                    new FutureCallback<AdModel>() {

                        @Override
                        public void onCompleted(Exception e, AdModel result) {
                            if(e!= null){
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if(result != null){
                                if(result.getStatus()){
                                    setSliderAdsAdapter(result);
                                }
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }

    }

    private void setSliderAdsAdapter(AdModel result) {

        AdSliderAdapter adapter = new AdSliderAdapter(getActivity(),result.getData());
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

        sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
        sliderView.startAutoCycle();


    }

    private void bindListner() {
        swipeRefreshLayout.setOnRefreshListener(this);
        iv_view_all_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) getActivity();
                assert itemClickListner != null;
                itemClickListner.onClick(1);

            }
        });

        iv_viewall_instutues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instituteFragment = new InstituteFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, instituteFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    private void getStoreList() {
        if (user.isOnline(getActivity())) {
            dialog.show();
            ApiCaller.getStoreApi(getActivity(), Config.Url.getStoreApi,
                    new FutureCallback<StoreModel>() {

                        @Override
                        public void onCompleted(Exception e, StoreModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {
                                if (result.getStatus()) {
                                    setRecyclerView(result);
                                }
                            }
                            dialog.dismiss();


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setRecyclerView(StoreModel result) {
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
        recycler_view_store.setLayoutManager(gridLayoutManager1);
        sToreAdapter = new SToreAdapter(getActivity(), result.getData());
        recycler_view_store.setAdapter(sToreAdapter);
    }

    private void getInstituiteList() {
        if (user.isOnline(getActivity())) {
            ApiCaller.getTrendingInstiuiteList(getActivity(), Config.Url.trendingInstitute,
                    new FutureCallback<TrendingInstituteResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, TrendingInstituteResponseModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {
                                if (result.getStatus()) {
                                    setRecyclerViewData(result);
                                }

                            }
                            dialog.dismiss();


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setRecyclerViewData(TrendingInstituteResponseModel result) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        reccycler_ciew_course.setLayoutManager(gridLayoutManager);

        courseAdapter = new CourseAdapter(getActivity(), result.getData(), recyclerViewClickListener);
        list = result.getData();
        reccycler_ciew_course.setAdapter(courseAdapter);
    }

    private void getStoreAddressList() {
        if (user.isOnline(getActivity())) {
            //  dialog.show();
            ApiCaller.getAddresssList(getActivity(), Config.Url.getAdressApi,
                    new FutureCallback<AddressResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, AddressResponseModel result) {
                            dialog.dismiss();
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                dialog.dismiss();
                                // Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                makaAddressListScrollView(result);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void makaAddressListScrollView(AddressResponseModel resultt) {
        listd = resultt.getData();
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        horizontalScrollView.setLayoutParams(layoutParams);
        horizontalScrollView.setVerticalScrollBarEnabled(false);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(linearParams);
        horizontalScrollView.addView(linearLayout);

        int i;

        for (i = 0; i < listd.size(); i++) {

            TextView textView = new TextView(getActivity());//create textview dynamically

            textView.setText(listd.get(i).getCity());

            LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params6.setMargins(10, 10, 0, 10);
            params6.gravity = Gravity.CENTER;
            textView.setLayoutParams(params6);
            textView.setBackgroundResource(R.drawable.background_rounded_theme_address);
            textView.setPadding(20, 10, 20, 10);
            textView.setTextColor(this.getResources().getColor(R.color.white));
            linearLayout.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), listd.get(finalI).getCity(), Toast.LENGTH_SHORT).show();
                    StaticData.address = listd.get(finalI).getCity();
                    itemClickListner = (ItemClickListner) getActivity();
                    assert itemClickListner != null;
                    itemClickListner.onClick(4);
                }
            });
        }
        if (ll_sub_child != null) {
            ll_sub_child.addView(horizontalScrollView);
        }

    }

    @Override
    public void onClickPosition(int position) {
        coursesFragment = new CoursesFragment(list.get(position).getInstituteId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, coursesFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                intializeViews(root);
            }
        }, 1000);


    }
}