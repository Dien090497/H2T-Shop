package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.model.Account;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ChangeInfoActivity extends AppCompatActivity {
    DatabaseReference myData;
    FirebaseAuth myAuth;
    FirebaseUser myUser;

    Toolbar toolbar;

    TextInputLayout textInputLayoutTenNew, textInputLayoutSDTNew, textInputLayoutHuyenNew, textInputLayoutThanhPhoNew, textInputLayoutNgaySinhNew;
    TextInputEditText textInputEditTextTenNew,textInputEditTextSDTNew, textInputEditTextHuyenNew,textInputEditTextThanhPhoNew, textInputEditTextNgaySinhNew;
    TextView save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_thong_tin);
        setTitle("Đăng Nhập");

        //      TollBar
        toolbar = findViewById(R.id.toolbarDoiThongTin);
        setToolbar();

        // Firebase

        myData = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        // Set thông tin
        textInputLayoutTenNew = findViewById(R.id.text_input_tenNew);
        textInputLayoutSDTNew = findViewById(R.id.text_input_sdtNew);
        textInputLayoutHuyenNew = findViewById(R.id.text_input_huyenNew);
        textInputLayoutThanhPhoNew = findViewById(R.id.text_input_thanhPhoNew);
        textInputLayoutNgaySinhNew = findViewById(R.id.text_input_ngaySinhNew);

        textInputEditTextTenNew = findViewById(R.id.text_input_edittext_tenNew);
        textInputEditTextSDTNew = findViewById(R.id.text_input_edittext_sdtNew);
        textInputEditTextHuyenNew = findViewById(R.id.text_input_edittext_huyenNew);
        textInputEditTextThanhPhoNew = findViewById(R.id.text_input_edittext_thanhPhoNew);
        textInputEditTextNgaySinhNew = findViewById(R.id.text_input_edittext_ngaySinhNew);

        readInfo();


    // Save
        save = findViewById(R.id.save);
        saveInfo();
    }

    private void saveInfo() {
        save.setOnClickListener(v -> {
            Dialog dialog = new Dialog(ChangeInfoActivity.this);
            dialog.setContentView(R.layout.dialog_thay_doi);

            TextView ok = dialog.findViewById(R.id.ok);
            TextView huy = dialog.findViewById(R.id.huy);

            ok.setOnClickListener(v12 -> {


                String tenNew = Objects.requireNonNull(textInputLayoutTenNew.getEditText()).getText().toString().trim();
                String sdtNew = Objects.requireNonNull(textInputLayoutSDTNew.getEditText()).getText().toString().trim();
                String huyenNew = Objects.requireNonNull(textInputLayoutHuyenNew.getEditText()).getText().toString().trim();
                String tpNew = Objects.requireNonNull(textInputLayoutThanhPhoNew.getEditText()).getText().toString().trim();
                String ngaySinhNew = Objects.requireNonNull(textInputLayoutNgaySinhNew.getEditText()).getText().toString().trim();

                myData.child("Account").child(getUserName()).child("ten").setValue(tenNew);
                myData.child("Account").child(getUserName()).child("sdt").setValue(sdtNew);
                myData.child("Account").child(getUserName()).child("huyen").setValue(huyenNew);
                myData.child("Account").child(getUserName()).child("thanhPho").setValue(tpNew);
                myData.child("Account").child(getUserName()).child("ngaySinh").setValue(ngaySinhNew);

                Toast.makeText(getApplicationContext(), "Thay đổi thành công!", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            });

            huy.setOnClickListener(v1 -> dialog.dismiss());

            dialog.show();
        });
    }

    private void readInfo() {
        myData.child("Account").child(getUserName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                textInputEditTextTenNew.setText(Objects.requireNonNull(account).getTen());
                textInputEditTextSDTNew.setText(account.getSdt());
                textInputEditTextHuyenNew.setText(account.getHuyen());
                textInputEditTextThanhPhoNew.setText(account.getThanhPho());
                textInputEditTextNgaySinhNew.setText(account.getNgaySinh());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getUserName(){
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

    private void setToolbar(){
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}