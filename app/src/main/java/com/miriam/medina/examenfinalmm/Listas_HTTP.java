package com.miriam.medina.examenfinalmm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.miriam.medina.examenfinalmm.modelo.Usuarios;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Listas_HTTP extends AppCompatActivity {
    Button btnAgrega;
    RecyclerView rvLista;
    List<Usuarios> modeloList = new ArrayList<Usuarios>();
    //para el dialog
    EditText deleteid;

    public static int listPosicion = 0;
    private adaptador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas_http);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        controles();
        setup();

    }



    private void setup() {
        metodoGET();

        btnAgrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                metodoPOST();
            }
        });
    }

    private void rvEventos() {
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val ="PUT";
                listPosicion = rvLista.getChildAdapterPosition(view);
                Intent intent = new Intent(getApplicationContext(), ActualizarDatos.class);
                intent.putExtra("id", modeloList.get(listPosicion).getId());
                intent.putExtra("name", modeloList.get(listPosicion).getName());
                intent.putExtra("email", modeloList.get(listPosicion).getEmail());
                intent.putExtra("gender", modeloList.get(listPosicion).getGender());
                intent.putExtra("status", modeloList.get(listPosicion).getStatus());
                intent.putExtra("identificador",val.toString());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void metodoGET() {
        String url = Uri.parse(Constantes.URL_BASE + "users")
                .buildUpon()
                .build().toString();
        JsonArrayRequest peticion = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        llenarRespuestaGET(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de red", Toast.LENGTH_SHORT).show();
                    }
                }) {
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> encabezado = new HashMap<>();
                encabezado.put("Authorization", Constantes.AUTH);
                return encabezado;
            }*/

        };
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(peticion);
    }

    private void llenarRespuestaGET(JSONArray response) {
        try {
            Usuarios ma = new Usuarios();
            for (int i = 0; i < response.length(); i++) {
                ma.setId(response.getJSONObject(i).getInt("id"));
                ma.setName(response.getJSONObject(i).getString("name"));
                ma.setEmail(response.getJSONObject(i).getString("email"));
                ma.setGender(response.getJSONObject(i).getString("gender"));
                ma.setStatus(response.getJSONObject(i).getString("status"));
                modeloList.add(new Usuarios(ma.getId(), ma.getName(), ma.getEmail(), ma.getGender(), ma.getStatus()));
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvLista.setLayoutManager(layoutManager);
            adapter = new adaptador(modeloList);
            rvLista.setAdapter(adapter);
            rvEventos();

        } catch (Exception e) {
        }
    }

    private void metodoPOST() {
        String val ="POST";
        Intent intent = new Intent(getApplicationContext(), ActualizarDatos.class);
        intent.putExtra("identificador",val.toString());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            finish();
        }
    }

    private void controles() {
        btnAgrega = findViewById(R.id.btn_agregar);
        rvLista = findViewById(R.id.rv_lista);

    }
}