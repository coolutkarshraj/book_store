package com.io.bookstores.activity.homeActivity.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.categoryAdapter.CategoryFragmentAdapter;
import com.io.bookstores.adapter.homeAdapter.AdSliderAdapter;
import com.io.bookstores.adapter.homeAdapter.HomeCourseRvAdapter;
import com.io.bookstores.adapter.homeAdapter.HomeSchoolsRvAdapter;
import com.io.bookstores.adapter.homeAdapter.HomeStoreRvAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.fragment.bookStoreFragments.AddressSliderFragment;
import com.io.bookstores.fragment.courseFragment.AllCoursesListFragment;
import com.io.bookstores.fragment.courseFragment.AllInstituteListFragment;
import com.io.bookstores.fragment.courseFragment.CourseEnrollFragment;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.dilvery.DilveryAddressDataModel;
import com.io.bookstores.model.dilvery.DilveryAdressResponseModel;
import com.io.bookstores.model.getAddressResponseModel.AddressResponseModel;
import com.io.bookstores.model.getAddressResponseModel.Datum;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;
import com.io.bookstores.model.insituteModel.TrendingInstituteResponseModel;
import com.io.bookstores.model.schoolModel.GetAllSchoolDataModel;
import com.io.bookstores.model.schoolModel.GetAllSchoolResponseModel;
import com.io.bookstores.model.sliderAdModel.AdModel;
import com.io.bookstores.model.storeModel.StoreModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private Activity activity;
    private List<TrendingInstituteDataModel> list = new ArrayList<>();
    private RecyclerView recycler_view_store, reccycler_ciew_course, recyclerView_courses,reccycler_schools;
    private HomeCourseRvAdapter courseAdapter;
    private HomeStoreRvAdapter homeStoreRvAdapter;
    private AllCoursesListFragment allCoursesListFragment;
    private CourseEnrollFragment courseEnrollFragment;
    private AllInstituteListFragment allInstituteListFragment;
    private RelativeLayout iv_viewall_instutues, iv_viewall_schools, iv_view_all_stores;
    private ItemClickListner itemClickListner;
    private RecyclerViewClickListener recyclerViewClickListener;
    private AddressResponseModel result;
    private SliderView sliderView;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private RelativeLayout rl_main_child;
    private LinearLayout ll_sub_child;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private View root;
    public static ViewPager viewPager;
    private List<Datum> listd = new ArrayList();
    private SpringDotsIndicator dotsIndicator;
    private LocalStorage localStorage;
    private DbHelper dbHelper;
    private int sizee = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        setRetainInstance(true);
        intializeViews(root);
        bindListner();
        startWorking();
        return root;
    }

    /*------------------------------------------- intailize all Views that are used in this fragment ----------------------------*/

    private void intializeViews(View root) {
        activity = getActivity();
        recyclerViewClickListener = this;
        user = new userOnlineInfo();
        dbHelper = new DbHelper(getActivity());
        dialog = new NewProgressBar(getActivity());
        itemClickListner = (ItemClickListner) getActivity();
        assert itemClickListner != null;
        ll_sub_child = root.findViewById(R.id.ll_sub_child);
        sliderView = root.findViewById(R.id.imageSlider);
        viewPager = root.findViewById(R.id.viewpager);
        dotsIndicator = (SpringDotsIndicator) root.findViewById(R.id.dots_indicator);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        iv_viewall_schools = root.findViewById(R.id.iv_viewall_schools);
        iv_view_all_stores = root.findViewById(R.id.iv_view_all_stores);
        iv_viewall_instutues = root.findViewById(R.id.iv_viewall_instutues);
        recycler_view_store = root.findViewById(R.id.recycler_view_store);
        reccycler_ciew_course = root.findViewById(R.id.reccycler_ciew_course);
        recyclerView_courses = root.findViewById(R.id.recyclerView_courses);
        reccycler_schools = root.findViewById(R.id.reccycler_schools);
        localStorage = new LocalStorage(getActivity());
    }

    /*----------------------------------------- bind all views that are used in this fragment -----------------------------------*/

    private void bindListner() {
        swipeRefreshLayout.setOnRefreshListener(this);
        iv_viewall_schools.setOnClickListener(this);
        iv_view_all_stores.setOnClickListener(this);
        iv_viewall_instutues.setOnClickListener(this);
    }

    /*------------------------------------------------------- on click Listner --------------------------------------------------*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_view_all_stores:
                itemClickListner.onClick(1);
              break;

            case R.id.iv_viewall_instutues:
                allInstituteListFragment = new AllInstituteListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, allInstituteListFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.iv_viewall_schools:
                itemClickListner.onClick(7);

        }
    }

    /*------------------------------------------------------ start Working ----------------------------------------------------*/

    private void startWorking() {
        getSliderAdList();
        getAllCities();
        getStoreList();
        getInstituiteList();
        getAllSchoolList();
    }

    /*-------------------------------------------------- get all banner from Api ---------------------------------------------*/

    private void getSliderAdList() {
        if (user.isOnline(activity)) {
            ApiCaller.getAdModel(getActivity(), Config.Url.discountList,
                    new FutureCallback<AdModel>() {

                        @Override
                        public void onCompleted(Exception e, AdModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {
                                if (result.getStatus()) {
                                    setSliderAdsAdapter(result);
                                }
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*------------------------------------------------------ setUp of Slider -------------------------------------------------*/

    private void setSliderAdsAdapter(AdModel result) {
        AdSliderAdapter adapter = new AdSliderAdapter(getActivity(), result.getData());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    /*------------------------------------------------ get all District from Api --------------------------------------------*/

    private void getAllCities() {
        if (user.isOnline(activity)) {
            ApiCaller.getdistic(getActivity(), Config.Url.disticGet,
                    new FutureCallback<DilveryAdressResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, DilveryAdressResponseModel result) {

                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus()) {
                                    makaAddressListScrollView(result.getData());
                                } else {
                                    Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }

    }

    /*--------------------------------------------- set Up of View Pager to display all district -------------------------------*/

    private void makaAddressListScrollView(List<DilveryAddressDataModel> resultt) {
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(((FragmentActivity) getActivity()).getSupportFragmentManager());
        if (resultt.size() % 6 == 0) {
            sizee = resultt.size() / 6;
        } else {
            sizee = resultt.size() / 6 + 1;
        }
        for (int p = 0; p < sizee; p++) {
          adapter.addFragment(new AddressSliderFragment(resultt));

            adapter.notifyDataSetChanged();

        }
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        dotsIndicator.setViewPager(viewPager);
    }
    /*---------------------------------------------------- get all store api call -------------------------------------------*/

    private void getStoreList() {
        if (user.isOnline(activity)) {
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

    /*--------------------------------------------- setup of recycler View of All store ----------------------------------------*/

    private void setRecyclerView(final StoreModel result) {
        final List<com.io.bookstores.model.storeModel.Datum> datumList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (i < result.getData().size()) {
                datumList.add(result.getData().get(i));
            }
        }
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
        recycler_view_store.setLayoutManager(gridLayoutManager1);
        homeStoreRvAdapter = new HomeStoreRvAdapter(getActivity(), datumList);
        recycler_view_store.setAdapter(homeStoreRvAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (datumList.size() > 1) {
                        com.io.bookstores.model.storeModel.Datum datum = datumList.get(datumList.size() - 1);

                        int index = result.getData().indexOf(datum);

                        int j = 1;
                        datumList.clear();
                        for (int i = 0; i < 4; i++) {
                            if ((j + index) >= result.getData().size()) {
                                index = 0;
                                j = 0;
                            }

                            datumList.add(result.getData().get(j + index));
                            j++;
                        }

                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    homeStoreRvAdapter.notifyDataSetChanged();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /*------------------------------------------------ get all institute Api Call ---------------------------------------------*/

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

    /*-------------------------------------------- setUp of All institute recycler view ----------------------------------------*/

    private void setRecyclerViewData(final TrendingInstituteResponseModel result) {
        final List<TrendingInstituteDataModel> datumList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (i < result.getData().size()) {
                datumList.add(result.getData().get(i));
            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        reccycler_ciew_course.setLayoutManager(gridLayoutManager);
        courseAdapter = new HomeCourseRvAdapter((FragmentActivity) getActivity(), datumList, recyclerViewClickListener);
        list = result.getData();
        reccycler_ciew_course.setAdapter(courseAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (datumList.size() > 1) {
                        TrendingInstituteDataModel datum = datumList.get(datumList.size() - 1);

                        int index = result.getData().indexOf(datum);

                        int j = 1;
                        datumList.clear();
                        for (int i = 0; i < 4; i++) {
                            if ((j + index) >= result.getData().size()) {
                                index = 0;
                                j = 0;
                            }

                            datumList.add(result.getData().get(j + index));
                            j++;
                        }

                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    courseAdapter.notifyDataSetChanged();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /*---------------------------------------------------- get all school api call -------------------------------------------*/

    private void getAllSchoolList() {
        if (user.isOnline(activity)) {

            ApiCaller.getSchoolApi(getActivity(), Config.Url.getSchools,
                    new FutureCallback<GetAllSchoolResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, GetAllSchoolResponseModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {
                                if (result.isStatus()) {

                                    setofSchoolRecyclerView(result.getData());
                                }
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*--------------------------------------------- setup of recycler View of All school ----------------------------------------*/

    private void setofSchoolRecyclerView(List<GetAllSchoolDataModel> result) {
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
        reccycler_schools.setLayoutManager(gridLayoutManager1);
         HomeSchoolsRvAdapter sToreAdapter = new HomeSchoolsRvAdapter(getActivity(), result);
        reccycler_schools.setAdapter(sToreAdapter);
    }


    /*----------------------------------------- on click of institute recycler view --------------------------------------------*/

    @Override
    public void onClickPosition(int position) {
        allCoursesListFragment = new AllCoursesListFragment(list.get(position).getInstituteId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, allCoursesListFragment)
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