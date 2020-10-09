package com.belicode.rsmsonline;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.belicode.rsmsonline.klinik.Klinik;
import com.belicode.rsmsonline.klinik.KlinikAdapter;
import com.belicode.rsmsonline.klinik.KlinikJSON;

import com.belicode.rsmsonline.pdModel.pdModel;

import java.util.List;

public class ListKlinik extends AppCompatActivity implements ListView.OnItemClickListener {
    List<Klinik> pList;
    ListView lv;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listip);


        lv = (ListView) findViewById(R.id.myList);
        lv.setOnItemClickListener(this);
        requestData( ConfigApp.SERVERAPP+"listklinik.php");
        pdModel.pdData(ListKlinik.this);

    }
    public void requestData(String uri) {

        StringRequest request = new StringRequest(uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pList = KlinikJSON.parseData(response);
                        KlinikAdapter adapter = new KlinikAdapter(ListKlinik.this, pList);
                        lv.setAdapter(adapter);
                        pdModel.hideProgressDialog();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                         pdModel.hideProgressDialog();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
         Intent intent = new Intent(this, DetailKlinik.class);
        Klinik p = pList.get(i);
        intent.putExtra("id_klinik2", p.getId_klinik());


        startActivity(intent);


    }
}
