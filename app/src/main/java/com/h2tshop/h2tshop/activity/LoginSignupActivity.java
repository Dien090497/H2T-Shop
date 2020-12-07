package com.h2tshop.h2tshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.h2tshop.h2tshop.MainActivity;
import com.h2tshop.h2tshop.R;

public class LoginSignupActivity extends AppCompatActivity {
    FirebaseAuth myAuth;
    FirebaseUser user;

    TextView btnOpenLogin,btnOpenSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_login_signup);

        myAuth = FirebaseAuth.getInstance();
        user= myAuth.getCurrentUser();

        try {
            String email = user.getEmail();
            if (email!=null){
                Intent intent = new Intent(LoginSignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception ignored){

        }


        btnOpenLogin = findViewById(R.id.tvOpenLogin);
        btnOpenSignUp = findViewById(R.id.tvOpenSignup);

        btnOpenLogin.setOnClickListener(v -> {
            Intent iLogin = new Intent(LoginSignupActivity.this,LoginActivity.class);
            startActivity(iLogin);
        });

        btnOpenSignUp.setOnClickListener(v -> {
            Intent iSignUp = new Intent(LoginSignupActivity.this,SignUpActivity.class);
            startActivity(iSignUp);
        });

    }
}
