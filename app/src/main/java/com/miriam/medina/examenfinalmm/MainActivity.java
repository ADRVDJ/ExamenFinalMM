package com.miriam.medina.examenfinalmm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etContra;
    Button btnLogin;
    ImageView imLogo;

    private Button btnLista, btnCamara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        darVariables();
        Eventos();
    }
    private void darVariables(){
        btnLista = (Button) findViewById(R.id.buttonirLista);
        btnCamara= (Button) findViewById(R.id.buttonCamara);
    }

    private void Eventos(){
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Listas_HTTP.class);
                startActivity(intent);
            }
        });
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsoCamara.class);
                startActivity(intent);
            }
        });
    }
}