package com.io.bookstores.fragment.courseFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.authentication.LoginActivity;
import com.io.bookstores.adapter.courseAdapter.EnrolledCourseRvAdapter;
import com.io.bookstores.adapter.courseAdapter.GuestEnrolledCourseRvAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.courseModel.EnrolledCourseListDataModel;
import com.io.bookstores.model.courseModel.EnrolledCourseListResponseModel;
import com.io.bookstores.model.guestCoursedModel.GuestEnrolledCourseResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

public class EnrollCourseListFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private RecyclerView recyclerView;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private LocalStorage localStorage;
    private TextView tv_login, notdata;
    private SearchView searchView;
    private EnrolledCourseRvAdapter adapter;
    private NestedScrollView nestedScrollView;
    private List<EnrolledCourseListDataModel> item;
    private ImageView iv_back;
    private ItemClickListner itemClickListner;

    public EnrollCourseListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enroll_course_list_fragment, container, false);
        intializeViews(view);
        bindListner();
        startWorking();
        return view;
    }

    /*----------------------------------- intilize all views that are used in this fragment ------------------------------------*/

    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        item = new ArrayList<>();
        itemClickListner = (ItemClickListner) getActivity();
        iv_back = view.findViewById(R.id.iv_back);
        dialog = new NewProgressBar(activity);
        localStorage = new LocalStorage(activity);
        searchView = view.findViewById(R.id.sv_courses);
        recyclerView = view.findViewById(R.id.recyclerView_courses);
        nestedScrollView = view.findViewById(R.id.nested_view);
        tv_login = view.findViewById(R.id.tv_login);
        notdata = view.findViewById(R.id.notdata);
    }

    /*----------------------------------------- bind all views that are used in this fragment --------------------------------*/

    private void bindListner() {
        tv_login.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    /*--------------------------------------------------- on click Listner --------------------------------------------------*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                Intent i = new Intent(activity, LoginActivity.class);
                startActivity(i);
                return;

            case R.id.iv_back:
                itemClickListner.onClick(6);
                return;
        }

    }

    /*------------------------------------------------------- start Working ------------------------------------------------*/

    private void startWorking() {
        getdataFromLocalStorage();
    }

    /* ------------------------------------------ get data from Local Storage ---------------------------------------------*/

    private void getdataFromLocalStorage() {
        if (localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")) {
            tv_login.setVisibility(View.VISIBLE);
            if (localStorage.getString(LocalStorage.guestId) != " " || localStorage.getString(LocalStorage.guestId) != null) {
                nestedScrollView.setVisibility(View.VISIBLE);
                tv_login.setVisibility(View.GONE);
                getGuestCourseDetail();
            }
        } else {

            getEnrolledCourseListApi();
            nestedScrollView.setVisibility(View.VISIBLE);

        }
    }/*---------------------------------------------------- Guest Course List ---------------------------------------------*/

    private void getGuestCourseDetail() {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.guestEnrollCourseList(getActivity(), Config.Url.guestEnrolledList + localStorage.getString(LocalStorage.guestId),
                    new FutureCallback<GuestEnrolledCourseResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, GuestEnrolledCourseResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }

                            if (result != null) {
                                guestUserData(result);
                                dialog.dismiss();

                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void guestUserData(GuestEnrolledCourseResponseModel result) {
        if (result.getData().isEmpty()) {
            notdata.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            notdata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            GuestEnrolledCourseRvAdapter adapter = new GuestEnrolledCourseRvAdapter(getActivity(), result.getData());
            recyclerView.setAdapter(adapter);
        }
    }

    /*------------------------------------------------ get Enroll list api call ---------------------------------------------*/

    private void getEnrolledCourseListApi() {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.enrollCourseList(getActivity(), Config.Url.courseEnrolledList, localStorage.getString(LocalStorage.token),
                    new FutureCallback<EnrolledCourseListResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, EnrolledCourseListResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(getActivity(), "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                } else {
                                    datasetToRecyclerView(result);
                                    dialog.dismiss();
                                }
                            }


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*------------------------------------------set all data into recycler View --------------------------------------------*/

    private void datasetToRecyclerView(EnrolledCourseListResponseModel result) {
        if (result.getData().isEmpty()) {
            notdata.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            notdata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            adapter = new EnrolledCourseRvAdapter(getActivity(), result.getData());
            recyclerView.setAdapter(adapter);
        }

    }




}
