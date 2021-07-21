package com.habnacos.apisguru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class General extends AppCompatActivity {
    private final String URL = "https://api.apis.guru/v2/metrics.json";
    TextView numSpecs, numAPIs, numEndpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        numSpecs = findViewById(R.id.numSpecs);
        numAPIs = findViewById(R.id.numAPIs);
        numEndpoints = findViewById(R.id.numEndpoints);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error en la conexion", Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(jor);
    }

    private void parseJson(JSONObject response) {
        Iterator<String> temp = response.keys();
        while (temp.hasNext()) {
            try {
                String key = temp.next();

                switch (key) {
                    case "numSpecs":
                        numSpecs.setText(response.get(key).toString());
                        break;
                    case "numAPIs":
                        numAPIs.setText(response.get(key).toString());
                        break;
                    case "numEndpoints":
                        numEndpoints.setText(response.get(key).toString());
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void goBack() {
        onBackPressed();
    }
}