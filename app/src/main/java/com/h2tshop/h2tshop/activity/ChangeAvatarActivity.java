package com.h2tshop.h2tshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.h2tshop.h2tshop.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class ChangeAvatarActivity extends AppCompatActivity {
    FirebaseStorage myStore;
    StorageReference storageRef;
    FirebaseUser myUser;
    DatabaseReference myData;
    FirebaseAuth myAuth;

    Toolbar toolbar;

    TextView chup,chon,save;
    ImageView avatarNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_avatar);
        setTitle("Thay ảnh đại điện");

        avatarNew = findViewById(R.id.imgNewAvatar);
        // Fire base
        myData = FirebaseDatabase.getInstance().getReference();

        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        myStore = FirebaseStorage.getInstance();
        storageRef = myStore.getReference();

        //      TollBar
        toolbar = findViewById(R.id.toolbarDoiAvatar);
        setToolbar();

        // set Avatar
        setAvatar();

        // Chụp
        chup = findViewById(R.id.chup);
        chup.setOnClickListener(v -> {
            Intent iChup = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(iChup,1);
        });

        // Chọn

        chon =findViewById(R.id.chon);
        chon.setOnClickListener(v -> {
            Intent iChon = new Intent();
            iChon.setType("image/*");
            iChon.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(iChon,1);

        });


        // Save

        save = findViewById(R.id.saveAvatar);
        saveInfo();
    }

    private void saveInfo(){
        save.setOnClickListener(v -> {
            Dialog dialog = new Dialog(ChangeAvatarActivity.this);
            dialog.setContentView(R.layout.dialog_thay_doi);

            TextView ok = dialog.findViewById(R.id.ok);
            TextView huy = dialog.findViewById(R.id.huy);

            ok.setOnClickListener(v12 -> {

                StorageReference mountainsRef = storageRef.child("avatar").child("avatar_" + getUserName() + ".png");

                avatarNew.setDrawingCacheEnabled(true);
                avatarNew.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) avatarNew.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);

                uploadTask.addOnSuccessListener(taskSnapshot -> {
                });
                Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }

                    // Continue with the task to get the download URL
                    return mountainsRef.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();

                        myData.child("Account").child(getUserName()).child("link").setValue(String.valueOf(uri));
                    }
                });

                Toast.makeText(getApplicationContext(), "Thay đổi thành công!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            });
            huy.setOnClickListener(v1 -> dialog.dismiss());
            dialog.show();
        });
    }

    private void setAvatar() {

        myData.child("Account").child(getUserName()).child("link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(Objects.requireNonNull(snapshot.getValue()).toString()).into(avatarNew);
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

    private void  setToolbar(){
        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Drawable drawable   = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null){

            Uri imageUri = data.getData();
            if (imageUri != null){
                avatarNew.setImageURI(imageUri);
            }else {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                avatarNew.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}