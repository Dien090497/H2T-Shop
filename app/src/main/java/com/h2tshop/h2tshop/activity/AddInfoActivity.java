package com.h2tshop.h2tshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.h2tshop.h2tshop.R;
import com.h2tshop.h2tshop.model.Account;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddInfoActivity extends AppCompatActivity {
    DatabaseReference myData;
    FirebaseAuth firebaseAuth;

    Toolbar toolbar;

    TextInputLayout textInputLayoutNgaySinh,textInputLayoutSDT,textInputLayoutHuyen,textInputLayoutThanhPho;
    TextView taoTK2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        setTitle("Đăng Ký");

        //      TollBar
        toolbar = findViewById(R.id.toolbarSignUp2);
        setToolbar();


        // Add thông tin lên Database.
        myData = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        textInputLayoutNgaySinh = findViewById(R.id.text_input_ngaySinh);
        textInputLayoutSDT = findViewById(R.id.text_input_sdt);
        textInputLayoutHuyen = findViewById(R.id.text_input_huyenThiXa);
        textInputLayoutThanhPho = findViewById(R.id.text_input_thanhPho);
        taoTK2 = findViewById(R.id.taoTK2);

        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();

        taoTK2.setOnClickListener(v -> {
            String email = bundle.getString("email");
            String ten = bundle.getString("ten");
            String password = bundle.getString("password");
            String ngaySinh = Objects.requireNonNull(textInputLayoutNgaySinh.getEditText()).getText().toString().trim();
            String sdt = Objects.requireNonNull(textInputLayoutSDT.getEditText()).getText().toString().trim();
            String huyen = Objects.requireNonNull(textInputLayoutHuyen.getEditText()).getText().toString().trim();
            String thanhPho = Objects.requireNonNull(textInputLayoutThanhPho.getEditText()).getText().toString().trim();

            Account account = new Account(email,ten,huyen,thanhPho,sdt,ngaySinh,"https://firebasestorage.googleapis.com/v0/b/h2tshop-4939d.appspot.com/o/avatar.png?alt=media&token=7bbe5dcc-901d-4c95-b795-a6ea50b3d417");

            StringBuilder _email= new StringBuilder();
            for (int i = 0; i < email.length(); i++) {
                if (String.valueOf(email.charAt(i)).equals(".")) {
                    break;
                }
                _email.append(email.charAt(i));

            }

            myData.child("Account").child(_email.toString()).setValue(account);
            dangKi();

        });


    }

    private void dangKi(){
        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();

        String email = bundle.getString("email");
        String password = bundle.getString("password");

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddInfoActivity.this, "Đăng ký thành công",
                                Toast.LENGTH_SHORT).show();
                        Intent ifinsh = new Intent(AddInfoActivity.this,LoginSignupActivity.class);
                        startActivity(ifinsh);
                    } else {
                        Toast.makeText(AddInfoActivity.this, "Đăng kí thất bại",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void setToolbar(){
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

}