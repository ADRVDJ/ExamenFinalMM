package com.miriam.medina.examenfinalmm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class UsoCamara extends AppCompatActivity {

    private ImageView imagensss;
    private Button btncapturar, btnsalir;
    String rutaImg;
    static final int REQUEST_TAKE_PHOTO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uso_camara);
        irVistaComponentes();
        accionesButtons();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UsoCamara.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA}, 1000);
        }
    }

    private void irVistaComponentes(){
        imagensss = (ImageView) findViewById(R.id.imageViewphoto);
        btncapturar = (Button) findViewById(R.id.buttonImg);
        btnsalir = (Button) findViewById(R.id.buttonSalir);
    }
    private void accionesButtons(){
        btncapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFoto();
            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //METODOS PARA CAPTURAR, ALMACENAR Y MOSTRAR FOTO
    private void TomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File imgFile = null;
            try {
                imgFile = crearRutaImg();
            } catch (IOException ex) {
            }
            if (imgFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.miriam.medina.examenfinalmm", imgFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }
    private File crearRutaImg() throws IOException {
        String imageFileName = "Backup_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        rutaImg = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(rutaImg);
            imagensss.setImageBitmap(imageBitmap);
        }
    }
}