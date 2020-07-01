package com.example.tpfinal.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpfinal.Cliente;
import com.example.tpfinal.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SolicitudDatos extends AppCompatActivity {

    private RequestQueue mQueue;
    ListView listView;
    List<String> list = new ArrayList<>();
    ArrayAdapter adapter;

    private ArrayList<Cliente> clients;

    LocationManager locationManager;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    Comparator<Cliente> porDistancia = new Comparator<Cliente>() {
        @Override
        public int compare(Cliente a, Cliente b) {
            return Float.compare(a.getDistanciaUsuario(), b.getDistanciaUsuario());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_datos);
        final Button button = findViewById(R.id.button);
        listView = findViewById(R.id.lista);
        mQueue = Volley.newRequestQueue(this);

        clients  = new ArrayList<Cliente>();
        jsonParse();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, '0');
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, mLocationListener);

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
    }

    private void ordenarLista(){

        Location temp;
        for(int x = 0; x< clients.size(); x++){
            temp = new Location("");
            temp.setLatitude(((Cliente) clients.get(x)).getLatitud());
            temp.setLongitude(((Cliente) clients.get(x)).getLongitud());

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, '0');
                return;
            }
            ((Cliente) clients.get(x)).setDistanciaUsuario(temp.distanceTo(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)));
            //list.add(new TextView(this));
        }

        Collections.sort(clients, porDistancia);
        for(int x = 0; x< clients.size(); x++) {
            list.add("Razón social: " + ((Cliente) clients.get(x)).getDescripcion() + "\n" +
                    "Latitud: " + ((Cliente) clients.get(x)).getLatitud() + " | Longitud: " + ((Cliente) clients.get(x)).getLongitud() +"\n" +
                    "Domicilio: " + ((Cliente) clients.get(x)).getDomicilio() + "\n" +
                    "Telefono: " + ((Cliente) clients.get(x)).getTelefono() + "\n" +
                    "Deuda: " + ((Cliente) clients.get(x)).getValor());
        }
        adapter = new ArrayAdapter(SolicitudDatos.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    private void jsonParse() {
        String url = "http://ppc.edit.com.ar:8080/resources/datos/deudas/-34.581727/-60.931513";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int x = 0; x<response.length(); x++){
                    try {
                        JSONObject cliente = response.getJSONObject(x);
                        Gson gson  = new Gson();
                        Cliente c = gson.fromJson(cliente.toString(), Cliente.class);
                        clients.add(c);
                        System.out.println(c.getDescripcion());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ordenarLista();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
       mQueue.add(request);
    }
}
