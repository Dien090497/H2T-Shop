package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.adapter.SaleShopAdapter;
import com.h2tshop.h2tshop.model.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaleShopActivity extends AppCompatActivity {
    DatabaseReference myData;

    Toolbar toolbar;

    final List<Sale> saleList = new ArrayList<>();
    GridView gridViewSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_shop);
        setTitle("Sale");

        //   Firebase
        myData = FirebaseDatabase.getInstance().getReference();


        //      TollBar
        toolbar = findViewById(R.id.toolbarShopSale);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set gridView
        gridViewSale = findViewById(R.id.grvSale);
        setGridViewSale();
    }

    private void setGridViewSale() {

        myData.child("AllProduct").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sale sale = snapshot.getValue(Sale.class);

                if  (Objects.requireNonNull(sale).getSale()>0){
                    saleList.add(sale);
                }

                SaleShopAdapter adapter = new SaleShopAdapter(SaleShopActivity.this,saleList);
                gridViewSale.setAdapter(adapter);

                gridViewSale.setOnItemClickListener((parent, view, position, id) -> {
                    Sale sl = saleList.get(position);
                    switch (sl.getType()){
                        case "Tui":
                            Intent iBag = new Intent(SaleShopActivity.this,DetailBagActivity.class);
                            Bundle bBag = new Bundle();
                            bBag.putString("id",sl.getId());
                            iBag.putExtras(bBag);
                            startActivity(iBag);
                            break;
                        case "QuanAo":
                            Intent iClothes = new Intent(SaleShopActivity.this,DetailClothesActivity.class);
                            Bundle bClothes = new Bundle();
                            bClothes.putString("id",sl.getId());
                            iClothes.putExtras(bClothes);
                            startActivity(iClothes);
                            break;
                        case "PhuKien":
                            Intent iJewelry = new Intent(SaleShopActivity.this, DetailJewelryActivity.class);
                            Bundle bJewelry = new Bundle();
                            bJewelry.putString("id",sl.getId());
                            iJewelry.putExtras(bJewelry);
                            startActivity(iJewelry);
                            break;
                        case "GiayDa":
                            Intent iShoes = new Intent(SaleShopActivity.this,DetailShoesActivity.class);
                            Bundle bShoes = new Bundle();
                            bShoes.putString("id",sl.getId());
                            iShoes.putExtras(bShoes);
                            startActivity(iShoes);
                            break;

                    }
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
