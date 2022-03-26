package com.example.ungdunghoctap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hpCanCaiThien extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtStt, txtMamh, txtTenmh, txtDiemhe10, txtDiemhe4, txtDiemquydoi;
    SimpleAdapter adapter;
    String MAND;
    //Lưu trữ các thông tin dưới dạng Key-Value
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_MAND = "MAND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hp_can_cai_thien);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        Intent intentThongTin = new Intent(hpCanCaiThien.this, ProfileActivity.class);
                        startActivity(intentThongTin);
                        break;

                    case R.id.logout:
                        String thongbao = "Đã đăng xuất!";
                        Intent intentDangXuat = new Intent(hpCanCaiThien.this, LoginActivity.class);
                        intentDangXuat.putExtra("dangxuat", thongbao);
                        startActivity(intentDangXuat);
                        break;

                    case R.id.exit:
                        AlertDialog.Builder dialogThoat = new AlertDialog.Builder(hpCanCaiThien.this);
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

        txtStt = findViewById(R.id.txtStt);
        txtMamh = findViewById(R.id.txtMamh);
        txtTenmh = findViewById(R.id.txtTenmh);
        txtDiemhe10 = findViewById(R.id.txtDiemhe10);
        txtDiemhe4 = findViewById(R.id.txtDiemhe4);
        txtDiemquydoi = findViewById(R.id.txtDiemquydoi);

        ListView list = (ListView)findViewById(R.id.listView1);

        List<Map<String,String>> data = new ArrayList<Map<String,String>>();

        Intent intent = this.getIntent();
        MAND = intent.getStringExtra("mssv");
        /////
        //sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        //String MAND = sharedPreferences.getString(KEY_MAND,null);

        try {
            String sql = "SELECT d.MaMH, mh.TenMH, d.DiemTK, d.Diems, d.DiemCH1, d.DiemTH " +
                         "FROM Diem d, NguoiDung nd, MonHoc mh " +
                         "WHERE d.MaSV = nd.MaND and mh.MaMH = d.MaMH and DiemCH1 = 'D' and nd.MaND = '" + MAND + "'";

            Connection con = new ConnectionClass().CONN();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            int stt = 1;

            while (rs.next()) {
                Log.e("dshpcct",data.size()+"");

                Map<String, String> tab = new HashMap<String, String>();

                tab.put("Stt", stt+"");
                tab.put("Mamh", rs.getString(1));
                tab.put("Tenmh", rs.getString(2));
                tab.put("Diemhe10", rs.getString(3));
                tab.put("Diemhe4", rs.getString(4));
                tab.put("Diemquydoi", rs.getString(5));

                data.add(tab);
                stt++;
            }
            String[] from = {"Stt","Mamh","Tenmh","Diemhe10","Diemhe4","Diemquydoi"};

            int[] to = {R.id.txtStt,R.id.txtMamh,R.id.txtTenmh,R.id.txtDiemhe10,R.id.txtDiemhe4,R.id.txtDiemquydoi};
            adapter = new SimpleAdapter(hpCanCaiThien.this, data, R.layout.listview_hocphancancaithien, from, to);
            list.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }
}