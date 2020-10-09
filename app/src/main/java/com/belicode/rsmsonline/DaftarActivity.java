package com.belicode.rsmsonline;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.belicode.rsmsonline.pdModel.pdModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class DaftarActivity extends AppCompatActivity {
    EditText edtnama, edtnip, edtalamat, edtnotelp, edtemail, edtpassword;
    Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        edtnama = (EditText) findViewById(R.id.input_nama);
        edtnip = (EditText) findViewById(R.id.input_nip);
        edtalamat = (EditText) findViewById(R.id.input_alamatl);
        edtnotelp = (EditText) findViewById(R.id.input_notelp);
        edtemail = (EditText) findViewById(R.id.input_email);
        edtpassword = (EditText) findViewById(R.id.input_password);
        btnDaftar = (Button) findViewById(R.id.btn_daftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAkun(edtemail.getText().toString());
            }
        });
        LinearLayout mAdViewLayout = findViewById(R.id.adView);
        Iklan.TampilkanBanner(getApplicationContext(), mAdViewLayout);
    }

    public void regisUserMining() {

        pdModel.pdData(DaftarActivity.this);
        String url = ConfigApp.SERVERAPP + "registrasi_user.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(DaftarActivity.this, "Succes registrasions...", Toast.LENGTH_LONG).show();
                        pdModel.hideProgressDialog();
                        startActivity(new Intent(DaftarActivity.this, MainActivity.class));


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(DaftarActivity.this, "Not connections", Toast.LENGTH_LONG).show();

                        pdModel.hideProgressDialog();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String email_user = edtemail.getText().toString().trim();
                String ho_hp = edtnotelp.getText().toString().trim();
                String nama_user = edtnama.getText().toString();
                String password = edtpassword.getText().toString();
                String alamat = edtalamat.getText().toString();
                String nip = edtnip.getText().toString();

                Map<String, String> params = new Hashtable<String, String>();

                params.put("nama_user", nama_user);
                params.put("nip", nip);
                params.put("alamat", alamat);

                params.put("no_telp", ho_hp);
                params.put("email_user", email_user);
                params.put("password", password);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DaftarActivity.this);
        requestQueue.add(stringRequest);
    }

    public void getAkun(String email_user) {
        pdModel.pdLogin(DaftarActivity.this);
        String url = ConfigApp.SERVERAPP + "cek.php";
        StringRequest stringRequest2 = new StringRequest(url + "?email_user=" + email_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";

                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_user");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (nama.equals("tersedia")) {
                    regisUserMining();
                } else {
                    Toast.makeText(DaftarActivity.this, "Email Sudah Digunakan", Toast.LENGTH_LONG).show();
                }

                pdModel.hideProgressDialog();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DaftarActivity.this, "Not Connections", Toast.LENGTH_LONG).show();
                        pdModel.hideProgressDialog();

                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(DaftarActivity.this);
        requestQueue2.add(stringRequest2);
    }
}
