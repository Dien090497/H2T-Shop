package com.h2tshop.h2tshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.model.AllProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteProductAdapter extends ArrayAdapter<AllProduct> {
    private List<AllProduct> productListAll;

    public AutoCompleteProductAdapter(@NonNull Context context, @NonNull List<AllProduct> productList) {
        super(context, 0, productList);
        productListAll  = new ArrayList<>(productList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView== null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_find_product,parent,false);
        }

        TextView namePro = convertView.findViewById(R.id.tvNameProductFind);

        AllProduct allProduct = getItem(position);

        if (allProduct!=null){
            namePro.setText(allProduct.getTen());
        }

        return convertView;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results  = new FilterResults();
            List<AllProduct> list = new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                list.addAll(productListAll);
            }else {
                String filterPatten = constraint.toString().toLowerCase().trim();

                for ( AllProduct allProduct : productListAll ){
                    if (allProduct.getTen().toLowerCase().contains(filterPatten)){
                        list.add(allProduct);
                    }
                }
            }
            results.values = list;
            results.count = list.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue){
            return ((AllProduct) resultValue).getTen();

        }
    };
}
