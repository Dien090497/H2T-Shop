package com.h2tshop.h2tshop.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.h2tshop.h2tshop.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    Toolbar toolbar;

    LinearLayout goLogin;
    TextView taoTK;
    TextInputLayout textInputEditTextTen,textInputEditTextEmailNew,textInputEditTextPasswordNew1,textInputEditTextPasswordNew2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        setTitle("Đăng Kí");

        //      TollBar
        toolbar = findViewById(R.id.toolbarSignUp);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);


//        Tạo tài khoản

        textInputEditTextTen = findViewById(R.id.text_input_ten);
        textInputEditTextEmailNew = findViewById(R.id.text_input_emailNew);
        textInputEditTextPasswordNew1 = findViewById(R.id.text_input_passwordNew1);
        textInputEditTextPasswordNew2 = findViewById(R.id.text_input_passwordNew2);

        taoTK = findViewById(R.id.taoTK);
        taoTK.setOnClickListener(v -> {
            // Xét màu
            Resources resources = getResources();
            Drawable bgr = resources.getDrawable(R.drawable.button_black);
            taoTK.setBackground(bgr);

            // Kiểm tra
            if (validateTen() && validateEmail() && validatePassword()){
                String ten = Objects.requireNonNull(textInputEditTextTen.getEditText()).getText().toString().trim();
                String email = Objects.requireNonNull(textInputEditTextEmailNew.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(textInputEditTextPasswordNew1.getEditText()).getText().toString().trim();

                Intent iAddInfo = new Intent(SignUpActivity.this, AddInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ten",ten);
                bundle.putString("email",email);
                bundle.putString("password",password);
                iAddInfo.putExtras(bundle);
                startActivity(iAddInfo);
            }
        });


//        Chuyển sang dăng nhập

        goLogin = findViewById(R.id.openLogin);
        goLogin.setOnClickListener(v -> {
            Intent iLogin = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(iLogin);
        });


    }

    private  boolean validateTen(){
        String ten = Objects.requireNonNull(textInputEditTextTen.getEditText()).getText().toString().trim();

        if (ten.equals("")){
            textInputEditTextTen.setError("Tên không được trống");
            return false;}
        else{
            textInputEditTextTen.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){
        String email = Objects.requireNonNull(textInputEditTextEmailNew.getEditText()).getText().toString().trim();
        if (email.equals("")){
            textInputEditTextEmailNew.setError("Email không được để trống");
            return false;
        }else {
            textInputEditTextEmailNew.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String password1 = Objects.requireNonNull(textInputEditTextPasswordNew1.getEditText()).getText().toString().trim();
        String password2 = Objects.requireNonNull(textInputEditTextPasswordNew2.getEditText()).getText().toString().trim();
        if (password1.length()<8){
            textInputEditTextPasswordNew1.setError("Mật khẩu không nhỏ quá 8 kí tự");
            return false;
        }else if (!password1.equals(password2)){
            textInputEditTextPasswordNew2.setError("Không trùng khớp");
            return false;
        }else{
            textInputEditTextPasswordNew1.setError(null);
            textInputEditTextPasswordNew2.setError(null);
            return true;
        }
    }
}
