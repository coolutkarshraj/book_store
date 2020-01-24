package com.io.bookstore.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.adapter.CoursesAdapter;
import com.io.bookstore.adapter.EnrolledCourseRvAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.courseModel.CourseResponseModel;
import com.io.bookstore.model.courseModel.EnrollCourseResponseModel;
import com.io.bookstore.model.courseModel.EnrolledCourseListDataModel;
import com.io.bookstore.model.courseModel.EnrolledCourseListResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EnrollCourseListFragment extends Fragment implements View.OnClickListener {


    private Activity activity;
    private RecyclerView recyclerView;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private LocalStorage localStorage;
    private TextView tv_login;
    private SearchView searchView;
    private EnrolledCourseRvAdapter adapter;
    private NestedScrollView nestedScrollView;
    private List<EnrolledCourseListDataModel> item;

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


    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();
        item = new ArrayList<>();
        dialog = new NewProgressBar(activity);
        localStorage = new LocalStorage(activity);
        searchView = view.findViewById(R.id.sv_courses);
        recyclerView = view.findViewById(R.id.recyclerView_courses);
        nestedScrollView = view.findViewById(R.id.nested_view);
        tv_login = view.findViewById(R.id.tv_login);
    }

    private void bindListner() {
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                Intent i =new Intent(activity, LoginActivity.class);
                startActivity(i);
                return;
        }

    }

    private void startWorking() {
        getdataFromLocalStorage();
        getEnrolledCourseListApi();
        searchViewSetUp();
    }


    private void getdataFromLocalStorage() {
        if (localStorage.getString(LocalStorage.token) == null ||
                localStorage.getString(LocalStorage.token).equals("")) {
            tv_login.setVisibility(View.VISIBLE);
        } else {
            nestedScrollView.setVisibility(View.VISIBLE);
        }
    }

    private void getEnrolledCourseListApi() {
        if (user.isOnline(getActivity())) {
            dialog.show();
            ApiCaller.enrollCourseList(getActivity(), Config.Url.courseEnrolledList, localStorage.getString(LocalStorage.token),
                    new FutureCallback<EnrolledCourseListResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, EnrolledCourseListResponseModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            datasetToRecyclerView(result);
                            dialog.dismiss();


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void datasetToRecyclerView(EnrolledCourseListResponseModel result) {
            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            adapter = new EnrolledCourseRvAdapter(getActivity(), result.getData());
            recyclerView.setAdapter(adapter);

    }

    private void searchViewSetUp() {


    }



}
