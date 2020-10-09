package com.belicode.rsmsonline;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.belicode.rsmsonline.pdModel.pdModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailKlinik extends AppCompatActivity {


    TextView tvsenin,tvselasa,tvrabu,tvkamis,tvjumat,tvsaptu,tvminggu;
    TextView tvdokter,tvidklinik,tvnamaklinik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailklinik);
        tvidklinik=(TextView)findViewById(R.id.tvidklinik);
        tvnamaklinik=(TextView)findViewById(R.id.tvnamaklinik);
        tvsenin=(TextView)findViewById(R.id.tvsenin);
        tvselasa=(TextView)findViewById(R.id.tvselasa);
        tvrabu=(TextView)findViewById(R.id.tvrabu);
        tvkamis=(TextView)findViewById(R.id.tvkamis);
        tvjumat=(TextView)findViewById(R.id.tvjumat);
        tvsaptu=(TextView)findViewById(R.id.tvsaptu);
        tvminggu=(TextView)findViewById(R.id.tvminggu);
        tvdokter=(TextView)findViewById(R.id.tvdokter);
        Intent intent =getIntent();
        tvidklinik.setText(intent.getStringExtra("id_klinik2"));
        getHari();
        LinearLayout mAdViewLayout = findViewById(R.id.adView);
        Iklan.TampilkanBanner(getApplicationContext(), mAdViewLayout);
    }
    public void getHari() {
        pdModel.pdLogin(DetailKlinik.this);
        String url = ConfigApp.SERVERAPP+"getHari.php?id_klinik=";
        StringRequest stringRequest2 = new StringRequest(url + tvidklinik.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                String senin = "";
                String selasa = "";
                String jumat = "";
                String rabu = "";
                String kamis = "";
                String saptu = "";
                String minggu = "";



                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_klinik");
                    senin = collegeData.getString("senin");
                    selasa = collegeData.getString("selasa");
                    rabu = collegeData.getString("rabu");
                    kamis = collegeData.getString("kamis");
                    jumat = collegeData.getString("jumat");
                    saptu = collegeData.getString("saptu");
                    minggu = collegeData.getString("minggu");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvnamaklinik.setText(nama);
                tvsenin.setText(senin);
                tvselasa.setText(selasa);
                tvrabu.setText(rabu);
                tvkamis.setText(kamis);
                tvjumat.setText(jumat);
                tvsaptu.setText(saptu);
                tvminggu.setText(minggu);
                pdModel.hideProgressDialog();

getDokter();


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailKlinik.this, "Not Connections", Toast.LENGTH_LONG).show();
                        pdModel.hideProgressDialog();

                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(DetailKlinik.this);
        requestQueue2.add(stringRequest2);
    }
    public void getDokter() {
        pdModel.pdLogin(DetailKlinik.this);
        String url = ConfigApp.SERVERAPP+"getdokter.php?id_klinik=";
        StringRequest stringRequest2 = new StringRequest(url + tvidklinik.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";




                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvdokter.append(nama+"\n");

                pdModel.hideProgressDialog();




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailKlinik.this, "Not Connections", Toast.LENGTH_LONG).show();
                        pdModel.hideProgressDialog();

                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(DetailKlinik.this);
        requestQueue2.add(stringRequest2);
    }

}
