package com.example.ungdunghoctap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dsSinhVien extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtStt, txtMaSV, txtHoTenSV, txtNgaySinh;
    ListView list;
    SimpleAdapter adapter;

    //Lưu trữ các thông tin dưới dạng Key-Value
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_MAND = "MAND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_sinh_vien);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtStt = findViewById(R.id.txtStt);
        txtMaSV = findViewById(R.id.txtMaSV);
        txtHoTenSV = findViewById(R.id.txtHoTenSV);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);

        list = (ListView)findViewById(R.id.lvdssinhvien);

        List<Map<String,String>> data = new ArrayList<Map<String,String>>();

        /////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String MAND = sharedPreferences.getString(KEY_MAND,null);

        try {
            String sql = "SELECT sv.MaSV, sv.HoTenSV, sv.NgaySinh " +
                         "FROM SinhVien sv, NguoiDung nd, qlht_CoVanHocTap cv " +
                         "WHERE sv.Malop = cv.MaLop_Id and cv.MaCB_Id = nd.MaND and nd.MaND = '" + MAND + "'";

            Connection con = new ConnectionClass().CONN();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            int stt = 1;

            while (rs.next()) {
                Log.e("dssv ",data.size()+"");


                Map<String, String> tab = new HashMap<String, String>();

                tab.put("Stt", stt+"");
                tab.put("txtMaSV", rs.getString(1));
                tab.put("txtHoTenSV", rs.getString(2));
                tab.put("txtNgaySinh", rs.getString(3));

                data.add(tab);
                stt++;
            }
            String[] from = {"Stt","txtMaSV","txtHoTenSV","txtNgaySinh"};

            int[] to = {R.id.txtStt,R.id.txtMaSV,R.id.txtHoTenSV,R.id.txtNgaySinh};
            adapter = new SimpleAdapter(dsSinhVien.this, data, R.layout.listview_ds_sinhvien, from, to);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                //Sự kiện Click vào từng sinh viên
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object cast = data.get(i).values().toArray()[3];
                    Intent intent = new Intent(dsSinhVien.this,MainActivity_Element_Student.class);
                    intent.putExtra("mssv",cast+"");
                    startActivity(intent);
                    //Toast.makeText(dsSinhVien.this, i+"", Toast.LENGTH_SHORT).show();
                    //Log.e("fadsfdas", cast+"");
                }
            });
        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
}