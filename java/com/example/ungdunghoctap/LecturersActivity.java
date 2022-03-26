package com.example.ungdunghoctap;

import static com.example.ungdunghoctap.R.layout.activity_lecturers;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import android.os.Bundle;

public class LecturersActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton imggvqldiem, imggvxemtkb;
    Button btnLop;
    TextView txtHoten;

    //Lưu trữ các thông tin dưới dạng Key-Value
    SharedPreferences sharedPreferences;
    final String SHARED_PREF_NAME = "mypref";
    final String KEY_MAND = "MAND";
    String MAND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_lecturers);

        toolbar = findViewById(R.id.toolbar);

        btnLop = findViewById(R.id.btnLop);
        imggvqldiem = findViewById(R.id.imggvqldiem);
        imggvxemtkb = findViewById(R.id.imggvxemtkb);

        txtHoten = findViewById(R.id.txtHoten);

        /////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        MAND = sharedPreferences.getString(KEY_MAND,null);

        try {
            String sql = "SELECT cv.MaLop_Id " +
                         "FROM NguoiDung nd, qlht_CoVanHocTap cv " +
                         "WHERE cv.MaCB_Id = nd.MaND and nd.MaND = '" + MAND + "'";
            Connection con = new ConnectionClass().CONN();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                //txtHoten.setText(rs.getString(1));
                btnLop.setText(rs.getString(1));
            }
        }catch (Exception e) {
            Log.e("Query Error : ", e.getMessage());
        }

        btnLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadTBTichLuy = new Intent(LecturersActivity.this, dsSinhVien.class);
                startActivity(loadTBTichLuy);
                overridePendingTransition(0,0);
            }
        });

        imggvqldiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadTBTichLuy = new Intent(LecturersActivity.this, qlDiem.class);
                startActivity(loadTBTichLuy);
                overridePendingTransition(0,0);
            }
        });

        imggvxemtkb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadTBTichLuy = new Intent(LecturersActivity.this, tkbGiangDay.class);
                startActivity(loadTBTichLuy);
                overridePendingTransition(0,0);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        Intent intentThongTin = new Intent(LecturersActivity.this, ProfileActivity.class);
                        startActivity(intentThongTin);
                        break;

                    case R.id.logout:
                        String thongbao = "Đã đăng xuất!";
                        Intent intentDangXuat = new Intent(LecturersActivity.this, LoginActivity.class);
                        intentDangXuat.putExtra("dangxuat", thongbao);
                        startActivity(intentDangXuat);
                        break;

                    case R.id.exit:
                        AlertDialog.Builder dialogThoat = new AlertDialog.Builder(LecturersActivity.this);
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