package com.io.bookstores.fragment.courseFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.adapter.courseAdapter.CoursesAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.model.courseModel.CourseDataModel;
import com.io.bookstores.model.courseModel.CourseResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AllCoursesListFragment extends Fragment implements RecyclerViewClickListener, View.OnClickListener {

    private Activity activity;
    private List<CourseDataModel> item;
    private RecyclerView recyclerView;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private CourseEnrollFragment courseEnrollFragment;
    private RecyclerViewClickListener recyclerViewClickListener;
    private CoursesAdapter coursesAdapter;
    int instituteId;
    private SearchView searchView;
    private ItemClickListner itemClickListner;
    private ImageView iv_back;
    private List<CourseDataModel> mData =new ArrayList<>();

    public AllCoursesListFragment(Integer instituteId) {
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

    /*---------------------------------- intialize all Views that are used in this Fragment-----------------------------------*/

    private void intializeViews(View root) {
        activity = getActivity();
        recyclerViewClickListener = this;
        user = new userOnlineInfo();
        dialog = new NewProgressBar(activity);
        item = new ArrayList<>();
        searchView = root.findViewById(R.id.sv_courses);
        itemClickListner = (ItemClickListner)getActivity();
        iv_back = root.findViewById(R.id.iv_back);
        recyclerView = root.findViewById(R.id.recyclerView_courses);
        iv_back.setOnClickListener(this);
    }

    /*---------------------------------------- bind all views that are used in this activity ----------------------------------*/

    private void bindListner() {

    }

    /*----------------------------------------------------- start Working -----------------------------------------------------*/

    private void startWorking() {
        getcourseList();
        searchViewSetUp();
    }

    /*---------------------------------------------- get all course List From Api ---------------------------------------------*/

    private void getcourseList() {
        if (user.isOnline(getActivity())) {
           // dialog.show();
            ApiCaller.getCourseList(getActivity(), Config.Url.courseList + instituteId,
                    new FutureCallback<CourseResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, CourseResponseModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {
                                if (result.getStatus() == null) {
                                    Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                } else if (result.getStatus()) {
                                    setRecyclerView(result);
                                }
                            }else {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            //dialog.dismiss();


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*-------------------------------------------- all courses data set into Recycler View ---------------------------------*/

    private void setRecyclerView(CourseResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        coursesAdapter = new CoursesAdapter(getActivity(), result.getData(), recyclerViewClickListener);
        item = result.getData();
        recyclerView.setAdapter(coursesAdapter);
    }

    /*------------------------------------------------------ set Up of Search View ------------------------------------------*/

    private void searchViewSetUp() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<CourseDataModel> newlist = new ArrayList<>();
                for (CourseDataModel productList : item) {
                    String name = productList.getCourseName().toLowerCase();
                    if (name.contains(s))
                        newlist.add(productList);
                }
                coursesAdapter.setFilter(newlist);
                return true;
            }

        });


    }


    @Override
    public void onClickPosition(int position) {
        courseEnrollFragment = new CourseEnrollFragment(item.get(position));
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, courseEnrollFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.iv_back:
               itemClickListner.onClick(6);
       }
    }
}