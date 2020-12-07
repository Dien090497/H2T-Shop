 package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.adapter.ImageProductAdapter;
import com.h2tshop.h2tshop.model.Bag;
import com.h2tshop.h2tshop.model.Cart;
import com.h2tshop.h2tshop.model.WishList;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;

 public class DetailBagActivity extends AppCompatActivity {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    Toolbar toolbar;

    ImageView imgFavorite;
    TextView tenSp,giaSp,mota,size,qnt,sale;
    TextView addToCart;
    ViewPager pager;
    CircleIndicator circleIndicator;
    ImageProductAdapter imageProductAdapter;
    final List<String> linkList = new ArrayList<>();
    final DecimalFormat df = new DecimalFormat("###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bag);
        setTitle(getString(R.string.title_detail));

        myData = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        imgFavorite = findViewById(R.id.love);



        //      TollBar
        toolbar = findViewById(R.id.toolbarDetailBag);
        setToolbar();

        // Set slide Image

        pager = findViewById(R.id. viewpagerDetailBag);
        circleIndicator = findViewById(R.id.circleIndicatorBag);

        setImageSlide();


        // set Favorite

        setFavoriteProduct();


        // Set thông tin SP

        tenSp = findViewById(R.id.tenSP);
        giaSp = findViewById(R.id.giaSP);
        mota = findViewById(R.id.mota);
        size = findViewById(R.id.sizeBag);
        sale = findViewById(R.id.saleBag);

        setDetailProduct();


        // set QNT
        qnt = findViewById(R.id.tvQNT);
        setQNT();

        // Add to Cart

        addToCart = findViewById(R.id.addToCatt);
        addProductToCart();

    }

     private void addProductToCart() {
         addToCart.setOnClickListener(v -> {
             if (Integer.parseInt(qnt.getText().toString())<1){
                 Toast.makeText(DetailBagActivity.this,"Số lượng không phù hợp",Toast.LENGTH_LONG).show();
             }else {

                 myData.child("Tui").child(getIDProduct()).child("link").child("link1").addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         Calendar calendar = Calendar.getInstance();

                         @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf  = new SimpleDateFormat(getString(R.string.sdf));

                         Log.e("!!!!",sdf.format(calendar.getTime())+"");
                         Cart cart = new Cart(getIDProduct(),
                                 tenSp.getText().toString(),
                                 size.getText().toString(),
                                 "Tui",
                                 snapshot.getValue().toString(),
                                 sdf.format(calendar.getTime()),
                                 Integer.parseInt(qnt.getText().toString()),
                                 Double.parseDouble(giaSp.getText().toString()));

                         myData.child("Account").child(getUserName()).child("Cart").child(sdf.format(calendar.getTime())).setValue(cart).addOnSuccessListener(aVoid -> {
                             Toast.makeText(DetailBagActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
                             qnt.setText("0");
                         }).addOnFailureListener(e -> Toast.makeText(DetailBagActivity.this, "Thêm thất bại", Toast.LENGTH_LONG).show());
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
             }
         });
    }

     private void setQNT() {
         qnt.setOnClickListener(v -> {
             Dialog dialog = new Dialog(DetailBagActivity.this);
             dialog.setContentView(R.layout.dialog_qnt);

             TextInputLayout textInputLayoutQNT = dialog.findViewById(R.id.text_input_qnt);
             TextView ok = dialog.findViewById(R.id.ok);
             TextView huy = dialog.findViewById(R.id.huy);

             ok.setOnClickListener(v12 -> {

                 if (textInputLayoutQNT.getEditText().getText().toString().trim().equals("")) {
                     textInputLayoutQNT.setError("Số lượng trống!");
                 } else {
                     String _qnt = textInputLayoutQNT.getEditText().getText().toString().trim();
                     qnt.setText(_qnt);
                     dialog.dismiss();
                 }
             });

             huy.setOnClickListener(v1 -> dialog.dismiss());

             dialog.show();
         });
    }

     private void setDetailProduct() {
         myData.child("Tui").child(getIDProduct()).addValueEventListener(new ValueEventListener() {
             @SuppressLint("SetTextI18n")
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 Bag bag = snapshot.getValue(Bag.class);
                 tenSp.setText(bag.getTen());
                 mota.setText(bag.getMota());
                 size.setText(bag.getSize());
                 if (bag.getSale()>0){
                     sale.setText("(-"+df.format(bag.getSale())+"%)");
                     giaSp.setText(df.format(bag.getGia()-(bag.getGia()*bag.getSale()/100)));
                 }else{
                     sale.setText("");
                     giaSp.setText(df.format(bag.getGia()));
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }

     private void setFavoriteProduct() {
         myData.child("Account").child(getUserName()).child("wishList").child(getIDProduct()).child("favorite").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 try {
                     boolean like = (boolean) snapshot.getValue();
                     if (like==true){
                         imgFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
                     }else {
                         imgFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_unlike));
                     }
                 }catch (Exception e){


                     myData.child("Tui").child(getIDProduct()).child("link").child("link1").addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             WishList wishList = new WishList(getIDProduct(),
                                     tenSp.getText().toString(),
                                     snapshot.getValue().toString(),
                                     "Tui",false,Double.parseDouble(giaSp.getText().toString()));

                             myData.child("Account").child(getUserName()).child("wishList").child(getIDProduct()).setValue(wishList);
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });


                 }

                 imgFavorite.setOnClickListener(v -> {

                     boolean like = (boolean) snapshot.getValue();
                     if (like==true){
                         like=false;
                         myData.child("Account").child(getUserName()).child("wishList").child(getIDProduct()).child("favorite").setValue(like);
                     }else if (like==false){
                         like = true;
                         myData.child("Account").child(getUserName()).child("wishList").child(getIDProduct()).child("favorite").setValue(like);
                     }
                 });
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }

     private void setImageSlide() {
         myData.child("Tui").child(getIDProduct()).child("link").addChildEventListener(new ChildEventListener() {
             @SuppressLint("ResourceAsColor")
             @Override
             public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 linkList.add(snapshot.getValue().toString());

                 imageProductAdapter = new ImageProductAdapter(DetailBagActivity.this,linkList);
                 pager.setAdapter(imageProductAdapter);

                 circleIndicator.setViewPager(pager);
                 imageProductAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

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

     private void setToolbar() {
         setSupportActionBar(toolbar);

         Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
         Drawable drawable   = getResources().getDrawable(R.drawable.back);
         getSupportActionBar().setHomeAsUpIndicator(drawable);
         getSupportActionBar().setHomeButtonEnabled(true);
    }

     private String getUserName(){
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

    private String getIDProduct(){
        Intent iBag = getIntent();
        Bundle bBag = iBag.getExtras();

        return bBag.getString("id");
    }


 }