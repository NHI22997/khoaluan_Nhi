package com.example.ungdunghoctap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
public class BangDiem extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_diem);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        Intent intentThongTin = new Intent(BangDiem.this, ProfileActivity.class);
                        startActivity(intentThongTin);
                        break;

                    case R.id.logout:
                        String thongbao = "Đã đăng xuất!";
                        Intent intentDangXuat = new Intent(BangDiem.this, LoginActivity.class);
                        intentDangXuat.putExtra("dangxuat", thongbao);
                        startActivity(intentDangXuat);
                        break;

                    case R.id.exit:
                        AlertDialog.Builder dialogThoat = new AlertDialog.Builder(BangDiem.this);
                        dialogThoat.setTitle("Thoát ứng dụng");
                        dialogThoat.setIcon(R.drawable.warning);
                        dialogThoat.setMessage("Bạn muốn thoát ứng dụng?");

                        dialogThoat.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent startMain = new Intent(Intent.ACTION_MAIN);
                                startMain.addCategory(Intent.CATEGORY_HOME);
                                startActivity(startMain);
                                finish();
                            }
                        });
                        dialogThoat.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialogThoat.show();
                        break;
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
}