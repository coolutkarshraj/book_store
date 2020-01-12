package com.io.bookstore.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.adapter.CoursesAdapter;
import com.io.bookstore.model.CourseModel;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {

    private ArrayList lstBook;
    private RecyclerView recyclerView;
    private CoursesAdapter coursesAdapter;

    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courses, container, false);
        recyclerView = root.findViewById(R.id.recyclerView_courses);
        setRecyclerViewData();
        bindListner();
        return root;
    }

    private void setRecyclerViewData() {

        lstBook = new ArrayList<>();

        lstBook.add(new CourseModel("NewYork Store","From outside, this place looks small, but when you come inside, it was like a shop at the front",
                R.drawable.test3));

        lstBook.add(new CourseModel("Obekan BookS","Good atmosphere Most moving books available thank you spectrawide for the hound of the baskes",
                R.drawable.bookstore2));


        lstBook.add(new CourseModel("The Jarir Store","Nice collection of items for. School and also good. Mobile accessories collections and. Cheap price",
                R.drawable.bookstore4));

        lstBook.add(new CourseModel("Al-ajeeri","Excellent bookstore full of very old books which are mostly arabic. Very diverse genres.",
                R.drawable.test4));

        lstBook.add(new CourseModel("NewYork Store","From outside, this place looks small, but when you come inside, it was like a shop at the front",
                R.drawable.bookstore2));

        lstBook.add(new CourseModel("The Jashnimal","Shop for Arabic & English books, Jarir publications books, office supplies, school supplies, arts",
                R.drawable.test1));

        lstBook.add(new CourseModel("Sakina BookModel Shop", "Best place to buy all kind of books. You will find almost everything you are searching for. Offers ",
                R.drawable.bookstore3));


        lstBook.add(new CourseModel("Better Books","Better Books is located in the heart of Salmiya, Kuwait. There are over 10,000 pre-owned books ",
                R.drawable.test5));


        lstBook.add(new CourseModel("Rakan BookModel store","Possibly the best book supply store on Qutaiba Street. Plenty of options and even some rare finds",
                R.drawable.test2));



        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        coursesAdapter = new CoursesAdapter(getActivity(),lstBook);
        recyclerView.setAdapter(coursesAdapter);
    }

    private void bindListner() {

    }



}