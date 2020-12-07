package com.h2tshop.h2tshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    final Activity context;
    final List<Cart> cartList;
    final LayoutInflater inflater;
    final DecimalFormat df = new DecimalFormat("###");

    public CartAdapter(Activity context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        ImageView imgPro;
        TextView tvNamePre,tvPricePro,tvQntPro,tvSizePro;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView==null){
            viewHolder = new ViewHolder();

            convertView =inflater.inflate(R.layout.item_cart,null);
            viewHolder.imgPro = convertView.findViewById(R.id.imgCartItem);
            viewHolder.tvNamePre = convertView.findViewById(R.id.tvNameCartItem);
            viewHolder.tvSizePro = convertView.findViewById(R.id.tvSizeCartItem);
            viewHolder.tvQntPro = convertView.findViewById(R.id.tvQntCartItem);
            viewHolder.tvPricePro = convertView.findViewById(R.id.tvPriceCartItem);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        Cart cart =cartList.get(position);

        Picasso.get().load(cart.getLink()).into(viewHolder.imgPro);
        viewHolder.tvNamePre.setText(cart.getTen());
        if (cart.getType().equals("Tui") || cart.getType().equals("PhuKien")){
            viewHolder.tvSizePro.setText("");
        }else {
            viewHolder.tvSizePro.setText(cart.getSize());
        }

        viewHolder.tvQntPro.setText(String.valueOf(cart.getQnt()));
        viewHolder.tvPricePro.setText(df.format(cart.getGia()));

        return convertView;
    }
}
