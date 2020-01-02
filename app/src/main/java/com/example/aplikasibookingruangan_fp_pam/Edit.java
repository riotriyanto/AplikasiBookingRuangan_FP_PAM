package com.example.aplikasibookingruangan_fp_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Edit extends AppCompatActivity {

    EditText mruangan, mtgl, mjam;
    Button simpan, batal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mruangan = findViewById(R.id.ruangan);
        mtgl = findViewById(R.id.tgl);
        mjam = findViewById(R.id.jam);
        simpan = findViewById(R.id.simpan);
        batal = findViewById(R.id.batal);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("ruangan");
        showEdit(nama);

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit.this, MainActivity.class);
                startActivity(intent);
            }
        });
        
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ruangan = mruangan.getText().toString();
                String tgl = mtgl.getText().toString();
                String jam = mjam.getText().toString();

                SimpanData(ruangan, tgl, jam);
            }
        });
    }

    private void showEdit(String nama) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.URL+"book?id="+nama, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean res = false;
                        String mess = null;
                        JSONArray data =null;
                        try {
                            res = response.getBoolean("success");
                            mess = response.getString("message");
                            data = response.getJSONArray("data");
                            Log.d("log", "data"+data.getJSONObject(0).getString("id_booking"));
                            mruangan.setText(data.getJSONObject(0).getString("urai_ruangan"));
                            mtgl.setText(data.getJSONObject(0).getString("tgl"));
                            mjam.setText(data.getJSONObject(0).getString("jam"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        textView.append(response.getString("message"));

                        if (res){
                        }else{
                            Toast.makeText(getApplicationContext() ,mess, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Edit.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext() ,error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void SimpanData(String ruangan, String tgl, String jam) {
        Intent intent = getIntent();
        String id = intent.getStringExtra("ruangan");

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ruangan", ruangan);
            jsonObject.put("tgl",tgl);
            jsonObject.put("jam",jam);
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Server.URL+"book?id="+id, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean res = false;
                        String mess = null;
                        try {
                            res = response.getBoolean("success");
                            mess = response.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        textView.append(response.getString("message"));

                        if (res){
                            Toast.makeText(getApplicationContext() ,mess, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext() ,mess, Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(Edit.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext() ,error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
