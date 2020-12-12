package com.h2tshop.h2tshop.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.model.Bill;

import java.text.DecimalFormat;
import java.util.List;

public class BillAdapter extends BaseAdapter {
    final Activity context;
    final List<Bill> billList;
    final LayoutInflater inflater;
    final DecimalFormat df = new DecimalFormat("###");

    public BillAdapter(Activity context, List<Bill> billList) {
        this.context = context;
        this.billList = billList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder{
        TextView tvIDBill,tvTotal,tvDateBill,tvStatusBill;
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView==null){
            viewHolder = new ViewHolder();

            convertView =inflater.inflate(R.layout.item_bill,null);
            viewHolder.tvIDBill = convertView.findViewById(R.id.tvIDBill);
            viewHolder.tvTotal = convertView.findViewById(R.id.tvTotalBill);
            viewHolder.tvDateBill = convertView.findViewById(R.id.tvDateBill);
            viewHolder.tvStatusBill = convertView.findViewById(R.id.tvStatusBill);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        Bill bill =billList.get(position);

        boolean status = bill.isStatus();

        viewHolder.tvIDBill.setText(bill.getIdBill());
        viewHolder.tvDateBill.setText(bill.getDateBill());
        if (status== true){
            viewHolder.tvStatusBill.setText("Đã nhận");
            viewHolder.tvStatusBill.setTextColor(Color.parseColor("#06C800"));
        }else {
            viewHolder.tvStatusBill.setText("Đang chuyển");
            viewHolder.tvStatusBill.setTextColor(Color.parseColor("#FF0000"));
        }

        viewHolder.tvTotal.setText(df.format(bill.getPrice())+" đ");
        return convertView;
    }
}
