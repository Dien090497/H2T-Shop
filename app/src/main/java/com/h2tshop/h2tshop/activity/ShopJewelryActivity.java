package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.adapter.ShopJewelryAdapter;
import com.h2tshop.h2tshop.model.Jewelry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopJewelryActivity extends AppCompatActivity {
    DatabaseReference myData;

    Toolbar toolbar;

    final List<Jewelry> jewelryList = new ArrayList<>();
    GridView gridViewJewelry;

    TextView openBagShop,openShoesShop,openClothesShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_jewelry);
        setTitle("Cửa hàng phụ kiện");

        //   Firebase
        myData = FirebaseDatabase.getInstance().getReference();


        //      TollBar
        toolbar = findViewById(R.id.toolbarShopJewelry);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set gridView
        gridViewJewelry = findViewById(R.id.grvJewelry);
        setGridViewJewelry();

        // Open other Shop
        anhXa();
        openBagShop.setOnClickListener(v -> {
            Intent iBag = new Intent(ShopJewelryActivity.this,ShopBagActivity.class);
            startActivity(iBag);
        });

        openClothesShop.setOnClickListener(v -> {
            Intent iClothes = new Intent(ShopJewelryActivity.this, ShopClothesActivity.class);
            startActivity(iClothes);
        });
        openShoesShop.setOnClickListener(v -> {
            Intent iShoes = new Intent(ShopJewelryActivity.this,ShopShoesActivity.class);
            startActivity(iShoes);
        });

    }


    private void setGridViewJewelry(){
        myData.child("PhuKien").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Jewelry jewelry = snapshot.getValue(Jewelry.class);
                jewelryList.add(jewelry);

                ShopJewelryAdapter adapter = new ShopJewelryAdapter(ShopJewelryActivity.this,jewelryList);
                gridViewJewelry.setAdapter(adapter);

                gridViewJewelry.setOnItemClickListener((parent, view, position, id) -> {
                    Intent iJewelry = new Intent(ShopJewelryActivity.this, DetailJewelryActivity.class);
                    Bundle bJewelry = new Bundle();
                    bJewelry.putString("id",jewelryList.get(position).getId());
                    iJewelry.putExtras(bJewelry);
                    startActivity(iJewelry);
                });

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

    private void anhXa(){
        openBagShop = findViewById(R.id.tui);
        openShoesShop = findViewById(R.id.giay);
        openClothesShop = findViewById(R.id.quanAo);
    }
}