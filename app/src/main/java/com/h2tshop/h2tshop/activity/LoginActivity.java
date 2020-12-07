package com.h2tshop.h2tshop.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.h2tshop.h2tshop.MainActivity;
import com.h2tshop.h2tshop.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    Toolbar toolbar;

    TextView login;
    TextInputLayout textInputLayoutEmail,textInputLayoutPassword;
    LinearLayout goSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đang nhập");
//      TollBar
        toolbar = findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);


//        Đăng nhập
        firebaseAuth = FirebaseAuth.getInstance();

        textInputLayoutEmail = findViewById(R.id.text_input_email);
        textInputLayoutPassword =findViewById(R.id.text_input_password);

        login = findViewById(R.id.loGin);
        login.setOnClickListener(v -> {
            // Xét màu
            Resources resources = getResources();
            Drawable bgr = resources.getDrawable(R.drawable.button_red);
            login.setBackground(bgr);

            dangNhap();

        });


//        Chuyển sang Đăng Kí
        goSignUp = findViewById(R.id.openSignup);
        goSignUp.setOnClickListener(v -> {

            Intent iSignUp = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(iSignUp);
        });
    }

//    Đăng nhập
    private void dangNhap(){
        String email = Objects.requireNonNull(textInputLayoutEmail.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(textInputLayoutPassword.getEditText()).getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent iLoginOk = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(iLoginOk);
                        finish();

                    } else {
                        textInputLayoutEmail.setError("Sai tài khoản hoặc mất khẩu!");
                    }
                });
    }


}
