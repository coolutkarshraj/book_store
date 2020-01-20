package com.io.bookstore.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.adapter.InstitutesAdapter;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.model.BookstoreModel;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.model.insituteModel.InsituiteResponseModel;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituteFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private ArrayList lstBook;
    private RecyclerView recyclerView;
    private InstitutesAdapter institutesAdapter;
    private CoursesFragment coursesFragment;
    private Button bv_browse_institute;
    private ItemClickListner itemClickListner;
    private userOnlineInfo user;
    private NewProgressBar dialog;

    public InstituteFragment() {
        // Required empty public constructor
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


    private void intializeViews(View root) {
        activity = getActivity();
        user = new userOnlineInfo();
        dialog = new NewProgressBar(getActivity());
        recyclerView = root.findViewById(R.id.recyclerView_institute);
        bv_browse_institute = (Button) root.findViewById(R.id.bv_institute_browse);
    }

    private void bindListner() {
       /*bv_browse_institute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClickListner = (ItemClickListner) getActivity();
                itemClickListner.onClick(1);

                coursesFragment= new CoursesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, coursesFragment)
                        .addToBackStack(null)
                        .commit();


            }
        }); */
    }

    @Override
    public void onClick(View view) {

    }

    private void startWorking() {
        setRecyclerViewData();
        getInstituiteList();
    }

    private void getInstituiteList() {
        if (user.isOnline(getActivity())) {
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
                            }
                            dialog.dismiss();


                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setRecyclerView(InsituiteResponseModel result) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        institutesAdapter = new InstitutesAdapter(getActivity(), result.getData());
        recyclerView.setAdapter(institutesAdapter);
    }

    private void setRecyclerViewData() {

        lstBook = new ArrayList<>();

        lstBook.add(new BookstoreModel("NewYork Store", "From outside, this place looks small, but when you come inside, it was like a shop at the front",
                R.drawable.test3));

        lstBook.add(new BookstoreModel("Obekan BookS", "Good atmosphere Most moving books available thank you spectrawide for the hound of the baskes",
                R.drawable.bookstore2));


        lstBook.add(new BookstoreModel("The Jarir Store", "Nice collection of items for. School and also good. Mobile accessories collections and. Cheap price",
                R.drawable.bookstore4));

        lstBook.add(new BookstoreModel("Al-ajeeri", "Excellent bookstore full of very old books which are mostly arabic. Very diverse genres.",
                R.drawable.test4));

        lstBook.add(new BookstoreModel("NewYork Store", "From outside, this place looks small, but when you come inside, it was like a shop at the front",
                R.drawable.bookstore2));

        lstBook.add(new BookstoreModel("The Jashnimal", "Shop for Arabic & English books, Jarir publications books, office supplies, school supplies, arts",
                R.drawable.test1));

        lstBook.add(new BookstoreModel("Sakina BookModel Shop", "Best place to buy all kind of books. You will find almost everything you are searching for. Offers ",
                R.drawable.bookstore3));


        lstBook.add(new BookstoreModel("Better Books", "Better Books is located in the heart of Salmiya, Kuwait. There are over 10,000 pre-owned books ",
                R.drawable.test5));


        lstBook.add(new BookstoreModel("Rakan BookModel store", "Possibly the best book supply store on Qutaiba Street. Plenty of options and even some rare finds",
                R.drawable.test2));



    }


}