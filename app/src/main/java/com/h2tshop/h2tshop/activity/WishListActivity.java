package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.adapter.WishListAdapter;
import com.h2tshop.h2tshop.model.WishList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WishListActivity extends AppCompatActivity {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    Toolbar toolbar;

    final List<WishList> wishLists = new ArrayList<>();
    GridView grvWishList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        setTitle("Yêu thích");

        myData = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        //      TollBar
        toolbar = findViewById(R.id.toolbarWishList);
        setSupportActionBar(toolbar);



        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);

        grvWishList =findViewById(R.id.grvWhisList);
        setGridViewWishList();


    }

    private void setGridViewWishList() {
        myData.child("Account").child(getNameUser()).child("wishList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                WishList wishList = snapshot.getValue(WishList.class);
                if (wishList.isFavorite()){
                    wishLists.add(wishList);
                }

                WishListAdapter adapter = new WishListAdapter(WishListActivity.this,wishLists);
                grvWishList.setAdapter(adapter);

                grvWishList.setOnItemClickListener((parent, view, position, id) -> {
                    WishList wl = wishLists.get(position);


                    switch (wl.getType()){
                        case "Tui":
                            Intent iBag = new Intent(WishListActivity.this,DetailBagActivity.class);
                            Bundle bBag = new Bundle();
                            bBag.putString("id",wl.getId());
                            iBag.putExtras(bBag);
                            startActivity(iBag);
                            break;
                        case "QuanAo":
                            Intent iClothes = new Intent(WishListActivity.this,DetailClothesActivity.class);
                            Bundle bClothes = new Bundle();
                            bClothes.putString("id",wl.getId());
                            iClothes.putExtras(bClothes);
                            startActivity(iClothes);
                            break;
                        case "PhuKien":
                            Intent iJewelry = new Intent(WishListActivity.this, DetailJewelryActivity.class);
                            Bundle bJewelry = new Bundle();
                            bJewelry.putString("id",wl.getId());
                            iJewelry.putExtras(bJewelry);
                            startActivity(iJewelry);
                            break;
                        case "GiayDa":
                            Intent iShoes = new Intent(WishListActivity.this,DetailShoesActivity.class);
                            Bundle bShoes = new Bundle();
                            bShoes.putString("id",wl.getId());
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



    private String getNameUser(){
        String email = myUser.getEmail();

        StringBuilder _email= new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(email).length(); i++) {
            if (String.valueOf(email.charAt(i)).equals(".")) {
                break;
            }
            _email.append(email.charAt(i));
        }


        return _email.toString();
    }
}