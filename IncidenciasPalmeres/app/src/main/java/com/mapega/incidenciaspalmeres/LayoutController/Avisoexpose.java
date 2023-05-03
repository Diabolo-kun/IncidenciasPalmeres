package com.mapega.incidenciaspalmeres.LayoutController;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapega.incidenciaspalmeres.ObjectClass.Aviso;
import com.mapega.incidenciaspalmeres.R;

public class Avisoexpose extends AppCompatActivity {

    TextView titulo;
    TextView fechaCreacion;
    TextView idUsuarioCreador;
    TextView descripcion;
    ImageView nivelPrioridad;
    Aviso aviso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avisoexpose);

        Intent intent = getIntent();
        aviso = (Aviso) intent.getSerializableExtra("aviso");

        titulo = findViewById(R.id.titulo_aviso);
        fechaCreacion = findViewById(R.id.fecha_creacion);
        idUsuarioCreador = findViewById(R.id.usuario_creador);
        descripcion = findViewById(R.id.descripcion);
        nivelPrioridad = findViewById(R.id.nivel_prioridad);

        titulo.setText(aviso.getTitulo());
        fechaCreacion.setText(aviso.getFechaCreacion().toString());
        idUsuarioCreador.setText(String.valueOf(aviso.getIdUsuarioCreador()));
        descripcion.setText(aviso.getDescripcion());
        if (aviso.getNivel_prioridad() == 1) {
            nivelPrioridad.setImageResource(R.drawable.ic_importance_1);
        }else if (aviso.getNivel_prioridad() == 2) {
            nivelPrioridad.setImageResource(R.drawable.ic_importance_2);
        }else if (aviso.getNivel_prioridad() == 3) {
            nivelPrioridad.setImageResource(R.drawable.ic_importance_3);
        }else{
            nivelPrioridad.setImageResource(R.drawable.ic_importance_ukn);
        }
    }
}