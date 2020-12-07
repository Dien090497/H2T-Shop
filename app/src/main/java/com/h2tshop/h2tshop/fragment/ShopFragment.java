package com.h2tshop.h2tshop.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import com.h2tshop.h2tshop.activity.DetailBagActivity;
import com.h2tshop.h2tshop.activity.DetailClothesActivity;
import com.h2tshop.h2tshop.activity.DetailJewelryActivity;
import com.h2tshop.h2tshop.activity.DetailShoesActivity;
import com.h2tshop.h2tshop.activity.SaleShopActivity;
import com.h2tshop.h2tshop.activity.ShopBagActivity;
import com.h2tshop.h2tshop.activity.ShopClothesActivity;
import com.h2tshop.h2tshop.activity.ShopJewelryActivity;
import com.h2tshop.h2tshop.activity.ShopShoesActivity;
import com.h2tshop.h2tshop.activity.WishListActivity;
import com.h2tshop.h2tshop.adapter.AutoCompleteProductAdapter;
import com.h2tshop.h2tshop.model.AllProduct;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {
    DatabaseReference myData;

    private View viewRoot;
    AutoCompleteTextView autoCompleteTextView;
    TextView openJewelryShop, openBagShop, openClothesShop, openShoesShop, openSaleShop, numberProduct;
    final List<AllProduct> allProductList =new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot =inflater.inflate(R.layout.fragment_shop,container,false);
        intView();
        return viewRoot;
    }

    private void intView() {
        myData = FirebaseDatabase.getInstance().getReference();

        openBagShop = viewRoot.findViewById(R.id.openBagShop);
        openClothesShop = viewRoot.findViewById(R.id.openClothesShop);
        openShoesShop = viewRoot.findViewById(R.id.openShoesShop);
        openJewelryShop = viewRoot.findViewById(R.id.openJewelryShop);
        openSaleShop = viewRoot.findViewById(R.id.openSaleShop);
        autoCompleteTextView = viewRoot.findViewById(R.id.autoComplete);

        openBagShop.setOnClickListener(v -> {
            Intent iBag = new Intent(getActivity(), ShopBagActivity.class);
            startActivity(iBag);
        });

        openClothesShop.setOnClickListener(v -> {
            Intent iClothes = new Intent(getActivity(), ShopClothesActivity.class);
            startActivity(iClothes);
        });

        openShoesShop.setOnClickListener(v -> {
            Intent iShoes = new Intent(getActivity(), ShopShoesActivity.class);
            startActivity(iShoes);
        });

        openJewelryShop.setOnClickListener(v -> {
            Intent iJewelry = new Intent(getActivity(), ShopJewelryActivity.class);
            startActivity(iJewelry);
        });

        openSaleShop.setOnClickListener(v -> {
            Intent iSale = new Intent(getActivity(), SaleShopActivity.class);
            startActivity(iSale);
        });
        //set Number Product
        numberProduct =viewRoot.findViewById(R.id.tvAllQnt);
        setNumberProduct();

        //setAutoCompleteTextView
        setAutoCompleteTextView();

    }

    private void setNumberProduct(){

        myData.child("AllProduct").addChildEventListener(new ChildEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AllProduct allProduct = snapshot.getValue(AllProduct.class);

                allProductList.add(allProduct);

                numberProduct.setText(allProductList.size()+" sản sản phẩm");
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

    private void setAutoCompleteTextView(){
        myData.child("AllProduct").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AllProduct allProduct = snapshot.getValue(AllProduct.class);

                allProductList.add(allProduct);

                AutoCompleteProductAdapter  adapter = new AutoCompleteProductAdapter(getActivity(),allProductList);

                autoCompleteTextView.setAdapter(adapter);

                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AllProduct product = allProductList.get(position);

                        switch (product.getType()){
                            case "Tui":
                                Intent iBag = new Intent(getActivity(), DetailBagActivity.class);
                                Bundle bBag = new Bundle();
                                bBag.putString("id",product.getId());
                                iBag.putExtras(bBag);
                                startActivity(iBag);
                                break;
                            case "QuanAo":
                                Intent iClothes = new Intent(getActivity(), DetailClothesActivity.class);
                                Bundle bClothes = new Bundle();
                                bClothes.putString("id",product.getId());
                                iClothes.putExtras(bClothes);
                                startActivity(iClothes);
                                break;
                            case "PhuKien":
                                Intent iJewelry = new Intent(getActivity(), DetailJewelryActivity.class);
                                Bundle bJewelry = new Bundle();
                                bJewelry.putString("id",product.getId());
                                iJewelry.putExtras(bJewelry);
                                startActivity(iJewelry);
                                break;
                            case "GiayDa":
                                Intent iShoes = new Intent(getActivity(), DetailShoesActivity.class);
                                Bundle bShoes = new Bundle();
                                bShoes.putString("id",product.getId());
                                iShoes.putExtras(bShoes);
                                startActivity(iShoes);
                                break;

                        }
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
