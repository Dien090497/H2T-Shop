package com.h2tshop.h2tshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.h2tshop.h2tshop.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {
    Toolbar toolbar;

    FirebaseAuth myAuth;
    FirebaseUser user;

    TextInputLayout textInputLayoutPass1,textInputLayoutPass2;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        setTitle("Đổi mật khẩu");

        //      TollBar
        toolbar = findViewById(R.id.toolbarDoiMatKhau);
        setToolbar();

        // Đổi MK and SAve

        myAuth = FirebaseAuth.getInstance();
        user = myAuth.getCurrentUser();

        textInputLayoutPass1= findViewById(R.id.text_input_passwordNew1);
        textInputLayoutPass2=findViewById(R.id.text_input_passwordNew2);
        save = findViewById(R.id.doiMK);

        savePassword();

    }

    private boolean validaePass(){
        String pass1 = Objects.requireNonNull(textInputLayoutPass1.getEditText()).getText().toString().trim();
        String pass2 = Objects.requireNonNull(textInputLayoutPass2.getEditText()).getText().toString().trim();
        if (pass1.length()<8){
            textInputLayoutPass1.setError("Mật khẩu không nhỏ quá 8 kí tự");
            return false;
        }else if (!pass1.equals(pass2)){
            textInputLayoutPass2.setError("Không trùng khớp");
            return false;
        }else{
            textInputLayoutPass1.setError(null);
            textInputLayoutPass2.setError(null);
            return true;
        }
    }

    private void savePassword(){
        save.setOnClickListener(v -> {
            if (validaePass()){
                Dialog  dialog = new Dialog(ChangePasswordActivity.this);
                dialog.setContentView(R.layout.dialog_thay_doi);

                TextView ok = dialog.findViewById(R.id.ok);
                TextView huy = dialog.findViewById(R.id.huy);

                ok.setOnClickListener(v12 -> {
                    String pass1 = Objects.requireNonNull(textInputLayoutPass1.getEditText()).getText().toString().trim();
                    Log.e("DASDSADAD", pass1);
                    user.updatePassword(pass1).addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_LONG).show());
                });

                huy.setOnClickListener(v1 -> dialog.dismiss());

                dialog.show();
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