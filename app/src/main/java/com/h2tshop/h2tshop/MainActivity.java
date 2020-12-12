package com.h2tshop.h2tshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.h2tshop.h2tshop.activity.CartActivity;
import com.h2tshop.h2tshop.activity.LoginSignupActivity;
import com.h2tshop.h2tshop.fragment.BillFragment;
import com.h2tshop.h2tshop.fragment.ShopFragment;

import com.h2tshop.h2tshop.fragment.AccountFragment;
import com.h2tshop.h2tshop.fragment.HomeFragment;
import com.h2tshop.h2tshop.fragment.AboutFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth myAuth;
    FirebaseUser user;
//asdsadsad
    NavigationView navigationView;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("AS");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawer, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new HomeFragment()).commit();
        setTitle("Trang Chủ");

        myAuth = FirebaseAuth.getInstance();
        user = myAuth.getCurrentUser();


        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.tranChu){
                setTitle("Trang Chủ");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
            }else if (item.getItemId() == R.id.taiKhoan) {
                setTitle("Tài khoản");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AccountFragment()).commit();
            }else if (item.getItemId() == R.id.cuaHang) {
                setTitle("Cửa hàng");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ShopFragment()).commit();
            }else if (item.getItemId() == R.id.gioHang) {
                Intent iCart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(iCart);
            }else if(item.getItemId() == R.id.bill){
                setTitle("Hóa Đơn Giao Dịch");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new BillFragment()).commit();
            }else if (item.getItemId() == R.id.about) {
                setTitle("Thông tin về chúng tôi");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AboutFragment()).commit();
            }else if (item.getItemId() == R.id.thoat) {
                myAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }

            drawer.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i==R.id.openMyCart){
            Intent intent = new Intent(MainActivity.this,CartActivity.class);
            startActivity(intent);
        }

        return true;

    }
}