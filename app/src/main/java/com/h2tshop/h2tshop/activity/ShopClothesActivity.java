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
import com.h2tshop.h2tshop.adapter.ShopClothesAdapter;
import com.h2tshop.h2tshop.model.Clothes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopClothesActivity extends AppCompatActivity {
    DatabaseReference myData;

    Toolbar toolbar;

    GridView gridViewClothes;
    final List<Clothes> clothesList = new ArrayList<>();
    TextView openBagShop,openShoesShop,openJewelryShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_clothes);
        setTitle("Cửa hàng quần áo");

        myData = FirebaseDatabase.getInstance().getReference();


        //      TollBar
        toolbar = findViewById(R.id.toolbarShopClothes);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);


        // set ListView

        gridViewClothes = findViewById(R.id.grvClothes);
        setGridViewClothes();

        // Open other Shop
        anhXa();
        openBagShop.setOnClickListener(v -> {
            Intent iBag = new Intent(ShopClothesActivity.this,ShopBagActivity.class);
            startActivity(iBag);
        });

        openJewelryShop.setOnClickListener(v -> {
            Intent iClothes = new Intent(ShopClothesActivity.this, ShopClothesActivity.class);
            startActivity(iClothes);
        });
        openShoesShop.setOnClickListener(v -> {
            Intent iShoes = new Intent(ShopClothesActivity.this,ShopShoesActivity.class);
            startActivity(iShoes);
        });

    }

    private void anhXa() {
        openBagShop = findViewById(R.id.tui);
        openShoesShop = findViewById(R.id.giay);
        openJewelryShop = findViewById(R.id.phuKien);
    }

    private void setGridViewClothes() {
        myData.child("QuanAo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Clothes clothes = snapshot.getValue(Clothes.class);
                clothesList.add(clothes);

                ShopClothesAdapter adapter = new ShopClothesAdapter(ShopClothesActivity.this,clothesList);
                gridViewClothes.setAdapter(adapter);

                gridViewClothes.setOnItemClickListener((parent, view, position, id) -> {
                    Intent iClothes = new Intent(ShopClothesActivity.this,DetailClothesActivity.class);
                    Bundle bClothes = new Bundle();
                    bClothes.putString("id",clothesList.get(position).getId());
                    iClothes.putExtras(bClothes);
                    startActivity(iClothes);
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
}
