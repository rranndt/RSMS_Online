package com.belicode.rsmsonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailAntrian extends AppCompatActivity {


TextView tvnoantrian,tvdilayani;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvnoantrian=(TextView)findViewById(R.id.tvnomor);
        tvdilayani=(TextView)findViewById(R.id.tvperkiraan);

        Intent intent =getIntent();
        tvnoantrian.setText(intent.getStringExtra("no_antrian"));
        tvdilayani.setText(intent.getStringExtra("di_layani"));
        LinearLayout mAdViewLayout = findViewById(R.id.adView);
        Iklan.TampilkanBanner(getApplicationContext(), mAdViewLayout);
    }
    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        Intent intent =new Intent(DetailAntrian.this,MainMenuActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
        super.onBackPressed();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent =new Intent(DetailAntrian.this,MainMenuActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
        return super.onKeyDown(keyCode, event);
    }
    }
