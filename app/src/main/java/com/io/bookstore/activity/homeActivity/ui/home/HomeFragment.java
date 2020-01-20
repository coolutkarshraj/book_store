package com.io.bookstore.activity.homeActivity.ui.home;

import android.os.Bundle;
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

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.adapter.CourseAdapter;
import com.io.bookstore.adapter.SToreAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.fragment.BookstoresFragment;
import com.io.bookstore.fragment.CategoryListFragment;
import com.io.bookstore.fragment.CourseEnrollmentFragment;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.model.getAddressResponseModel.AddressResponseModel;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private ArrayList<Integer> item;
    private ArrayList<String> item1;

    private ArrayList<String> staoreName;
    private ArrayList<Integer> storeIcon;

    private ArrayList<String> coursename;
    private ArrayList<Integer> courseicon;
    private RecyclerView recycler_view_store,reccycler_ciew_course,recyclerView_courses;
    private CourseAdapter courseAdapter;
    private SToreAdapter sToreAdapter;
    private BookstoresFragment bookstoresFragment;
    private CategoryListFragment categoryListFragment;
    private CourseEnrollmentFragment courseEnrollmentFragment;
    private ImageView iv_view_all_stores,iv_viewall_instutues;
    private ItemClickListner itemClickListner;
    SliderLayout sliderLayout;
    TextView tvSliderName;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private RelativeLayout rl_main_child;
    private LinearLayout ll_sub_child;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.fragment_home, container, false);
      createArray();
      user = new userOnlineInfo();
        dialog = new NewProgressBar(getActivity());
      tvSliderName = root.findViewById(R.id.tv_slider_name);
        ll_sub_child = root.findViewById(R.id.ll_sub_child);
        sliderLayout = root.findViewById(R.id.image);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(2);
        setImageTOAddPart();
        iv_view_all_stores = root.findViewById(R.id.iv_view_all_stores);
        iv_viewall_instutues = root.findViewById(R.id.iv_viewall_instutues);
        recycler_view_store = root.findViewById(R.id.recycler_view_store);
        reccycler_ciew_course = root.findViewById(R.id.reccycler_ciew_course);
        recyclerView_courses = root.findViewById(R.id.recyclerView_courses);
        getStoreList();
        setRecyclerViewData();
        bindListner();
        getStoreAddressList();
        return root;
    }

    private void setImageTOAddPart() {
        for (int i =0;i<= item.size() -1 ;i++) {
            SliderView sliderView = new DefaultSliderView(getActivity());
            sliderView.setImageDrawable(item.get(i));
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(String.valueOf(item1.get(i)));
            ((DefaultSliderView) sliderView).setDescriptionTextSize(20);
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {

                }
            });
            sliderLayout.addSliderView(sliderView);
        }
    }

    private void bindListner() {

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
                courseEnrollmentFragment= new CourseEnrollmentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, courseEnrollmentFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setRecyclerViewData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        reccycler_ciew_course.setLayoutManager(gridLayoutManager);
        courseAdapter = new CourseAdapter(getActivity(),coursename,courseicon);
        reccycler_ciew_course.setAdapter(courseAdapter);
}

    private void createArray() {
        item = new ArrayList<>();
        item1 = new ArrayList<>();
        courseicon = new ArrayList<>();
        coursename = new ArrayList<>();
        staoreName = new ArrayList<>();
        storeIcon = new ArrayList<>();
        item.add(R.drawable.offer2);
        item.add(R.drawable.offer3);
        item.add(R.drawable.offer4);
        item.add(R.drawable.offer5);
        item.add(R.drawable.offer6);

        item1.add("Get Flat 20% Off");
        item1.add("Get Flat 30% off");
        item1.add("Buy One Get One Free");
        item1.add("Combo Pack Offer");
        item1.add("New Arrival Offer");

        courseicon.add(R.drawable.horizon);
        courseicon.add(R.drawable.azure);
        courseicon.add(R.drawable.ielts);
        courseicon.add(R.drawable.ielts);

        coursename.add("Microsoft Office,Cisco,More");
        coursename.add("CPA,LeaderShip,etc");
        coursename.add("IELTS,CPA,OtherCourse");
        coursename.add("IELTS - CPA - Arabic courses");
    }

    private void getStoreList() {
        if (user.isOnline(getActivity())) {
            dialog.show();
            ApiCaller.getStoreApi(getActivity(), Config.Url.getStoreApi,
                    new FutureCallback<StoreModel>() {

                        @Override
                        public void onCompleted(Exception e, StoreModel result) {
                            if(e!= null){
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if(result != null){
                                if(result.getStatus()){
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
        sToreAdapter = new SToreAdapter(getActivity(),result.getData());
        recycler_view_store.setAdapter(sToreAdapter);
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
                                Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void makaAddressListScrollView(final AddressResponseModel result) {
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
        for(i =0;i<result.getData().size();i++)
        {

            TextView textView = new TextView(getActivity());//create textview dynamically
            textView.setText(result.getData().get(i).getCity());
            LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params6.setMargins(10, 5, 10, 5);
            params6.gravity = Gravity.CENTER;
            textView.setLayoutParams(params6);
            textView.setBackgroundResource(R.drawable.background_rounded_theme_address);
            textView.setPadding(10, 10, 10, 10);
            textView.setTextColor(this.getResources().getColor(R.color.white));
            linearLayout.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),result.getData().get(finalI).getCity(),Toast.LENGTH_SHORT).show();
                    StaticData.address = result.getData().get(finalI).getCity() ;
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
}