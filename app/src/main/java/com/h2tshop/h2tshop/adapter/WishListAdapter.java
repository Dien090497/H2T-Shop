package com.h2tshop.h2tshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.model.WishList;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class WishListAdapter extends BaseAdapter {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    final List<WishList> wishLists;
    public final Activity context;
    public final LayoutInflater inflater;
    final DecimalFormat df = new DecimalFormat("###");

    public WishListAdapter(Activity context , List<WishList> wishLists ) {
        this.wishLists = wishLists;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wishLists.size();
    }

    @Override
    public Object getItem(int position) {
        return wishLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        ImageView imgProduct;
        TextView tvTenProduct,tvGiaProduct;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder =new ViewHolder();
            convertView=inflater.inflate(R.layout.item_wish_list,null);
            holder.tvTenProduct= convertView.findViewById(R.id.namePro);
            holder.tvGiaProduct = convertView.findViewById(R.id.pricePro);
            holder.imgProduct = convertView.findViewById(R.id.imgPro);

            convertView.setTag(holder);
        }else{
            holder =(ViewHolder) convertView.getTag();
        }
        WishList wishList =wishLists.get(position);

        holder.tvTenProduct.setText(wishList.getTen());
        holder.tvGiaProduct.setText(df.format(wishList.getGia()));
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("Account").child(getNameUser()).child("wishList").child(wishList.getId()).child("link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(snapshot.getValue().toString()).into(holder.imgProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return convertView;
    }

    private String getNameUser(){
        myAuth = FirebaseAuth.getInstance();
        myUser= myAuth.getCurrentUser();

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
