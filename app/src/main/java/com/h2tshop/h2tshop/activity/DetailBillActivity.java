package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.adapter.CartAdapter;
import com.h2tshop.h2tshop.model.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailBillActivity extends AppCompatActivity {
    DatabaseReference myData;

    Toolbar toolbar;

    ListView listView;

    List<Cart> cartList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);
        setTitle(getString(R.string.detail_bill));

        //      TollBar
        toolbar = findViewById(R.id.toolbarDetailBill);
        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);


        // set ListView
        myData = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.lvDetailBill);
        setListView();


    }

    private void setListView(){
        myData.child("Bill").child(getIdBill()).child("product").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cart c = snapshot.getValue(Cart.class);

                cartList.add(c);

                CartAdapter cartAdapter = new CartAdapter(DetailBillActivity.this,cartList);
                listView.setAdapter(cartAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getIdBill(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        return bundle.getString("idBill");
    }
}