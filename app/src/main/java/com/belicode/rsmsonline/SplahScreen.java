package com.belicode.rsmsonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplahScreen extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        mHandler.sendEmptyMessageDelayed(1, 4000);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(android.os.Message msg) {
            mHandler.removeMessages(1);
            Intent intent = new Intent(SplahScreen.this, MainActivity.class);
            startActivity(intent);
            finish();


        }
    };

}
