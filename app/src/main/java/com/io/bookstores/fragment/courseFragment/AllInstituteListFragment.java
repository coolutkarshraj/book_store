package com.io.bookstores.fragment.courseFragment;

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
import com.io.bookstores.adapter.courseAdapter.InstitutesAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.model.insituteModel.InsituiteDataModel;
import com.io.bookstores.model.insituteModel.InsituiteResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllInstituteListFragment extends Fragment implements RecyclerViewClickListener, View.OnClickListener {

    private Activity activity;
    private RecyclerView recyclerView;
    private InstitutesAdapter institutesAdapter;
    private AllCoursesListFragment allCoursesListFragment;
    private userOnlineInfo user;
    private NewProgressBar dialog;
    private ImageView iv_back;
    private SearchView searchView;
    private List<InsituiteDataModel> item = new ArrayList<>();
    RecyclerViewClickListener recyclerViewClickListener;
    private ItemClickListner itemClickListner;

    public AllInstituteListFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_institute, container, false);
        intializeViews(root);
        bindListner();
        startWorking();
        return root;
    }



    /*--------------------------------------- intialize all Views that are used in this fragment ------------------------------*/

    private void intializeViews(View root) {
        activity = getActivity();
        user = new userOnlineInfo();
        recyclerViewClickListener = this;
        itemClickListner =(ItemClickListner)getActivity();
        iv_back = root.findViewById(R.id.iv_back);
        searchView = root.findViewById(R.id.sv_institute);
        dialog = new NewProgressBar(getActivity());
        recyclerView = root.findViewById(R.id.recyclerView_institute);

    }

    /*------------------------------------------ bind all view that are used in this activity ---------------------------------*/

    private void bindListner() {
        iv_back.setOnClickListener(this);
    }

    /*--------------------------------------------------- on Click Listner -------------------------------------------------*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                itemClickListner.onClick(6);
        }
    }
    /*------------------------------------------------------ start Working ------------------------------------------------*/

    private void startWorking() {
        getInstituiteList();
        searchViewSetUp();
    }

    /*-------------------------------------------------- get all Institute Api Call -----------------------------------------*/

    private void getInstituiteList() {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.getInstiuiteList(getActivity(), Config.Url.insituitelist,
                    new FutureCallback<InsituiteResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, InsituiteResponseModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {
                                if (result.getStatus()) {
                                    setRecyclerView(result);
                                }
                            }else {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            dialog.dismiss();


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*------------------------------------------- all Institute data set into recycler view ----------------------------------*/

    private void setRecyclerView(InsituiteResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        institutesAdapter = new InstitutesAdapter(getActivity(), result.getData(),recyclerViewClickListener);
        item = result.getData();
        recyclerView.setAdapter(institutesAdapter);
    }

    /*---------------------------------------------------- set up of search view --------------------------------------------*/

    private void searchViewSetUp() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<InsituiteDataModel> newlist = new ArrayList<>();
                for (InsituiteDataModel productList : item) {
                    String name = productList.getInstituteName().toLowerCase();
                    if (name.contains(s))
                        newlist.add(productList);
                }
                institutesAdapter.setFilter(newlist);
                return true;
            }

        });

    }
    /*---------------------------------------------- recycler view on Item Click Listner -----------------------------------------*/

    @Override
    public void onClickPosition(int position) {
        allCoursesListFragment = new AllCoursesListFragment(item.get(position).getInstituteId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, allCoursesListFragment)
                .addToBackStack(null)
                .commit();

    }


}