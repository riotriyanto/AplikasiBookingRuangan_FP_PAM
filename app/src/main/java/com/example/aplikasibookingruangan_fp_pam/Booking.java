package com.example.aplikasibookingruangan_fp_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Booking extends AppCompatActivity {

    Button simpan, kembali;
    EditText ruangan, tgl, jam;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);



        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        simpan = findViewById(R.id.simpan);
        kembali = findViewById(R.id.batal);
        ruangan = findViewById(R.id.ruangan);
        tgl =  findViewById(R.id.tgl);
        jam =  findViewById(R.id.jam);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Booking.this, MainActivity.class);
                startActivity(intent);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String druangan = ruangan.getText().toString();
                String dtgl = tgl.getText().toString();
                String djam = jam.getText().toString();

                // mengecek kolom yang kosong
                if (druangan.trim().length() > 0 && dtgl.trim().length() > 0 && djam.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
//                        checkLogin(username, password);
                        tambahbooking(druangan, dtgl, djam);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void tambahbooking(final String druangan, final String dtgl, final String djam) {
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
//        showDialog();
//        final TextView textView = (TextView) findViewById(R.id.textView);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final JSONObject jsonObject = new JSONObject();
        final Session session = new Session();
        String id_user = session.getRegisteredPass(getBaseContext());
        try {
            jsonObject.put("id_user", id_user);
            jsonObject.put("ruangan",druangan);
            jsonObject.put("jam", djam);
            jsonObject.put("tgl",dtgl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.URL+"book", jsonObject,
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
                        Intent intent = new Intent(Booking.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.append("Error getting response"+error);
                Toast.makeText(getApplicationContext() ,error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

// add it to the RequestQueue
//        queue.add(getRequest);
    }
}
