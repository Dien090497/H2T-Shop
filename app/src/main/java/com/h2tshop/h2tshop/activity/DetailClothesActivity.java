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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.h2tshop.h2tshop.model.Cart;
import com.h2tshop.h2tshop.model.Clothes;
import com.h2tshop.h2tshop.model.WishList;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;


@SuppressWarnings("rawtypes")
public class DetailClothesActivity extends AppCompatActivity {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    Toolbar toolbar;

    TextView tenSp,giaSp,mota,qnt,sale;
    Spinner spinnerSize;
    TextView addToCart;
    ImageView imgFavorite;
    ViewPager pager;
    CircleIndicator circleIndicator;
    ImageProductAdapter imageProductAdapter;
    final List<String> linkList = new ArrayList<>();
    final ArrayList<String> sizeList = new ArrayList<>();
    final DecimalFormat df = new DecimalFormat("###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_clothes);

        setTitle(getString(R.string.title_detail));

        myData = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();



        String email = Objects.requireNonNull(myUser).getEmail();

        StringBuilder _email= new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(email).length(); i++) {
            if (String.valueOf(email.charAt(i)).equals(".")) {
                break;
            }
            _email.append(email.charAt(i));
        }

        Intent iClothes = getIntent();
        Bundle bClothes = iClothes.getExtras();

        //      TollBar
        toolbar = findViewById(R.id.toolbarDetailClothes);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set slide Image

        pager = findViewById(R.id. viewpagerDetailClothes);
        circleIndicator = findViewById(R.id.circleIndicatorClothes);

        myData.child("QuanAo").child(bClothes.getString("id")).child("link").addChildEventListener(new ChildEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                linkList.add(Objects.requireNonNull(snapshot.getValue()).toString());

                imageProductAdapter = new ImageProductAdapter(linkList);
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

        // set Favorite
        imgFavorite = findViewById(R.id.loveClothes);

        myData.child("Account").child(_email.toString()).child("wishList").child(bClothes.getString("id")).child("favorite").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    boolean like = (boolean) snapshot.getValue();
                    if (like){
                        imgFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
                    }else {
                        imgFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_unlike));
                    }
                }catch (Exception e){
                    String email = myUser.getEmail();

                    StringBuilder _email= new StringBuilder();
                    for (int i = 0; i < Objects.requireNonNull(email).length(); i++) {
                        if (String.valueOf(email.charAt(i)).equals(".")) {
                            break;
                        }
                        _email.append(email.charAt(i));
                    }

                    Intent iClothes = getIntent();
                    Bundle bClothes = iClothes.getExtras();

                    myData.child("QuanAo").child(bClothes.getString("id")).child("link").child("link1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            WishList wishList = new WishList(bClothes.getString("id"),
                                    tenSp.getText().toString(),
                                    Objects.requireNonNull(snapshot.getValue()).toString(),
                                    "QuanAo",false,Double.parseDouble(giaSp.getText().toString()));

                            myData.child("Account").child(_email.toString()).child("wishList").child(bClothes.getString("id")).setValue(wishList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

                imgFavorite.setOnClickListener(v -> {
                    String email1 = myUser.getEmail();

                    StringBuilder _email1 = new StringBuilder();
                    for (int i = 0; i < Objects.requireNonNull(email1).length(); i++) {
                        if (String.valueOf(email1.charAt(i)).equals(".")) {
                            break;
                        }
                        _email1.append(email1.charAt(i));
                    }

                    Intent iBag = getIntent();
                    Bundle bBag = iBag.getExtras();

                    boolean like = (boolean) snapshot.getValue();
                    if (like){
                        like=false;
                        myData.child("Account").child(_email1.toString()).child("wishList").child(bBag.getString("id")).child("favorite").setValue(like);
                    }else if (!like){
                        like = true;
                        myData.child("Account").child(_email1.toString()).child("wishList").child(bBag.getString("id")).child("favorite").setValue(like);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set thông tin SP


        tenSp = findViewById(R.id.tenSP);
        giaSp = findViewById(R.id.giaSP);
        mota = findViewById(R.id.mota);
        spinnerSize = findViewById(R.id.sizeClothes);
        sale = findViewById(R.id.saleClothes);

        myData.child("QuanAo").child(bClothes.getString("id")).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Clothes clothes = snapshot.getValue(Clothes.class);
                tenSp.setText(Objects.requireNonNull(clothes).getTen());
                mota.setText(clothes.getMota());
                if (clothes.getSale()>0){
                    sale.setText("(-"+df.format(clothes.getSale())+"%)");
                    giaSp.setText(df.format(clothes.getGia()-(clothes.getGia()*clothes.getSale()/100)));
                }else{
                    sale.setText("");
                    giaSp.setText(df.format(clothes.getGia()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set Spinner size

        myData.child("QuanAo").child(bClothes.getString("id")).child("size").addChildEventListener(new ChildEventListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                sizeList.add(Objects.requireNonNull(snapshot.getValue()).toString());

                ArrayAdapter arrayAdapter = new ArrayAdapter(DetailClothesActivity.this, android.R.layout.simple_spinner_item,sizeList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSize.setAdapter(arrayAdapter);

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

        qnt = findViewById(R.id.tvQNT);
        qnt.setOnClickListener(v -> {
            Dialog dialog = new Dialog(DetailClothesActivity.this);
            dialog.setContentView(R.layout.dialog_qnt);

            TextInputLayout textInputLayoutQNT = dialog.findViewById(R.id.text_input_qnt);
            TextView ok = dialog.findViewById(R.id.ok);
            TextView huy = dialog.findViewById(R.id.huy);

            ok.setOnClickListener(v12 -> {

                if (Objects.requireNonNull(textInputLayoutQNT.getEditText()).getText().toString().trim().equals("")) {
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


        // Pay
        addToCart = findViewById(R.id.addToCat);
        addToCart.setOnClickListener(v -> {
            if (Integer.parseInt(qnt.getText().toString())<1){
                Toast.makeText(DetailClothesActivity.this,"Số lượng không phù hợp",Toast.LENGTH_LONG).show();
            }else {

                myData.child("QuanAo").child(bClothes.getString("id")).child("link").child("link1").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Calendar calendar = Calendar.getInstance();

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf  = new SimpleDateFormat(getString(R.string.sdf));

                        Log.e("!!!!",sdf.format(calendar.getTime())+"");
                        Cart cart = new Cart(bClothes.getString("id"),
                                tenSp.getText().toString(),
                                spinnerSize.getSelectedItem().toString(),
                                "QuanAo",
                                Objects.requireNonNull(snapshot.getValue()).toString(),
                                sdf.format(calendar.getTime()),
                                Integer.parseInt(qnt.getText().toString()),
                                Double.parseDouble(giaSp.getText().toString()));

                        myData.child("Account").child(_email.toString()).child("Cart").child(sdf.format(calendar.getTime())).setValue(cart).addOnSuccessListener(aVoid -> {
                            Toast.makeText(DetailClothesActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
                            qnt.setText("0");
                        }).addOnFailureListener(e -> Toast.makeText(DetailClothesActivity.this, "Thêm thất bại", Toast.LENGTH_LONG).show());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}