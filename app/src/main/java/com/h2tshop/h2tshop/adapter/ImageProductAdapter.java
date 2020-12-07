package com.h2tshop.h2tshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.h2tshop.h2tshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageProductAdapter extends PagerAdapter {
    private final List<String> listLink;

    public ImageProductAdapter(Context context, List<String> listLink) {
        this.listLink = listLink;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_product,container,false);
        ImageView imageView = view.findViewById(R.id.imageProduct);

        String link = listLink.get(position);

        Picasso.get().load(link).into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listLink.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
