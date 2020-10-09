package com.belicode.rsmsonline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.belicode.rsmsonline.klinik.Klinik;
import com.belicode.rsmsonline.klinik.KlinikAdapter;
import com.belicode.rsmsonline.klinik.KlinikJSON;
import com.belicode.rsmsonline.pdModel.pdModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class PilikKlik2 extends AppCompatActivity implements ListView.OnItemClickListener {
    List<Klinik> pList;
    ListView lv;
    TextView tviduser,tvdilayani, tvidpasien, tvnomor, tvidklinik, tvnomortelp;

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_NO = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private String no_telp;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listip);
        tvdilayani = (TextView) findViewById(R.id.tvdilayani);
        tviduser = (TextView) findViewById(R.id.tvIduser);
        tvnomortelp = (TextView) findViewById(R.id.tvnomortelp);
        tvidklinik = (TextView) findViewById(R.id.tvidklinik);
        Intent intent=getIntent();
        tvnomor = (TextView) findViewById(R.id.tvnomor);
        tvidpasien = (TextView) findViewById(R.id.tvidpasien);
        tvidpasien.setText(intent.getStringExtra("id_p"));

        lv = (ListView) findViewById(R.id.myList);
        lv.setOnItemClickListener(this);
        requestData( ConfigApp.SERVERAPP+"listklinik.php");
        new AlertDialog.Builder(PilikKlik2.this)
                .setMessage("Pilih Klinik Sesuai Yang Di Butuhkan")
                .setTitle("Pesan")
                .setCancelable(false)
                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        lanjutan();

                        //lm.clearTestProviderLocation(provider);
                    }
                })



                .show();


    }
    public void lanjutan(){
        new AlertDialog.Builder(PilikKlik2.this)
                .setMessage("Jika Klinik Tutup Antrian Tidak Dapat Di Proses")
                .setTitle("Jangan Pilih Jika Klinik Tutup")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        //lm.clearTestProviderLocation(provider);
                    }
                })



                .show();
    }
    public void requestData(String uri) {

        StringRequest request = new StringRequest(uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pList = KlinikJSON.parseData(response);
                        KlinikAdapter adapter = new KlinikAdapter(PilikKlik2.this, pList);
                        lv.setAdapter(adapter);
                        pdModel.hideProgressDialog();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //    Toast.makeText(PengumumanActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        pdModel.hideProgressDialog();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Intent intent = new Intent(this, DetailPengumumanActivity.class);
        Klinik p = pList.get(i);
        tvidklinik.setText(p.getId_klinik());

        //intent.putExtra(Config.TAG_MAPEL, p.getJudul());
        //intent.putExtra(Config.TAG_KELAS, p.getDetail());
        //intent.putExtra(Config.TAG_SUB_KELAS, p.getTgl());
        //intent.putExtra(Config.TAG_TGL,p.getImage());

        //startActivity(intent);
        if (tvidklinik.getText().equals("")) {
            Toast.makeText(PilikKlik2.this, "Id BelumDi Pilih", Toast.LENGTH_LONG).show();
        } else {
            pdModel.pdLogin(PilikKlik2.this);
            String url = ConfigApp.SERVERAPP+"getHari.php?id_klinik=";
            StringRequest stringRequest2 = new StringRequest(url + p.getId_klinik(), new Response.Listener<String>() {
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
                    new AlertDialog.Builder(PilikKlik2.this)
                            .setMessage("Senin :"+ senin+"\n"
                                    +"Selasa :"+selasa+"\n"
                                    +"Rabu :"+rabu+"\n"
                                    +"Kamis :"+kamis+"\n"
                                    +"Jumat :"+jumat+"\n"
                                    +"Saptu :"+saptu+"\n"
                                    +"Minggu :"+minggu+"\n")
                            .setTitle(nama)
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @SuppressLint("NewApi")
                                public void onClick(DialogInterface dialog, int id) {
                                    inputantrian();
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
                   /* tvnamaklinik.setText(nama);
                    tvsenin.setText(senin);
                    tvselasa.setText(selasa);
                    tvrabu.setText(rabu);
                    tvkamis.setText(kamis);
                    tvjumat.setText(jumat);
                    tvsaptu.setText(saptu);
                    tvminggu.setText(minggu);
                    pdModel.hideProgressDialog();

                    getDokter();*/


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PilikKlik2.this, "Not Connections", Toast.LENGTH_LONG).show();
                            pdModel.hideProgressDialog();

                        }
                    });

            RequestQueue requestQueue2 = Volley.newRequestQueue(PilikKlik2.this);
            requestQueue2.add(stringRequest2);



        }
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
        no_telp = settings.getString(PREF_NO, DefaultUnameValue);
        tviduser.setText(UnameValue);
        tvnomortelp.setText(no_telp);

        getNomor();
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

    public void getNomor() {
        String url =  ConfigApp.SERVERAPP+"getNomorantrian.php";
        StringRequest stringRequest2 = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";


                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nomor_antrian");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvnomor.setText(nama);
                getDilayani();
                pdModel.hideProgressDialog();


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PilikKlik2.this, "Not Connections", Toast.LENGTH_LONG).show();

                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(PilikKlik2.this);
        requestQueue2.add(stringRequest2);
    }

    public void inputantrian() {

        String url =  ConfigApp.SERVERAPP+"inputantrian.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(PilikKlik2.this, "Succes registrasions...", Toast.LENGTH_LONG).show();
                        updateNomor();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(PilikKlik2.this, "Not connections", Toast.LENGTH_LONG).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id_pasien = tvidpasien.getText().toString().trim();
                String nomor_antrian = tvnomor.getText().toString().trim();
                String id_klinik = tvidklinik.getText().toString();
                String no_telp = tvnomortelp.getText().toString();
                String di_layani = tvdilayani.getText().toString();
                Map<String, String> params = new Hashtable<String, String>();

                params.put("id_pasien", id_pasien);
                params.put("nomor_antrian", nomor_antrian);
                params.put("id_klinik", id_klinik);
                params.put("di_layani", di_layani);
                params.put("no_telp", no_telp);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PilikKlik2.this);
        requestQueue.add(stringRequest);
    }

    public void updateNomor() {
        String url =  ConfigApp.SERVERAPP+"updatenomor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(PilikKlik2.this, "Succes ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PilikKlik2.this, MainMenuActivity.class);

                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(PilikKlik2.this, "Kode Salah", Toast.LENGTH_LONG).show();



                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String nomor_antrian = tvnomor.getText().toString().trim();


                Map<String, String> params = new Hashtable<String, String>();

                params.put("nomor_antrian", nomor_antrian);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PilikKlik2.this);

        requestQueue.add(stringRequest);

    }
    public void getDilayani() {
        String url =  ConfigApp.SERVERAPP+"getDilayani.php?nomor_antrian=";
        StringRequest stringRequest2 = new StringRequest(url + tvnomor.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";


                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("di_layani");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvdilayani.setText(nama);



            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PilikKlik2.this, "Not Connections", Toast.LENGTH_LONG).show();

                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(PilikKlik2.this);
        requestQueue2.add(stringRequest2);
    }


}
