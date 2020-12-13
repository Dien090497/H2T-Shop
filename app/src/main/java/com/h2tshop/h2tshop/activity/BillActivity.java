package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.h2tshop.h2tshop.adapter.BillAdapter;
import com.h2tshop.h2tshop.model.Bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillActivity extends AppCompatActivity {

    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    Toolbar toolbar;

    ListView listView;
    final List<Bill> billList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setTitle(getString(R.string.bill));

        //      TollBar
        toolbar = findViewById(R.id.toolbarBill);
        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);



        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();
        myData = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.lvBill);

        setListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BillActivity.this, DetailBillActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idBill",billList.get(position).getIdBill());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    private  void setListView(){
        myData.child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Bill bill = snapshot.getValue(Bill.class);

                if (Objects.requireNonNull(bill).getEmail().equals(myUser.getEmail())){
                    billList.add(bill);
                }

                BillAdapter billAdapter = new BillAdapter(BillActivity.this,billList);
                listView.setAdapter(billAdapter);

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (!billList.get(position).isStatus()){
                            Dialog dialog = new Dialog(BillActivity.this);
                            dialog.setContentView(R.layout.dialog_delete);

                            TextView ok = dialog.findViewById(R.id.ok);
                            TextView huy = dialog.findViewById(R.id.huy);

                            ok.setOnClickListener(v -> {
                                String idBill = billList.get(position).getIdBill();
                                myData.child("Bill").child(idBill).setValue(null).addOnSuccessListener(aVoid -> {
                                    billList.remove(position);

                                    listView.setAdapter(billAdapter);

                                    Toast.makeText(BillActivity.this, "Xóa thành công!", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();

                                });
                            });

                            huy.setOnClickListener(v -> dialog.dismiss());
                            dialog.show();
                        }
                        return false;
                    }
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
}