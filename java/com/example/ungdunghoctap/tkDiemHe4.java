package com.example.ungdunghoctap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class tkDiemHe4 extends AppCompatActivity {

    Toolbar toolbar;
    PieChart piechart;

    ArrayList<PieEntry>diemch1;
    ArrayList<PieModel>myList = new ArrayList<>();
    String MAND;
    //Lưu trữ các thông tin dưới dạng Key-Value
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_MAND = "MAND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tk_diem_he4);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        piechart = findViewById(R.id.piechart);

        diemch1 = new ArrayList<>();

        pieData();
        for(int i=0; i<myList.size(); i++){
            String diemch = myList.get(i).getDiemCH1();
            int dem = Integer.parseInt(myList.get(i).getDem());
            diemch1.add(new PieEntry(dem,""+diemch));
        }

        PieDataSet pieDataSet = new PieDataSet(diemch1,"LOẠI ĐIỂM");
        //Màu sắc: VORDIPLOM_COLORS,...
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(14f);

        PieData pieData = new PieData(pieDataSet);

        piechart.setData(pieData);
        piechart.getDescription().setEnabled(false);
        piechart.setCenterText("ĐIỂM HỆ 4");
        piechart.setCenterTextSize(23);
        //Size DiemCH1: A, B, C, D
        piechart.setEntryLabelTextSize(20);
        piechart.animate();

        Legend legend = new Legend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);

        //Animation
        piechart.animateY(1400, Easing.EaseInOutQuad);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        Intent intentThongTin = new Intent(tkDiemHe4.this, ProfileActivity.class);
                        startActivity(intentThongTin);
                        break;

                    case R.id.logout:
                        String thongbao = "Đã đăng xuất!";
                        Intent intentDangXuat = new Intent(tkDiemHe4.this, LoginActivity.class);
                        intentDangXuat.putExtra("dangxuat", thongbao);
                        startActivity(intentDangXuat);
                        break;

                    case R.id.exit:
                        AlertDialog.Builder dialogThoat = new AlertDialog.Builder(tkDiemHe4.this);
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

    public void pieData() {
        Intent intent = this.getIntent();
        MAND = intent.getStringExtra("mssv");
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        // MAND = sharedPreferences.getString(KEY_MAND,null);
        try {
            String sql = "SELECT DiemCH1, COUNT(*) as Dem FROM Diem WHERE MaSV = '" + MAND + "' and DiemCH1 != '' GROUP BY DiemCH1";
            Connection con = new ConnectionClass().CONN();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                myList.add(new PieModel("" + rs.getString(1),"" + rs.getString(2)));
            }

        }catch (Exception e){
            Log.e("Query Error : ", e.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
}