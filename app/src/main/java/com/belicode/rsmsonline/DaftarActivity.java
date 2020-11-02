package com.belicode.rsmsonline;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
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
import com.belicode.rsmsonline.helper.ValidEmail;
import com.belicode.rsmsonline.pdModel.pdModel;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class DaftarActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtNik, edtAddress, edtPhone, edtEmail, edtPassword, edtPasswordConfirm;
    private Button btnDaftar;
    private TextInputLayout tipName, tipNik, tipAddress, tipPhone, tipEmail, tipPass, tipPassConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        edtName = (EditText) findViewById(R.id.edtNameRegister);
        edtNik = (EditText) findViewById(R.id.edtNikRegister);
        edtAddress = (EditText) findViewById(R.id.edtAddressRegister);
        edtPhone = (EditText) findViewById(R.id.edtPhoneRegister);
        edtEmail = (EditText) findViewById(R.id.edtEmailRegister);
        edtPassword = (EditText) findViewById(R.id.edtPasswordRegister);
        edtPasswordConfirm = (EditText) findViewById(R.id.edtPasswordConfirmation);
        btnDaftar = (Button) findViewById(R.id.btnRegister);

        // TextInputLayout
        tipName = findViewById(R.id.tipNameRegister);
        tipNik = findViewById(R.id.tipNikRegister);
        tipAddress = findViewById(R.id.tipAddressRegister);
        tipPhone = findViewById(R.id.tipPhoneRegister);
        tipEmail = findViewById(R.id.tipEmailRegister);
        tipPass = findViewById(R.id.tipPasswordRegister);
        tipPassConfirm = findViewById(R.id.tipPasswordConfirmationRegister);

        btnDaftar.setOnClickListener(this);
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

                String email_user = edtEmail.getText().toString().trim();
                String ho_hp = edtPhone.getText().toString().trim();
                String nama_user = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                String alamat = edtAddress.getText().toString();
                String nip = edtNik.getText().toString();

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

    @Override
    public void onClick(View v) {
        String inputName = edtName.getText().toString().trim();
        String inputNik = edtNik.getText().toString().trim();
        String inputAddress = edtAddress.getText().toString().trim();
        String inputPhone = edtPhone.getText().toString().trim();
        String inputEmail = edtEmail.getText().toString().trim();
        String inputPass = edtPassword.getText().toString().trim();

        boolean isEmptyField = false;

        if (v.getId() == R.id.btnRegister) {
            // Input Name
            if (TextUtils.isEmpty(inputName)) {
                isEmptyField = true;
                tipName.setError(getString(R.string.toast_not_empty));
            } else {
                tipName.setError(null);
            }

            // Input Nik
            if (TextUtils.isEmpty(inputNik)) {
                isEmptyField = true;
                tipNik.setError(getString(R.string.toast_not_empty));
            } else if (edtNik.length() < 16) {
                tipNik.setError("NIK Harus 16 Digit");
                return;
            } else {
                tipNik.setError(null);
            }

            // Input Address
            if (TextUtils.isEmpty(inputAddress)) {
                isEmptyField = true;
                tipAddress.setError(getString(R.string.toast_not_empty));
            } else {
                tipAddress.setError(null);
            }

            // Input Phone Number
            if (TextUtils.isEmpty(inputPhone)) {
                isEmptyField = true;
                tipPhone.setError(getString(R.string.toast_not_empty));
            } else if (edtPhone.length() < 11) {
                tipPhone.setError(getString(R.string.digit_phone));
                return;
            } else {
                tipPhone.setError(null);
            }

            // Input Email
            if (TextUtils.isEmpty(inputEmail)) {
                isEmptyField = true;
                tipEmail.setError(getString(R.string.toast_not_empty));
            } else if (!ValidEmail.isValidEmail(edtEmail.getText().toString().trim())) {
                tipEmail.setError(getString(R.string.email_not_valid));
                return;
            } else {
                tipEmail.setError(null);
            }

            // Input Password
            if (TextUtils.isEmpty(inputPass)) {
                isEmptyField = true;
                tipPass.setError(getString(R.string.toast_not_empty));
            } else if (edtPassword.length() < 6) {
                tipPass.setError(getString(R.string.digit_password));
                return;
            } else {
                tipPass.setError(null);
            }

            if (!edtPassword.getText().toString().equals(edtPasswordConfirm.getText().toString())) {
                tipPassConfirm.setError("Password Harus Sama");
                return;
            } else {
                tipPassConfirm.setError(null);
            }

            if (!isEmptyField) {
                getAkun(edtEmail.getText().toString());
            }
        }
    }

    public void btnLoginNow(View view) {
        startActivity(new Intent(DaftarActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
