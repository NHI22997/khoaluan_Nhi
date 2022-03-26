package com.example.ungdunghoctap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText mand, passwordlogin;
    Button btnlogin;
    ImageButton imggoogle;

    /////Lưu trữ các thông tin dưới dạng Key-Value
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_MAND = "MAND";
    private static final String KEY_VAITRO = "VAITRO";
    /////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mand = findViewById(R.id.mand);
        passwordlogin = findViewById(R.id.passwordlogin);
        btnlogin = findViewById(R.id.btnlogin);
        imggoogle = findViewById(R.id.imggoogle);

        /////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        /////

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginActivity.checkLogin().execute("");
            }
        });
    }

    public class checkLogin extends AsyncTask<String, String, String> {

        String z = null;
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {

            mand.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 0) {
                        mand.setError("Hãy nhập Tài khoản!");
                    } else {
                        mand.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            passwordlogin.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 0) {
                        passwordlogin.setError("Hãy nhập Mật khẩu!");
                    } else {
                        passwordlogin.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            try {
                String sql = "SELECT * FROM NguoiDung WHERE MaND = '" + mand.getText() + "' AND MatKhau = '" + HASH.encryptPassword(passwordlogin.getText().toString()) + "'";
                Connection con = new ConnectionClass().CONN();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sql);

                if (rs.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                        }
                    });
                    z = "Success";

                    //Phân quyền
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_MAND, rs.getString("MaND"));
                    editor.putString(KEY_VAITRO, rs.getString("VaiTro"));
                    editor.apply();
                    /////
                    if(Integer.parseInt(rs.getString("VaiTro")) == 0){
                        Intent loadMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(loadMain);

                        finish();
                    }else {
                        Intent loadMain = new Intent(LoginActivity.this, LecturersActivity.class);
                        startActivity(loadMain);

                        finish();
                    }
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(LoginActivity.this, "Kiểm tra lại thông tin đăng nhập!", Toast.LENGTH_LONG).show();

                        }
                    });

                    mand.setText("");
                    passwordlogin.setText("");
                }
            } catch (Exception e) {
                isSuccess = false;
                Log.e("SQL Error : ", e.getMessage());
            }
            return z;
        }
    }
}
