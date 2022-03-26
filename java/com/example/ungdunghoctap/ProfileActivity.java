package com.example.ungdunghoctap;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtHoten, txtNgaysinh, txtEmail, txtSdt;

    //Lưu trữ các thông tin dưới dạng Key-Value
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_MAND = "MAND";
    private static final String KEY_VAITRO = "VAITRO";
    /////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtHoten = findViewById(R.id.txtHoten);
        txtNgaysinh = findViewById(R.id.txtNgaysinh);
        txtEmail = findViewById(R.id.txtEmail);
        txtSdt = findViewById(R.id.txtSdt);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String MAND = sharedPreferences.getString(KEY_MAND,null);
        String VAITRO = sharedPreferences.getString(KEY_VAITRO,null);

        if(Integer.parseInt(VAITRO) == 0){
            try {
                String sql = "SELECT sv.HoTenSV, sv.NgaySinh, nd.Email2, nd.DienThoai1 " +
                             "FROM SinhVien sv, NguoiDung nd " +
                             "WHERE sv.MaSV = nd.MaND and nd.MaND = '" + MAND + "'";
                Connection con = new ConnectionClass().CONN();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()){
                    txtHoten.setText(rs.getString(1));
                    txtNgaysinh.setText(rs.getString(2));
                    txtEmail.setText(rs.getString(3));
                    txtSdt.setText(rs.getString(4));
                }
            }catch (Exception e){
                Log.e("Query Error : ", e.getMessage());
            }
        }else {
            try {
                String sql = "SELECT cb.TenCB, cb.ngsinhcb, nd.Email1, nd.DienThoai1 " +
                             "FROM CanBo cb, NguoiDung nd " +
                             "WHERE cb.MaCB = nd.MaND and nd.MaND = '" + MAND + "'";
                Connection con = new ConnectionClass().CONN();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()){
                    txtHoten.setText(rs.getString(1));
                    txtNgaysinh.setText(rs.getString(2));
                    txtEmail.setText(rs.getString(3));
                    txtSdt.setText(rs.getString(4));
                }

            }catch (Exception e){
                Log.e("Query Error : ", e.getMessage());
            }
        }

        //////
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Thông tin");
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/

        //Menu
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        Intent intentThongTin = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intentThongTin);
                        break;

                    case R.id.logout:

                        //Làm sạch bộ nhớ
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        ////

                        String thongbao = "Đã đăng xuất!";
                        Intent intentDangXuat = new Intent(ProfileActivity.this, LoginActivity.class);
                        intentDangXuat.putExtra("dangxuat", thongbao);
                        startActivity(intentDangXuat);
                        break;

                    case R.id.exit:
                        AlertDialog.Builder dialogThoat = new AlertDialog.Builder(ProfileActivity.this);
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
