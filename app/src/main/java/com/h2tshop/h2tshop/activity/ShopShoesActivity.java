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
import com.h2tshop.h2tshop.adapter.ShopShoesAdapter;
import com.h2tshop.h2tshop.model.Shoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopShoesActivity extends AppCompatActivity {
    DatabaseReference myData;

    Toolbar toolbar;

    GridView gridViewShoes;
    final List<Shoes> shoesList = new ArrayList<>();
    TextView openBagShop,openJewelryShop,openClothesShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_shoes);
        setTitle("Cửa hang giầy");

        myData = FirebaseDatabase.getInstance().getReference();

        // TollBar
        toolbar = findViewById(R.id.toolbarShopShoes);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Set GridView

        gridViewShoes = findViewById(R.id.grvShoes);
        setGridViewShoes();


        // Open other Shop
        anhXa();
        openBagShop.setOnClickListener(v -> {
            Intent iBag = new Intent(ShopShoesActivity.this,ShopBagActivity.class);
            startActivity(iBag);
        });

        openClothesShop.setOnClickListener(v -> {
            Intent iClothes = new Intent(ShopShoesActivity.this, ShopClothesActivity.class);
            startActivity(iClothes);
        });
        openJewelryShop.setOnClickListener(v -> {
            Intent iShoes = new Intent(ShopShoesActivity.this, ShopJewelryActivity.class);
            startActivity(iShoes);
        });



    }

    private void setGridViewShoes(){
        myData.child("GiayDa").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Shoes shoes = snapshot.getValue(Shoes.class);
                shoesList.add(shoes);

                ShopShoesAdapter adapter = new ShopShoesAdapter(ShopShoesActivity.this,shoesList);
                gridViewShoes.setAdapter(adapter);

                gridViewShoes.setOnItemClickListener((parent, view, position, id) -> {
                    Intent iShoes = new Intent(ShopShoesActivity.this,DetailShoesActivity.class);
                    Bundle bShoes = new Bundle();
                    bShoes.putString("id",shoesList.get(position).getId());
                    iShoes.putExtras(bShoes);
                    startActivity(iShoes);
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
        openJewelryShop = findViewById(R.id.phuKien);
        openClothesShop = findViewById(R.id.quanAo);
    }
}