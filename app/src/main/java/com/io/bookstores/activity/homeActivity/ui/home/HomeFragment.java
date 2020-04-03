package com.io.bookstores.activity.homeActivity.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.io.bookstores.StaticData;
import com.io.bookstores.adapter.AdSliderAdapter;
import com.io.bookstores.adapter.CourseAdapter;
import com.io.bookstores.adapter.SToreAdapter;
import com.io.bookstores.adapter.categoryAdapter.CategoryFragmentAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.fragment.BookstoresFragment;
import com.io.bookstores.fragment.CourseEnrollmentFragment;
import com.io.bookstores.fragment.CoursesFragment;
import com.io.bookstores.fragment.InstituteFragment;
import com.io.bookstores.fragment.category.AddressSliderFragment;
import com.io.bookstores.fragment.category.CategoryGridFragment;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.DbHelper;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.WishListLocalResponseModel;
import com.io.bookstores.model.dilvery.DilveryAddressDataModel;
import com.io.bookstores.model.dilvery.DilveryAdressResponseModel;
import com.io.bookstores.model.getAddressResponseModel.AddressResponseModel;
import com.io.bookstores.model.getAddressResponseModel.Datum;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;
import com.io.bookstores.model.insituteModel.TrendingInstituteResponseModel;
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
import java.util.Locale;


public class HomeFragment extends Fragment implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Integer> item = new ArrayList<>();
    private ArrayList<String> item1 = new ArrayList<>();
    private ArrayList<String> staoreName = new ArrayList<>();
    private ArrayList<Integer> storeIcon = new ArrayList<>();
    private List<TrendingInstituteDataModel> list= new ArrayList<>();
    private List<WishListLocalResponseModel> lists = new ArrayList<>();
    private ArrayList<String> coursename = new ArrayList<>();
    private ArrayList<Integer> courseicon = new ArrayList<>();
    private RecyclerView recycler_view_store, reccycler_ciew_course, recyclerView_courses;
    private CourseAdapter courseAdapter;
    private SToreAdapter sToreAdapter;
    ItemClickListner clickListner;
    CategoryGridFragment categoryGridFragment;
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
    public static SwipeRefreshLayout swipeRefreshLayout;
    private View root;
    public  static ViewPager viewPager;
    private List<Datum> listd= new ArrayList();
    SpringDotsIndicator dotsIndicator;
    LocalStorage localStorage;
    DbHelper dbHelper;
    int bookId;
    int sizee = 0;
    private  List<String> listcity = new ArrayList<>();
    private List<String> listDistict = new ArrayList<>();
    private List<DilveryAddressDataModel> listdata = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        setRetainInstance(true);
        intializeViews(root);
        getAllCities();
        return root;
    }

    private void intializeViews(View root) {
       // Toast.makeText(getActivity(), "selected lang = "+ StaticData.selectedLanguage, Toast.LENGTH_SHORT).show();
        recyclerViewClickListener = this;
        user = new userOnlineInfo();
        dbHelper = new DbHelper(getActivity());
        dialog = new NewProgressBar(getActivity());
        ll_sub_child = root.findViewById(R.id.ll_sub_child);
        sliderView = root.findViewById(R.id.imageSlider);
        getSliderAdList();
        viewPager = root.findViewById(R.id.viewpager);
        dotsIndicator = (SpringDotsIndicator) root.findViewById(R.id.dots_indicator);
        iv_view_all_stores = root.findViewById(R.id.iv_view_all_stores);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        iv_viewall_instutues = root.findViewById(R.id.iv_viewall_instutues);
        recycler_view_store = root.findViewById(R.id.recycler_view_store);
        reccycler_ciew_course = root.findViewById(R.id.reccycler_ciew_course);
        recyclerView_courses = root.findViewById(R.id.recyclerView_courses);
        localStorage = new LocalStorage(getActivity());
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
        swipeRefreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clcik", Toast.LENGTH_SHORT).show();
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
                                /*makaAddressListScrollView(result);*/
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
    private void getAllCities () {
        if (user.isOnline(getActivity())) {
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


                                }
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }

    }





    private void makaAddressListScrollView(List<DilveryAddressDataModel>  resultt) {
      //  listd = resultt.getData();

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