package com.h2tshop.h2tshop.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.model.Shoes;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ShopShoesAdapter extends BaseAdapter {
    DatabaseReference myData;

    final List<Shoes> shoesList;
    public final Activity context;
    public final LayoutInflater inflater;
    final DecimalFormat df = new DecimalFormat("###");

    public ShopShoesAdapter(Activity context , List<Shoes> shoesList ) {
        this.shoesList = shoesList;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shoesList.size();
    }

    @Override
    public Object getItem(int position) {
        return shoesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        ImageView imgProduct;
        TextView tvNameProduct,tvPriceProduct,tvSaleProduct;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder =new ViewHolder();
            convertView=inflater.inflate(R.layout.item_pro,null);
            holder.tvSaleProduct = convertView.findViewById(R.id.salePro);
            holder.tvNameProduct= convertView.findViewById(R.id.nameProSale);
            holder.tvPriceProduct = convertView.findViewById(R.id.priceProSale);
            holder.imgProduct = convertView.findViewById(R.id.imgProDetail);

            convertView.setTag(holder);
        }else{
            holder =(ViewHolder) convertView.getTag();
        }
        Shoes shoes =shoesList.get(position);
        if (shoes.getSale()>0){
            holder.tvSaleProduct.setText("(-"+df.format(shoes.getSale())+"%)");
            holder.tvPriceProduct.setText(df.format(shoes.getGia()-(shoes.getGia()*shoes.getSale()/100)));
        }else {
            holder.tvSaleProduct.setText("");
            holder.tvPriceProduct.setText(df.format(shoes.getGia()));
        }

        holder.tvNameProduct.setText(shoes.getTen());
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("GiayDa").child(shoes.getId()).child("link").child("link1").addValueEventListener(new ValueEventListener() {
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
}
