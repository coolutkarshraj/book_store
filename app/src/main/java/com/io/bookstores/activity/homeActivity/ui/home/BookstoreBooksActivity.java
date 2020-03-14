package com.io.bookstores.activity.homeActivity.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.io.bookstores.R;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.adapter.BookstoreBooksAdapter;
import com.io.bookstores.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookstoreBooksActivity extends AppCompatActivity {
    List<BookModel> lstBook ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore_books);


        lstBook = new ArrayList<>();
        lstBook.add(new BookModel(25,R.drawable.thevigitarian));
        lstBook.add(new BookModel(35,R.drawable.thewildrobot));
        lstBook.add(new BookModel(40,R.drawable.mariasemples));
        lstBook.add(new BookModel(20,R.drawable.themartian));
        lstBook.add(new BookModel(15,R.drawable.hediedwith));
        lstBook.add(new BookModel(25,R.drawable.thevigitarian));
        lstBook.add(new BookModel(35,R.drawable.thewildrobot));
        lstBook.add(new BookModel(40,R.drawable.mariasemples));
        lstBook.add(new BookModel(20,R.drawable.themartian));
        lstBook.add(new BookModel(15,R.drawable.hediedwith));

        lstBook.add(new BookModel(25,R.drawable.thevigitarian));
        lstBook.add(new BookModel(35,R.drawable.thewildrobot));
        lstBook.add(new BookModel(40,R.drawable.mariasemples));
        lstBook.add(new BookModel(20,R.drawable.themartian));
        lstBook.add(new BookModel(15,R.drawable.hediedwith));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        BookstoreBooksAdapter myAdapter = new BookstoreBooksAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);


    }
}
