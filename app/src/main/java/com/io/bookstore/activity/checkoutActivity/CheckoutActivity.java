package com.io.bookstore.activity.checkoutActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.io.bookstore.R;
import com.io.bookstore.adapter.AddressAdapter;
import com.io.bookstore.adapter.CartAdapter;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    Button btnLoginToDashBoard;
    ImageView iv_back;
    private ArrayList itemname;
    private RecyclerView recyclerView;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        craeteArray();
        recyclerView = findViewById(R.id.recyclerView);
        iv_back = findViewById(R.id.iv_back);
        btnLoginToDashBoard = findViewById(R.id.btnLoginToDashBoard);

        bindListner();
        setRecyclerViewData();
    }

    private void bindListner() {
        btnLoginToDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this,ProcessingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setRecyclerViewData() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(CheckoutActivity.this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        addressAdapter = new AddressAdapter(CheckoutActivity.this,itemname);
        recyclerView.setAdapter(addressAdapter);
    }

    private void craeteArray() {
        itemname = new ArrayList();
        itemname.add("Sector -14 ,Noida,Ap");
        itemname.add("Sector -19 ,Noida,Ap");
        itemname.add("Sector -22 ,Noida,Ap");
        itemname.add("Sector -28 ,Noida,Ap");
    }
}
