package com.miriam.medina.examenfinalmm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarDatos extends AppCompatActivity {
    EditText etid, etname, etmail, etgender, etstatus;
    Button btnCancelar, btnActualizar;
    Bundle parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);
        parametros = this.getIntent().getExtras();
        controles();
        acciones();
        if (parametros.getString("identificador").equals("PUT")) {
            btnActualizar.setText("Actualizar");
            llenado();
        }
    }

    private void llenado() {
        etid.setText(parametros.getString("id"));
        etid.setEnabled(false);
        etname.setText(parametros.getString("name"));
        etmail.setText(parametros.getString("email"));
        etgender.setText(parametros.getString("gender"));
        etstatus.setText(parametros.getString("status"));
    }

    private void acciones() {

        if (parametros.getString("identificador").equals("PUT")) {
            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    metodoPUT();
                }
            });
        } else {
            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    metodoPOST();
                }
            });

        }


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Listas_HTTP.class));
                finish();
            }
        });
    }

    private void metodoPOST() {
        String url = Uri.parse(Constantes.URL_BASE + "users")
                .buildUpon()
                .build().toString();
        StringRequest peticion = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuestaPOST(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActualizarDatos.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> encabezado = new HashMap<>();
                encabezado.put("Authorization", Constantes.AUTH);
                return encabezado;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> encabezado = new HashMap<>();
                encabezado.put("id", etid.getText().toString());
                encabezado.put("name", etname.getText().toString());
                encabezado.put("email", etmail.getText().toString());
                encabezado.put("gender", etgender.getText().toString());
                encabezado.put("status", etstatus.getText().toString());
                return encabezado;
            }
        };
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(peticion);

    }

    private void respuestaPOST(String response) {
        try {
            JSONObject res = new JSONObject(response);
            if (res.getString("result").compareTo("ok") == 0) {
                Toast.makeText(this, "El usuario se agrego", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Listas_HTTP.class));
                finish();
            } else {
                Toast.makeText(this, "El usuario no se agrego", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }

    private void metodoPUT() {
        if (
                !etname.getText().toString().isEmpty() &&
                        !etmail.getText().toString().isEmpty() &&
                        !etgender.getText().toString().isEmpty() &&
                        !etstatus.getText().toString().isEmpty()
        ) {
            String url = Uri.parse(Constantes.URL_BASE + "users")
                    .buildUpon()
                    .appendQueryParameter("id", etid.getText().toString())
                    .appendQueryParameter("name", etname.getText().toString())
                    .appendQueryParameter("email", etmail.getText().toString())
                    .appendQueryParameter("gender", etgender.getText().toString())
                    .appendQueryParameter("status", etstatus.getText().toString())
                    .build().toString();
            JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.PUT, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            respuesta(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActualizarDatos.this, "Error de red " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> encabezado = new HashMap<>();
                    encabezado.put("Authorization", Constantes.AUTH);
                    return encabezado;
                }
            };
            RequestQueue cola = Volley.newRequestQueue(this);
            cola.add(peticion);
        } else {
            Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show();
        }

    }

    private void respuesta(JSONObject response) {
        try {
            if (response.getString("result").compareTo("ok") == 0) {
                Toast.makeText(this, "Actualizacion Exitosa", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Listas_HTTP.class));
                finish();
            } else {
                Toast.makeText(this, "Actualizacion Fallida, intenta de nuevo", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
        }
    }

    private void controles() {
        etid = findViewById(R.id.id);
        etname = findViewById(R.id.nombre);
        etmail = findViewById(R.id.email);
        etgender = findViewById(R.id.genero);
        etstatus = findViewById(R.id.estado);
        btnCancelar = findViewById(R.id.btn_ad_cancelar);
        btnActualizar = findViewById(R.id.btn_ad_actualizar);
    }
}