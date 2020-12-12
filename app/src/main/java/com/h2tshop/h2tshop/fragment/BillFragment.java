package com.h2tshop.h2tshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class BillFragment extends Fragment {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    private View viewRoot;
    ListView listView;
    final List<Bill> billList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot =inflater.inflate(R.layout.fragment_bill,container,false);
        intView();
        return viewRoot;
    }

    private void intView() {
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();
        myData = FirebaseDatabase.getInstance().getReference();

        listView = viewRoot.findViewById(R.id.lvBill);

        setListView();

    }


    private  void setListView(){
        myData.child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Bill bill = snapshot.getValue(Bill.class);

                if (Objects.requireNonNull(bill).getEmail().equals(myUser.getEmail())){
                    billList.add(bill);
                }

                BillAdapter billAdapter = new BillAdapter(requireActivity(),billList);
                listView.setAdapter(billAdapter);
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
