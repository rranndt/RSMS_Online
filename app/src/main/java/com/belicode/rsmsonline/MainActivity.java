package com.belicode.rsmsonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtPass, edtEmail;
    private TextInputLayout tipEmail, tipPassword;

    private Button btnLogin;
    String email_user, password_user;
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_EMAIL = "email_user";
    private static final String PREF_NO = "Password";
    private static final String PREF_PASS = "Password2";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private String no_telp;
    private String pass;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btn_login);

        tipEmail = findViewById(R.id.tipEmail);
        tipPassword = findViewById(R.id.tipPassword);

        btnLogin.setOnClickListener(this);
    }

    public void loginMining() {
        pdModel.pdLogin(MainActivity.this);
        String url = ConfigApp.SERVERAPP + "loginuser.php";
        StringRequest stringRequest2 = new StringRequest(url + "?email_user=" + edtEmail.getText().toString().trim() + "&password=" + edtPass.getText().toString().trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String active = "";
                String tes = "";
                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("content_access");
                    JSONObject collegeData = result.getJSONObject(0);
                    active = collegeData.getString("status");
                    tes = collegeData.getString("active");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (active.equals("active")) {
                    Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                    intent.putExtra("email_user", edtEmail.getText().toString());
                    finish();
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "salah boi", Toast.LENGTH_SHORT).show();

                    pdModel.hideProgressDialog();

                } else {
//                    loginMining2();
                    Log.e(TAG, "ikan hiu makan tomat: ");
                    Toast.makeText(MainActivity.this, "Ikan hiu makan tomat : " + active, Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        pdModel.hideProgressDialog();
                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(MainActivity.this);

        requestQueue2.add(stringRequest2);
    }


    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        loadPreferences();
        super.onResume();
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = edtEmail.getText().toString();
        pass = edtPass.getText().toString();

        System.out.println("onPause save name: " + UnameValue);

        editor.putString(PREF_EMAIL, UnameValue);
        editor.putString(PREF_PASS, pass);

        editor.apply();
    }

    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_EMAIL, DefaultUnameValue);
        pass = settings.getString(PREF_PASS, DefaultUnameValue);

        edtEmail.setText(UnameValue);
        edtPass.setText(pass);

        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_panduan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_panduan) {
            Intent intent = new Intent(MainActivity.this, PanduanActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnGoogle(View view) {
        Toast.makeText(this, getString(R.string.in_development), Toast.LENGTH_SHORT).show();
    }

    public void btnFacebook(View view) {
        Toast.makeText(this, getString(R.string.in_development), Toast.LENGTH_SHORT).show();
    }

    public void ivForgotPass(View view) {
        Toast.makeText(this, getString(R.string.in_development), Toast.LENGTH_SHORT).show();
    }

    public void tvRegisterAccount(View view) {
        startActivity(new Intent(MainActivity.this, DaftarActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        String inputEmail = edtEmail.getText().toString().trim();
        String inputPassword = edtPass.getText().toString().trim();

        boolean isEmptyField = false;

        if (v.getId() == R.id.btn_login) {
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
            if (TextUtils.isEmpty(inputPassword)) {
                isEmptyField = true;
                tipPassword.setError(getString(R.string.toast_not_empty));
            } else {
                tipPassword.setError(null);
            }

            if (!isEmptyField) {
                loginMining();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
