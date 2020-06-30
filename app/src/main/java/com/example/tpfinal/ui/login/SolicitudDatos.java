package com.example.tpfinal.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpfinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SolicitudDatos extends AppCompatActivity {
    private TextView TextViewDatos;
    private RequestQueue mQueue;

    private void jsonParse() {
        String url = "http://ppc.edit.com.ar:8080/resources/datos/deudas/-34.581727/-60.931513";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int x = 0; x<response.length(); x++){
                    try {
                        JSONObject cliente = response.getJSONObject(x);
                        String descripcion = cliente.getString("descripcion");
                        int latitud = cliente.getInt("latitud");
                        int longitud = cliente.getInt("longitud");
                        String domicilio = cliente.getString("domicilio");
                        String telefono = cliente.getString("telefono");
                        String valor = cliente.getString("valor");
                        String detalle = cliente.getString("detalle");
                        String tipo = cliente.getString("tipo");
                        int distancia = cliente.getInt("distancia");
                        TextViewDatos.append(descripcion + ", " + String.valueOf(latitud) + ", " + String.valueOf(longitud) +
                                ", " + domicilio + ", " + telefono + ", " + valor + ", " + detalle + ", " + tipo + ", " + String.valueOf(latitud) +"/n/n)");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_datos);
        TextViewDatos = findViewById(R.id.text_view_datos);
        Button buttonParse = findViewById(R.id.button_parse);
        final Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("caller", "LoginActivity");
                i.putExtra("borrar",1);
                startActivity(i);
                finish();
            }
        });
        mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
    });
    }
}
