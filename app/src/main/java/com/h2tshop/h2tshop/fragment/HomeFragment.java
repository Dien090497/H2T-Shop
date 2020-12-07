package com.h2tshop.h2tshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.activity.SaleShopActivity;
import com.h2tshop.h2tshop.activity.ShopBagActivity;
import com.h2tshop.h2tshop.activity.ShopClothesActivity;
import com.h2tshop.h2tshop.activity.ShopJewelryActivity;
import com.h2tshop.h2tshop.activity.ShopShoesActivity;
import com.h2tshop.h2tshop.model.Sale;

public class HomeFragment extends Fragment {
    DatabaseReference myData;
    
    private View viewRoot;

    TextView openPhuKien, openTui, openQuanAo, openGiay, openShop;
    LinearLayout openSale;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot =inflater.inflate(R.layout.fragment_home,container,false);
        intView();
        return viewRoot;
    }

    private void intView() {

        openPhuKien = viewRoot.findViewById(R.id.phuKien);
        openTui = viewRoot.findViewById(R.id.tui);
        openQuanAo = viewRoot.findViewById(R.id.quanAo);
        openGiay = viewRoot.findViewById(R.id.giay);
        openShop = viewRoot.findViewById(R.id.shop);
        openSale = viewRoot.findViewById(R.id.sale);


        openPhuKien.setOnClickListener(v -> {

        });

        openTui.setOnClickListener(v -> {
            Intent iTui = new Intent(getActivity(), ShopBagActivity.class);
            startActivity(iTui);
        });

        openQuanAo.setOnClickListener(v -> {
            Intent iClothes = new Intent(getActivity(), ShopClothesActivity.class);
            startActivity(iClothes);
        });

        openGiay.setOnClickListener(v -> {
            Intent iShoes = new Intent(getActivity(), ShopShoesActivity.class);
            startActivity(iShoes);
        });

        openPhuKien.setOnClickListener(v -> {
            Intent iJewelry = new Intent(getActivity(), ShopJewelryActivity.class);
            startActivity(iJewelry);
        });

        openSale.setOnClickListener(v -> {
            addSale();

            Intent iSale = new Intent(getActivity(), SaleShopActivity.class);
            startActivity(iSale);
        });

    }

    private void addSale(){
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("AllProduct").setValue(null);

        myData.child("Tui").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sale sale = snapshot.getValue(Sale.class);
                sale.setType("Tui");
                myData.child("AllProduct").child(sale.getId()).setValue(sale);

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


        myData.child("QuanAo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sale sale = snapshot.getValue(Sale.class);
                sale.setType("QuanAo");
                myData.child("AllProduct").child(sale.getId()).setValue(sale);
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

        myData.child("PhuKien").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sale sale = snapshot.getValue(Sale.class);
                sale.setType("PhuKien");
                myData.child("AllProduct").child(sale.getId()).setValue(sale);
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

        myData.child("GiayDa").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sale sale = snapshot.getValue(Sale.class);
                sale.setType("GiayDa");
                myData.child("AllProduct").child(sale.getId()).setValue(sale);
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
