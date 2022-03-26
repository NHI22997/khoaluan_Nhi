package com.example.ungdunghoctap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class tkDiemTichLuy extends AppCompatActivity {

    Toolbar toolbar;
    LineChart linechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tk_diem_tich_luy);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        linechart = findViewById(R.id.linechart);
        LineDataSet lineDataSetTBTichLuyHK = new LineDataSet(dataValuesTBTichLuyHK(), "THỐNG KÊ ĐIỂM TB TÍCH LŨY HỌC KỲ");
        lineDataSetTBTichLuyHK.setColor(Color.RED);
        lineDataSetTBTichLuyHK.setLineWidth(3f);

        LineDataSet lineDataSetTBTichLuy = new LineDataSet(dataValuesTBTichLuy(), "THỐNG KÊ ĐIỂM TB TÍCH LŨY");
        lineDataSetTBTichLuy.setColor(Color.BLUE);
        lineDataSetTBTichLuy.setLineWidth(3f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(lineDataSetTBTichLuyHK);
        dataSets.add(lineDataSetTBTichLuy);

        LineData lineData = new LineData(dataSets);
        linechart.setVisibleXRangeMaximum(65);
        linechart.setData(lineData);
        linechart.invalidate();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        Intent intentThongTin = new Intent(tkDiemTichLuy.this, ProfileActivity.class);
                        startActivity(intentThongTin);
                        break;

                    case R.id.logout:
                        String thongbao = "Đã đăng xuất!";
                        Intent intentDangXuat = new Intent(tkDiemTichLuy.this, LoginActivity.class);
                        intentDangXuat.putExtra("dangxuat", thongbao);
                        startActivity(intentDangXuat);
                        break;

                    case R.id.exit:
                        AlertDialog.Builder dialogThoat = new AlertDialog.Builder(tkDiemTichLuy.this);
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

    public ArrayList<Entry> dataValuesTBTichLuyHK() {
        ArrayList<Entry> dv = new ArrayList<Entry>();
            dv.add(new Entry(0, 3));
            dv.add(new Entry(1, 4));
            dv.add(new Entry(2, 2));
            dv.add(new Entry(3, 1));
            dv.add(new Entry(4, 5));

         return dv;
    }

    public ArrayList<Entry> dataValuesTBTichLuy() {
        ArrayList<Entry> dv = new ArrayList<Entry>();
            dv.add(new Entry(0, 3));
            dv.add(new Entry(1, 5));
            dv.add(new Entry(2, 4));
            dv.add(new Entry(3, 3));
            dv.add(new Entry(4, 2));

        return dv;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
}