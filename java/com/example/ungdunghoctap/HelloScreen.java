package com.example.ungdunghoctap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class HelloScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_screen);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){

                }finally {
                    Intent loadLogin = new Intent(HelloScreen.this, LoginActivity.class);
                    startActivity(loadLogin);
                }
            }
        });
        thread.start();
    }
}