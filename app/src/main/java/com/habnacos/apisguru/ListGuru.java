package com.habnacos.apisguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.habnacos.apisguru.model.ApiGuru;
import com.habnacos.apisguru.model.GuruAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListGuru extends AppCompatActivity {
    private final String URL = "https://api.apis.guru/v2/list.json";
    ArrayList<ApiGuru> apiGurus = new ArrayList<>();
    RecyclerView listViewGuru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_guru);

        listViewGuru = findViewById(R.id.listViewGuru);
        listViewGuru.setLayoutManager(new LinearLayoutManager(this));

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
        int count = 0;
        while (temp.hasNext()) {
            try {
                String key = temp.next();
                JSONObject guru = (JSONObject) response.get(key);
                guru = (JSONObject) guru.get("versions");
                key = guru.keys().next();
                guru = (JSONObject) guru.get(key);
                guru = (JSONObject) guru.get("info");
                JSONObject contact = (JSONObject) guru.get("contact");
                JSONObject x_logo = (JSONObject) guru.get("x-logo");

                apiGurus.add(new ApiGuru(
                        (String) contact.get("email"),
                        (String) contact.get("name"),
                        (String) contact.get("name"),
                        (String) guru.get("title"),
                        (String) guru.get("description"),
                        (String) guru.get("version"),
                        (String) x_logo.get("url")
                ));

                if (count++ == 29)
                    break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        GuruAdapter guruAdapter = new GuruAdapter(getApplicationContext(), apiGurus, listViewGuru);
    }
}