package com.belicode.rsmsonline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
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


public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tviduser, tvnamauser, tvemailuser, tvnomortelp;
    LinearLayout lv_anggotakeluarga, lv_pendaftaran, lv_klinik, lv_infoantrian;
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_NO = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private String no_telp;
    private String email_user;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv_anggotakeluarga = (LinearLayout) findViewById(R.id.view_anggotakeluarga);
        lv_pendaftaran = (LinearLayout) findViewById(R.id.view_pendaftaran);
        lv_klinik = (LinearLayout) findViewById(R.id.view_klinik);
        lv_infoantrian = (LinearLayout) findViewById(R.id.view_antrian);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        LinearLayout linearLayout = (LinearLayout) hView.findViewById(R.id.lnav);
        AnimationDrawable animationDrawable2 = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable2.setEnterFadeDuration(2000);
        animationDrawable2.setExitFadeDuration(4000);
        animationDrawable2.start();
        tviduser = (TextView) hView.findViewById(R.id.tvIduser);
        tvnomortelp = (TextView) hView.findViewById(R.id.tvnomortelp);
        tvnamauser = (TextView) hView.findViewById(R.id.tvNamauser);
        tvemailuser = (TextView) hView.findViewById(R.id.tvemailuser);
        Intent intent = getIntent();
        LinearLayout mAdViewLayout = findViewById(R.id.adView);
        Iklan.TampilkanBanner(getApplicationContext(), mAdViewLayout);
        // tvemailuser.setText(intent.getStringExtra("email_user"));
        lv_anggotakeluarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainMenuActivity.this, ListAnggotaKeluarga.class);
                intent1.putExtra("id_user2", tviduser.getText().toString());
                startActivity(intent1);
                Iklan.Tampilkanintersial(getApplicationContext());


            }
        });
        lv_pendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainMenuActivity.this)
                        .setMessage("PENDAFTARAN PASIEN")
                        .setCancelable(false)
                        .setPositiveButton("PASIEN LAMA", new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent1 = new Intent(MainMenuActivity.this, PilihPasienActivity.class);
                                intent1.putExtra("id_user2", tviduser.getText().toString());
                                startActivity(intent1);
                            }
                        })

                        .setNegativeButton("PASIEN BARU", new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(MainMenuActivity.this, InputPasienActivity.class);
                                startActivity(intent1);
                            }
                        })

                        .show();
            }
        });
        lv_klinik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainMenuActivity.this, ListKlinik.class);
                startActivity(intent1);

            }
        });
        lv_infoantrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainMenuActivity.this, InfoAntrian.class);
                startActivity(intent1);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1 = new Intent(MainMenuActivity.this, HubungiKami.class);
//                startActivity(intent1);
                Toast.makeText(MainMenuActivity.this, "tes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bantuan) {

            Intent intent = new Intent(MainMenuActivity.this, Bantuan.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_tentang) {
            Intent intent = new Intent(MainMenuActivity.this, TentangApp.class);
            startActivity(intent);
        } else if (id == R.id.nav_akun) {

            Intent intent = new Intent(MainMenuActivity.this, Akun.class);
            intent.putExtra("id_akun", tviduser.getText().toString());
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

            new AlertDialog.Builder(MainMenuActivity.this)
                    .setMessage("Keluar ?")
                    .setTitle("Anda akan keluar dari Aplikasi antrian")
                    .setCancelable(false)
                    .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int id) {
                            finish();

                            //lm.clearTestProviderLocation(provider);
                        }
                    })

                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })

                    .show();
        }
       /* }else if (id==R.id.nav_verifikasi){
            Intent intent222 =new Intent(MainMenuActivity.this,PilihVerifikasi.class);
            intent222.putExtra("id_user", tviduser.getText().toString());
            startActivity(intent222);
        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getProfile() {
        pdModel.pdLogin(MainMenuActivity.this);
        String url = ConfigApp.SERVERAPP + "getprofile.php";
        StringRequest stringRequest2 = new StringRequest(url + "?email_user=" + tvemailuser.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                String no_telp = "";


                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_user");
                    id_user = collegeData.getString("id_user");
                    no_telp = collegeData.getString("no_telp");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvnamauser.setText(nama);
                tviduser.setText(id_user);
                tvnomortelp.setText(no_telp);
                pdModel.hideProgressDialog();


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainMenuActivity.this, "Not Connections", Toast.LENGTH_LONG).show();
                        pdModel.hideProgressDialog();

                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(MainMenuActivity.this);
        requestQueue2.add(stringRequest2);
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = tviduser.getText().toString();
        no_telp = tvnomortelp.getText().toString();

        System.out.println("onPause save name: " + UnameValue);

        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_NO, no_telp);
        editor.apply();
    }

    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        email_user = settings.getString("email_user", DefaultUnameValue);
        no_telp = settings.getString(PREF_NO, DefaultUnameValue);

        tviduser.setText(UnameValue);
        tvnomortelp.setText(no_telp);
        tvemailuser.setText(email_user);
        Toast.makeText(MainMenuActivity.this, tvemailuser.getText().toString(), Toast.LENGTH_LONG).show();
        getProfile();

        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }
}
