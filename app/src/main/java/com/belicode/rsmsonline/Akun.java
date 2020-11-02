package com.belicode.rsmsonline;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Akun extends AppCompatActivity {
    EditText edtnama, edtnip, edtalamat, edtnotelp, edtemail, edtpassword;
    Button btnDaftar;
    String id_user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        edtnama = (EditText) findViewById(R.id.edtNameRegister);
        edtnip = (EditText) findViewById(R.id.edtNikRegister);
        edtalamat = (EditText) findViewById(R.id.edtAddressRegister);
        edtnotelp = (EditText) findViewById(R.id.edtPhoneRegister);
        edtemail = (EditText) findViewById(R.id.edtEmailRegister);
        edtpassword = (EditText) findViewById(R.id.edtPasswordRegister);
        btnDaftar = (Button) findViewById(R.id.btnRegister);
        Intent intent = getIntent();
        id_user = intent.getStringExtra("id_akun");
        getAkun();
        Toast.makeText(Akun.this, id_user, Toast.LENGTH_LONG).show();
        btnDaftar.setText("Ubdate");
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateuser();
            }
        });

    }

    public void updateuser() {

        pdModel.pdData(Akun.this);
        String url = ConfigApp.SERVERAPP + "updateuser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(Akun.this, "Succes registrasions...", Toast.LENGTH_LONG).show();
                        finish();
                        pdModel.hideProgressDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(Akun.this, "Not connections", Toast.LENGTH_LONG).show();

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
                params.put("id_user", id_user);
                params.put("nip", nip);
                params.put("alamat", alamat);

                params.put("no_telp", ho_hp);
                params.put("email_user", email_user);
                params.put("password", password);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Akun.this);
        requestQueue.add(stringRequest);
    }

    public void getAkun() {
        pdModel.pdLogin(Akun.this);
        String url = ConfigApp.SERVERAPP + "getAkun.php";
        StringRequest stringRequest2 = new StringRequest(url + "?id_user=" + id_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                String no_telp = "";
                String nip = "";
                String alamat = "";
                String email_user = "";
                String password = "";


                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_user");
                    nip = collegeData.getString("nip");
                    alamat = collegeData.getString("alamat");
                    no_telp = collegeData.getString("no_telp");
                    email_user = collegeData.getString("email_user");
                    password = collegeData.getString("password");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                edtalamat.setText(alamat);
                edtemail.setText(email_user);
                edtnama.setText(nama);
                edtpassword.setText(password);
                edtnip.setText(nip);
                edtnotelp.setText(no_telp);
                pdModel.hideProgressDialog();


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Akun.this, "Not Connections", Toast.LENGTH_LONG).show();
                        pdModel.hideProgressDialog();

                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(Akun.this);
        requestQueue2.add(stringRequest2);
    }

}
