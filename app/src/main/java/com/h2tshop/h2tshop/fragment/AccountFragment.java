 package com.h2tshop.h2tshop.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.activity.EditInfoActivity;
import com.h2tshop.h2tshop.activity.WishListActivity;
import com.h2tshop.h2tshop.model.Account;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h2tshop.h2tshop.model.WishList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

 public class AccountFragment extends Fragment {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    ImageView imgAvatar;
    TextView tvTen,tvThanhPho,editInfo,tvWishList;
    final List<WishList> wishLists = new ArrayList<>();
    private View viewRoot;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot =inflater.inflate(R.layout.fragment_account,container,false);
        intView();
        return viewRoot;
    }

    private void intView() {
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        myData = FirebaseDatabase.getInstance().getReference();

        // set Avatar
        imgAvatar = viewRoot.findViewById(R.id.imgAvatar);
        setAvatarAccount();


        //set Info
        tvTen = viewRoot.findViewById(R.id.tvTen);
        tvThanhPho = viewRoot.findViewById(R.id.tvThanhPho);
        setInfoAccount();

//        WishList
        tvWishList =viewRoot.findViewById(R.id.wishList);
        setWishList();

        tvWishList.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WishListActivity.class);
            startActivity(intent);
        });

//  Sửa thông tin
        editInfo= viewRoot.findViewById(R.id.editInfo);
        editInfo.setOnClickListener(v -> {
            Intent iEditInfo = new Intent(getActivity(), EditInfoActivity.class);
            startActivity(iEditInfo);
        });


    }

    private void setWishList(){
        myData.child("Account").child(getNameUser()).child("wishList").addChildEventListener(new ChildEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                WishList wl = snapshot.getValue(WishList.class);

                if (wl.isFavorite()){
                    wishLists.add(wl);
                }


                tvWishList.setText("YÊU THÍCH ("+wishLists.size()+")");
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

    private void setAvatarAccount(){
        myData.child("Account").child(getNameUser()).child("link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(Objects.requireNonNull(snapshot.getValue()).toString()).into(imgAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setInfoAccount(){
        myData.child("Account").child(getNameUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);

                tvTen.setText(Objects.requireNonNull(account).getTen());
                tvThanhPho.setText(account.getThanhPho());

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
