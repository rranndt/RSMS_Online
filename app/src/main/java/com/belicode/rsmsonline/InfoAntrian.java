package com.belicode.rsmsonline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.belicode.rsmsonline.antrian.Antrian;
import com.belicode.rsmsonline.antrian.AntrianAdapter;
import com.belicode.rsmsonline.antrian.AntrianJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class InfoAntrian extends AppCompatActivity implements ListView.OnItemClickListener {
    List<Antrian> pList;
    ListView lv;
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    TextView tviduser, tvnomor_saatini;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.antrian_info);

        tviduser = (TextView) findViewById(R.id.tvIduser);
        tvnomor_saatini = (TextView) findViewById(R.id.tvnomorsaatini);
        lv = (ListView) findViewById(R.id.myList);
        lv.setOnItemClickListener(this);
        Iklan.Tampilkanintersial(getApplicationContext());
        final Handler handler2 = new Handler();
        Runnable refresh2 = new Runnable() {
            @Override
            public void run() {
                Integer scond = 7;
                getNomor();
                handler2.postDelayed(this, scond * 1000);
            }
        };
        handler2.postDelayed(refresh2, 5 * 1000);
        LinearLayout mAdViewLayout = findViewById(R.id.adView);
        Iklan.TampilkanBanner(getApplicationContext(), mAdViewLayout);
    }

    public void requestData(String uri) {

        StringRequest request = new StringRequest(uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pList = AntrianJSON.parseData(response);
                        AntrianAdapter adapter = new AntrianAdapter(InfoAntrian.this, pList);
                        lv.setAdapter(adapter);


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //    Toast.makeText(PengumumanActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @SuppressLint("NewApi")
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetailAntrian.class);
        Antrian p = pList.get(i);
        intent.putExtra("no_antrian", p.getNomor_antrian());
        intent.putExtra("di_layani", p.getDi_layani());
        //intent.putExtra(Config.TAG_SUB_KELAS, p.getTgl());
        //intent.putExtra(Config.TAG_TGL,p.getImage());

        startActivity(intent);
        finish();
        finishAffinity();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();

    }

    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);

        tviduser.setText(UnameValue);
        requestData(ConfigApp.SERVERAPP + "listantrian.php?id_user=" + tviduser.getText().toString());

        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

    public void getNomor() {
        String url = ConfigApp.SERVERAPP + "getnomor.php?id_user=" ;
        StringRequest stringRequest2 = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";

                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nomor_antrian");

                    Log.e("Antrian", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ANTRIAN2", e.getMessage());
                }
                tvnomor_saatini.setText(nama);
                Toast.makeText(InfoAntrian.this, "nama : " + nama, Toast.LENGTH_SHORT).show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InfoAntrian.this, "Not Connections", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(InfoAntrian.this);
        requestQueue2.add(stringRequest2);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InfoAntrian.this, MainMenuActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
        super.onBackPressed();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(InfoAntrian.this, MainMenuActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
        return super.onKeyDown(keyCode, event);
    }
}
