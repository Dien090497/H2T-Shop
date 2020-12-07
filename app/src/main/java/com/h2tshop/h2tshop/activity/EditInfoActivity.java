package com.h2tshop.h2tshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.h2tshop.h2tshop.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class EditInfoActivity extends AppCompatActivity {
    Toolbar toolbar;

    DatabaseReference myData;

    TextView tvMatKhau,tvThongTinCaNhan,tvAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin);
        setTitle("Sửa thông tin");
        //      TollBar
        toolbar = findViewById(R.id.toolbarSuaThongTin);
        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);

        myData = FirebaseDatabase.getInstance().getReference();

        // Mật khẩu

        tvMatKhau = findViewById(R.id.matKhau);
        tvMatKhau.setOnClickListener(v -> {
            Intent intentMK = new Intent(EditInfoActivity.this, ChangePasswordActivity.class);
            startActivity(intentMK);
        });

        // Thông tin cá nhân
        tvThongTinCaNhan = findViewById(R.id.thongTinCaNhan);
        tvThongTinCaNhan.setOnClickListener(v -> {
            Intent iInfo = new Intent(EditInfoActivity.this, ChangeInfoActivity.class);
            startActivity(iInfo);
        });

        // Avatar
        tvAvatar = findViewById(R.id.avatar);
        tvAvatar.setOnClickListener(v -> {
            Intent iAvatar = new Intent(EditInfoActivity.this, ChangeAvatarActivity.class);
            startActivity(iAvatar);
        });

    }
}