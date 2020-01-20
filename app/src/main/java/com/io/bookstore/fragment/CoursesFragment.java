package com.io.bookstore.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.CoursesAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.model.CourseModel;
import com.io.bookstore.model.courseModel.CourseDataModel;
import com.io.bookstore.model.courseModel.CourseResponseModel;
import com.io.bookstore.model.insituteModel.InsituiteResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CoursesFragment extends Fragment implements RecyclerViewClickListener {

    private Activity activity;
    private List<CourseDataModel> item;
    private RecyclerView recyclerView;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private CourseEnrollmentFragment courseEnrollmentFragment;
    private RecyclerViewClickListener recyclerViewClickListener;
    private CoursesAdapter coursesAdapter;
    int instituteId;

    public CoursesFragment(Integer instituteId) {
        this.instituteId = instituteId;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courses, container, false);

        intializeViews(root);
        bindListner();
        startWorking();
        return root;
    }

    private void intializeViews(View root) {
        activity = getActivity();
        recyclerViewClickListener = this;
        user = new userOnlineInfo();
        dialog = new NewProgressBar(activity);
        item = new ArrayList<>();
        recyclerView = root.findViewById(R.id.recyclerView_courses);
    }

    private void bindListner() {

    }

    private void startWorking() {
        getcourseList();
    }

    private void getcourseList() {
        if (user.isOnline(getActivity())) {
            dialog.show();
            ApiCaller.getCourseList(getActivity(), Config.Url.courseList + instituteId,
                    new FutureCallback<CourseResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, CourseResponseModel result) {
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

    private void setRecyclerView(CourseResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        coursesAdapter = new CoursesAdapter(getActivity(), result.getData(), recyclerViewClickListener);
        item = result.getData();
        recyclerView.setAdapter(coursesAdapter);
    }


    @Override
    public void onClickPosition(int position) {
        courseEnrollmentFragment= new CourseEnrollmentFragment(item.get(position));
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, courseEnrollmentFragment)
                .addToBackStack(null)
                .commit();
    }
}