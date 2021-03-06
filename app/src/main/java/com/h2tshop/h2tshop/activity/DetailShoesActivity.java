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
import com.h2tshop.h2tshop.model.Shoes;
import com.h2tshop.h2tshop.model.WishList;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;

public class DetailShoesActivity extends AppCompatActivity {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    Toolbar toolbar;
    ViewPager pager;
    CircleIndicator circleIndicator;

    ImageView imgFavorite;
    ImageProductAdapter imageProductAdapter;
    final List<String> linkList = new ArrayList<>();
    TextView tenSp,giaSp,mota,sale,qnt;
    Spinner spinnerSize;
    TextView addToCart;
    final ArrayList<String> sizeList = new ArrayList<>();
    final DecimalFormat df = new DecimalFormat("###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shoes);
        setTitle(getString(R.string.title_detail));

        //Firebase
        myData = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();


        //      TollBar
        toolbar = findViewById(R.id.toolbarDetailShoes);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set slide Image

        pager = findViewById(R.id. viewpagerDetailShoes);
        circleIndicator = findViewById(R.id.circleIndicatorShoes);
        slideImage();

        // set Favorite
        imgFavorite = findViewById(R.id.loveShoes);
        setImgFavorite();

        // Set thông tin SP

        tenSp = findViewById(R.id.tenSP);
        giaSp = findViewById(R.id.giaSP);
        mota = findViewById(R.id.mota);
        sale = findViewById(R.id.saleShoes);
        setInfo();

        //set spinner Size

        spinnerSize = findViewById(R.id.sizeShoes);
        setSpinnerSize();

        // set QNT

        qnt = findViewById(R.id.tvQNTShoes);
        setQNT();

        // Oder
        addToCart = findViewById(R.id.addToCartShoes);
        setOder();



    }

    private void setOder() {
        String _email= getNameUser();
        Intent iShoes = getIntent();
        Bundle bShoes = iShoes.getExtras();

        addToCart.setOnClickListener(v -> {
            if (Integer.parseInt(qnt.getText().toString())<1){
                Toast.makeText(DetailShoesActivity.this,"Số lượng không phù hợp",Toast.LENGTH_LONG).show();
            }else {

                myData.child("GiayDa").child(bShoes.getString("id")).child("link").child("link1").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Calendar calendar = Calendar.getInstance();

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf  = new SimpleDateFormat(getString(R.string.sdf));

                        Log.e("!!!!",sdf.format(calendar.getTime())+"");
                        Cart cart = new Cart(bShoes.getString("id"),
                                tenSp.getText().toString(),
                                spinnerSize.getSelectedItem().toString(),
                                "GiayDa",
                                Objects.requireNonNull(snapshot.getValue()).toString(),
                                sdf.format(calendar.getTime()),
                                Integer.parseInt(qnt.getText().toString()),
                                Double.parseDouble(giaSp.getText().toString()));

                        myData.child("Account").child(_email).child("Cart").child(sdf.format(calendar.getTime())).setValue(cart).addOnSuccessListener(aVoid -> {
                            Toast.makeText(DetailShoesActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
                            qnt.setText("0");
                        }).addOnFailureListener(e -> Toast.makeText(DetailShoesActivity.this, "Thêm thất bại", Toast.LENGTH_LONG).show());
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
            Dialog dialog = new Dialog(DetailShoesActivity.this);
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

    private  void slideImage(){

        Intent iShoes = getIntent();
        Bundle bShoes = iShoes.getExtras();
        myData.child("GiayDa").child(bShoes.getString("id")).child("link").addChildEventListener(new ChildEventListener() {
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
    }


    private  void setImgFavorite(){

        String _email= getNameUser();
        Intent iShoes = getIntent();
        Bundle bShoes = iShoes.getExtras();

        myData.child("Account").child(_email).child("wishList").child(bShoes.getString("id")).child("favorite").addValueEventListener(new ValueEventListener() {
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

                    myData.child("GiayDa").child(bShoes.getString("id")).child("link").child("link1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            WishList wishList = new WishList(bShoes.getString("id"),
                                    tenSp.getText().toString(),
                                    Objects.requireNonNull(snapshot.getValue()).toString(),
                                    "GiayDa",false,Double.parseDouble(giaSp.getText().toString()));

                            myData.child("Account").child(_email).child("wishList").child(bShoes.getString("id")).setValue(wishList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

                imgFavorite.setOnClickListener(v -> {

                    boolean like = (boolean) snapshot.getValue();
                    if (like){
                        like=false;
                        myData.child("Account").child(_email).child("wishList").child(bShoes.getString("id")).child("favorite").setValue(like);
                    }else if (!like){
                        like = true;
                        myData.child("Account").child(_email).child("wishList").child(bShoes.getString("id")).child("favorite").setValue(like);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private  void setInfo(){
        Intent iShoes = getIntent();
        Bundle bShoes = iShoes.getExtras();

        myData.child("GiayDa").child(bShoes.getString("id")).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shoes shoes = snapshot.getValue(Shoes.class);
                tenSp.setText(Objects.requireNonNull(shoes).getTen());
                if (shoes.getSale()>0){
                    sale.setText("(-"+df.format(shoes.getSale())+"%)");
                    giaSp.setText(df.format(shoes.getGia()-(shoes.getGia()*shoes.getSale()/100)));
                }else{
                    sale.setText("");
                    giaSp.setText(df.format(shoes.getGia()));
                }
                mota.setText(shoes.getMota());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setSpinnerSize(){
        Intent iShoes = getIntent();
        Bundle bShoes = iShoes.getExtras();

        myData.child("GiayDa").child(bShoes.getString("id")).child("size").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                sizeList.add(Objects.requireNonNull(snapshot.getValue()).toString());

                ArrayAdapter arrayAdapter = new ArrayAdapter(DetailShoesActivity.this, android.R.layout.simple_spinner_item,sizeList);
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
    }
}