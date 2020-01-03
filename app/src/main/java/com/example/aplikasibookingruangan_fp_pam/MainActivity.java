package com.example.aplikasibookingruangan_fp_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasibookingruangan_fp_pam.Adapter.AdapterData;
import com.example.aplikasibookingruangan_fp_pam.Model.ModelData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<ModelData> mItem;
    ProgressDialog pd;
    Button btn_logout, tambah;
    TextView txt_id, txt_username;
    String id, username;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_username = (TextView) findViewById(R.id.txt_username);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        tambah = (Button) findViewById(R.id.tambah);
mItem = new ArrayList<>();
//kmentar



        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        final Session session = new Session();
        txt_username.setText("USERNAME : " + session.getRegisteredUser(getBaseContext()));
        if (session.getLoggedInStatus(getBaseContext()) == false){
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
                session.clearLoggedInUser(getBaseContext());
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Booking.class);
                startActivity(intent);
            }
        });

        LoadJson();
//        pd = new ProgressDialog(this);
        mRecyclerview = (RecyclerView) findViewById(R.id.viewe);
        mAdapter = new AdapterData(MainActivity.this, mItem);
//
        mManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
//
//
       mRecyclerview.setAdapter(mAdapter);


    }
    private void LoadJson(){
//        pd.setMessage("Mengambil Data");
//        pd.setCancelable(false);
//        pd.show();
        final Session session = new Session();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.URL+"book?id_user="+session.getRegisteredPass(getBaseContext()), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        pd.cancel();
                        Log.d("volley", "response" + response.toString());
                        JSONArray mess = null;
                        try {
                            mess = response.getJSONArray("data");
                            Log.d("voley","mes"+mess.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i=0; i < mess.length(); i++){
                            try {
                                JSONObject data = mess.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setRuangan(data.getString("urai_ruangan"));
                                md.setId(data.getString("id_booking"));
                                Log.d("p","pes"+data.getString("urai_ruangan"));
                               mItem.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                pd.cancel();
                Log.d("voley", "error"+error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
}
