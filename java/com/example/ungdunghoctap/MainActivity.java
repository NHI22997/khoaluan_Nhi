package com.example.ungdunghoctap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton imgtkdiemtichluy, imgtkdiemhe4, imghpcancaithien, imgbangdiem;
    TextView txtHoten, txttichluyhkgannhat, txttbtichluy,txttongtinchi;

    String MAND;
    //Lưu trữ các thông tin dưới dạng Key-Value
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_MAND = "MAND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgtkdiemtichluy = findViewById(R.id.imgtkdiemtichluy);
        imgtkdiemhe4 = findViewById(R.id.imgtkdiemhe4);
        imghpcancaithien = findViewById(R.id.imghpcancaithien);
        imgbangdiem = findViewById(R.id.imgbangdiem);

        //txtHoten = findViewById(R.id.txtHoten);
        txttichluyhkgannhat = findViewById(R.id.txttichluyhkgannhat);
        txttbtichluy = findViewById(R.id.txttbtichluy);
        txttongtinchi = findViewById(R.id.txttongtinchi);

        //Intent intent = this.getIntent();
        //MAND = intent.getStringExtra("mssv");

        /////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        MAND = sharedPreferences.getString(KEY_MAND,null);

        try {
            String sql = "SELECT d.DTBS, d.DTBTLS, d.Tcchiatl " +
                         "FROM DiemTKHK d, NguoiDung nd " +
                         "WHERE d.MaSV = nd.MaND and nd.MaND = '" + MAND + "'";
            Connection con = new ConnectionClass().CONN();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                //txtHoten.setText(rs.getString(1));
                txttichluyhkgannhat.setText(rs.getString(1));
                txttbtichluy.setText(rs.getString(2));
                txttongtinchi.setText(rs.getString(3));
            }
        }catch (Exception e) {
            Log.e("Query Error : ", e.getMessage());
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        Intent intentThongTin = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intentThongTin);
                        break;

                    case R.id.logout:
                        String thongbao = "Đã đăng xuất!";
                        Intent intentDangXuat = new Intent(MainActivity.this, LoginActivity.class);
                        intentDangXuat.putExtra("dangxuat", thongbao);
                        startActivity(intentDangXuat);
                        break;

                    case R.id.exit:
                        AlertDialog.Builder dialogThoat = new AlertDialog.Builder(MainActivity.this);
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

        imgtkdiemtichluy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadTKDiemTichLuy = new Intent(MainActivity.this, tkDiemTichLuy.class);
                loadTKDiemTichLuy.putExtra("mssv",MAND);
                startActivity(loadTKDiemTichLuy);
                overridePendingTransition(0,0);
            }
        });

        imgtkdiemhe4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadTKDiemHe4 = new Intent(MainActivity.this, tkDiemHe4.class);
                loadTKDiemHe4.putExtra("mssv",MAND);
                startActivity(loadTKDiemHe4);
                overridePendingTransition(0,0);
            }
        });

        imghpcancaithien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadHpCanCaiThien = new Intent(MainActivity.this, hpCanCaiThien.class);
                loadHpCanCaiThien.putExtra("mssv",MAND);
                startActivity(loadHpCanCaiThien);
                overridePendingTransition(0,0);
            }
        });

        imgbangdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadBangDiem = new Intent(MainActivity.this, BangDiem.class);
                loadBangDiem.putExtra("mssv",MAND);
                startActivity(loadBangDiem);
                overridePendingTransition(0,0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
}