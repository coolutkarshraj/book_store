package com.io.bookstores.activity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.io.bookstores.R;
import com.io.bookstores.activity.authentication.LoginActivity;
import com.io.bookstores.activity.homeActivity.ui.order.OrderFragment;
import com.io.bookstores.adapter.ProfilePagerAdapter;
import com.io.bookstores.fragment.SettingsFragment;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.loginModel.LoginModel;

public class ProfileFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static CircleImageView iv_avatar;
    public static TextView et_firstname;
    EditProfileFragment editProfileFragment;
    FloatingActionButton fabEditProfile;
    private TextView loggedih;
    LinearLayout ll_main_view;
    private  ImageView iv_back;
    private LocalStorage localStorage;
    private ItemClickListner itemClickListner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.profile_fragment, container, false);
        initView(view);
        localStorage = new LocalStorage(getActivity());
        LoginModel loginModel =  localStorage.getUserProfile() ;
        System.out.println(loginModel);
        if(localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")){
            ll_main_view.setVisibility(View.GONE);
            loggedih.setVisibility(View.VISIBLE);
        }else {
            ll_main_view.setVisibility(View.VISIBLE);
            loggedih.setVisibility(View.GONE);

        }
        bindListner();
        return view;
    }
    private void bindListner() {
        loggedih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        fabEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileFragment = new EditProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, editProfileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner.onClick(6);
            }
        });
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        );

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    private void initView(View view) {
        iv_avatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
        iv_back = (ImageView)view.findViewById(R.id.iv_back);
        itemClickListner = (ItemClickListner)getActivity();
        tabLayout = (TabLayout) view.findViewById(R.id.tab_profile);
        loggedih = (TextView) view.findViewById(R.id.loggedih);
        ll_main_view = (LinearLayout) view.findViewById(R.id.ll_main_view);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_profile);
        fabEditProfile = (FloatingActionButton) view.findViewById(R.id.fab_editpic);
        et_firstname = (TextView) view.findViewById(R.id.et_firstname);
        ProfilePagerAdapter pfadapter = new ProfilePagerAdapter(getChildFragmentManager());
        pfadapter.addFragment(new SettingsFragment());
        pfadapter.addFragment(new OrderFragment());
        viewPager.setAdapter(pfadapter);
    }


}
