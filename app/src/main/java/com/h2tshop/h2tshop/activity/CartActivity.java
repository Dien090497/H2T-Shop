package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.adapter.CartAdapter;
import com.h2tshop.h2tshop.model.Bill;
import com.h2tshop.h2tshop.model.Cart;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    Toolbar toolbar;

    ListView lvCart;
    EditText codeSale;
    TextView total,pay;
    final List<Cart> cartList = new ArrayList<>();
    CartAdapter cartAdapter;
    final DecimalFormat df = new DecimalFormat("###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Giỏ hàng");

        myData= FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        //      TollBar
        toolbar = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ListView
        lvCart = findViewById(R.id.lvCart);
        total = findViewById(R.id.total);

        setListViewCart();


        //  Pay


    }
    private void setListViewCart(){
        myData.child("Account").child(getNameUser()).child("Cart").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Cart cart= snapshot.getValue(Cart.class);
                cartList.add(cart);

                Log.e("ÁĐÁa",cartList.size()+"");

                setListView(cartList);

                setTotal(cartList);

                pay = findViewById(R.id.pay);
                pay.setOnClickListener(v -> {
                    if (cartList.size()!=0){
                        Dialog dialog = new Dialog(CartActivity.this);
                        dialog.setContentView(R.layout.dialog_pay);

                        TextView ok = dialog.findViewById(R.id.ok);
                        TextView huy = dialog.findViewById(R.id.huy);

                        ok.setOnClickListener(v12 -> {

                            Calendar calendar = Calendar.getInstance();

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf  = new SimpleDateFormat(getString(R.string.sdf));
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2  = new SimpleDateFormat(getString(R.string.sdf2));
                                Bill bill = new Bill(myUser.getEmail(),sdf.format(calendar.getTime()), Double.parseDouble(total.getText().toString()), false,sdf2.format(calendar.getTime()));
                                myData.child("Bill").child(bill.getIdBill()).setValue(bill).addOnSuccessListener(aVoid -> {
                                    for (Cart c : cartList){
                                        myData.child("Bill").child(bill.getIdBill()).child("product").child(c.getId()).setValue(c);

                                    }
                                    myData.child("Account").child(getNameUser()).child("Cart").setValue(null);
                                    cartList.clear();
                                    setTotal(cartList);
                                    setListView(cartList);
                                    dialog.dismiss();
                                });

                        });

                        huy.setOnClickListener(v1 -> dialog.dismiss());
                        dialog.show();


                    }

                });

                lvCart.setOnItemLongClickListener((parent, view, position, id) -> {
                    Dialog dialog = new Dialog(CartActivity.this);
                    dialog.setContentView(R.layout.dialog_delete);

                    TextView ok = dialog.findViewById(R.id.ok);
                    TextView huy = dialog.findViewById(R.id.huy);

                    ok.setOnClickListener(v -> {
                        String iCheck = cartList.get(position).getICheck();
                        myData.child("Account").child(getNameUser()).child("Cart").child(iCheck).setValue(null).addOnSuccessListener(aVoid -> {

                            cartList.remove(position);

                            setTotal(cartList);

                            setListView(cartList);

                            Toast.makeText(CartActivity.this, "Xóa thành công!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        });
                    });

                    huy.setOnClickListener(v -> dialog.dismiss());
                    dialog.show();

                    return true;
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
    private void setTotal(List<Cart> cartList){
        double _total =0;

        for (Cart cart : cartList) {
            _total = _total+cart.getGia();
        }
        total.setText(df.format(_total));
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

    private void setListView(List<Cart> cartList){
        cartAdapter = new CartAdapter(CartActivity.this,cartList);

        lvCart.setAdapter(cartAdapter);
    }

}