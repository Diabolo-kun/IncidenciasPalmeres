package com.mapega.incidenciaspalmeres;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mapega.incidenciaspalmeres.ObjectClass.Usuario;

public class Mantenimiento extends AppCompatActivity {

    private EditText productoInput;
    private EditText descripcionInput;
    private Button crearProductoButton;
    private Usuario user; // variable de instancia para guardar el usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_mantenimiento);

        // Obtener el usuario del Intent
        Intent intent = getIntent();
        user = (Usuario) intent.getSerializableExtra("usuario");

        // Referenciamos los elementos del formulario
        productoInput = findViewById(R.id.titulo_input);
        descripcionInput = findViewById(R.id.descripcion_input);
        crearProductoButton = findViewById(R.id.button_crear_mantenimiento);

        // Asignamos un listener al botón
        crearProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzamos un método asíncrono para ejecutar addAlmacen de la clase Conexion
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        return Conexion.addMantenimiento(productoInput.getText().toString(),
                                user.getId(),
                                descripcionInput.getText().toString());
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            Toast.makeText(Mantenimiento.this, "Producto creado correctamente", Toast.LENGTH_SHORT).show();
                            // Limpiamos los campos del formulario
                            productoInput.setText("");
                            descripcionInput.setText("");
                            finish();
                        } else {
                            Toast.makeText(Mantenimiento.this, "Error al crear el producto", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
            }
        });
    }
}