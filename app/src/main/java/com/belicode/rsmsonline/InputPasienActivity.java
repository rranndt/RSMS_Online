package com.belicode.rsmsonline;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.belicode.rsmsonline.pdModel.pdModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class InputPasienActivity extends AppCompatActivity {
    EditText edtnama, edtnip, edtalamat, edtnotelp, edttgllahir;
    Spinner spJK;
    TextView tviduser;
    Button btnDaftar, btntgllahir;
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    int hour, minute, mYear, mMonth, mDay;
    int hour2, minute2, mYear2, mMonth2, mDay2;
    private String[] arrMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputpasien);
        edtnama = (EditText) findViewById(R.id.edtNameRegister);
        edtnip = (EditText) findViewById(R.id.edtNikRegister);
        edtalamat = (EditText) findViewById(R.id.edtAddressRegister);
        edtnotelp = (EditText) findViewById(R.id.edtPhoneRegister);
        edttgllahir = (EditText) findViewById(R.id.input_tgl_lahir);
        spJK = (Spinner) findViewById(R.id.sp_jeniskelamin);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        btnDaftar = (Button) findViewById(R.id.btnRegister);
        btntgllahir = (Button) findViewById(R.id.btntgl_lahir);
        btntgllahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        tviduser = (TextView) findViewById(R.id.tvIduser);
        spjenis();
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputPasien();
            }
        });
    }

    private void spjenis() {
        List<String> list = new ArrayList<String>();
        list.add("Laki-laki");
        list.add("Perempuan");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spJK.setAdapter(dataAdapter);
    }

    public void inputPasien() {

        pdModel.pdData(InputPasienActivity.this);
        String url = ConfigApp.SERVERAPP + "inputpasien.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(InputPasienActivity.this, "Succes registrasions...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InputPasienActivity.this, PilihKlik.class);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(InputPasienActivity.this, "Not connections", Toast.LENGTH_LONG).show();

                        pdModel.hideProgressDialog();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tgl_lahir = edttgllahir.getText().toString();
                String ho_hp = edtnotelp.getText().toString();
                String nama_user = edtnama.getText().toString();
                String jenis_kelamin = spJK.getSelectedItem().toString();
                String alamat = edtalamat.getText().toString();
                String nip = edtnip.getText().toString();
                String id_user = tviduser.getText().toString();
                Map<String, String> params = new Hashtable<String, String>();

                params.put("nama_pasien", nama_user);
                params.put("nip", nip);
                params.put("jenis_kelamin", jenis_kelamin);

                params.put("no_telp", ho_hp);
                params.put("tgl_lahir", tgl_lahir);
                params.put("alamat", alamat);

                params.put("id_user", id_user);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(InputPasienActivity.this);
        requestQueue.add(stringRequest);
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

        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID:
                return new DatePickerDialog(
                        this, mDateSetListener, mYear, mMonth, mDay);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    String sdate = arrMonth[mMonth] + " " + LPad(mDay + "", "0", 2) + ", " + mYear;
                    edttgllahir.setText(sdate);
                }
            };

    private static String LPad(String schar, String spad, int len) {
        String sret = schar;
        for (int i = sret.length(); i < len; i++) {
            sret = spad + sret;
        }
        return new String(sret);
    }


}
