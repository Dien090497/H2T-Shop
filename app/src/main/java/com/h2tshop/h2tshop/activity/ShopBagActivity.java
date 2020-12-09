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

import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.adapter.ShopBagAdapter;
import com.h2tshop.h2tshop.model.Bag;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopBagActivity extends AppCompatActivity {
    DatabaseReference myData;

    Toolbar toolbar;

    final List<Bag> bagList = new ArrayList<>();
    GridView gridViewTui;
    TextView openJewelryShop,openShoesShop,openClothesShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_bag);
        setTitle("Cửa hàng túi");
        //   Firebase
        myData = FirebaseDatabase.getInstance().getReference();


        //      TollBar
        toolbar = findViewById(R.id.toolbarShopTui);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);



        // Set gridView
        gridViewTui = findViewById(R.id.grvTui);
        setGridViewBag();


        // open other Shop
        anhXa();
        openJewelryShop.setOnClickListener(v -> {
            Intent iBag = new Intent(ShopBagActivity.this, ShopJewelryActivity.class);
            startActivity(iBag);
        });

        openClothesShop.setOnClickListener(v -> {
            Intent iClothes = new Intent(ShopBagActivity.this, ShopClothesActivity.class);
            startActivity(iClothes);
        });
        openShoesShop.setOnClickListener(v -> {
            Intent iShoes = new Intent(ShopBagActivity.this,ShopShoesActivity.class);
            startActivity(iShoes);
        });

    }

    private void anhXa() {
        openJewelryShop = findViewById(R.id.phuKien);
        openShoesShop = findViewById(R.id.giay);
        openClothesShop = findViewById(R.id.quanAo);
    }

    private void setGridViewBag(){
        myData.child("Tui").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Bag bag = snapshot.getValue(Bag.class);
                bagList.add(bag);

                ShopBagAdapter adapter = new ShopBagAdapter(ShopBagActivity.this,bagList);
                gridViewTui.setAdapter(adapter);

                gridViewTui.setOnItemClickListener((parent, view, position, id) -> {
                    Intent iBag = new Intent(ShopBagActivity.this,DetailBagActivity.class);
                    Bundle bBag = new Bundle();
                    bBag.putString("id",bagList.get(position).getId());
                    iBag.putExtras(bBag);
                    startActivity(iBag);
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