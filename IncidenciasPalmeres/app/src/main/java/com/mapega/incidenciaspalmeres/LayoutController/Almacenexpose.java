package com.mapega.incidenciaspalmeres.LayoutController;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mapega.incidenciaspalmeres.Conexion;
import com.mapega.incidenciaspalmeres.ObjectClass.IncidenciaAlmacen;
import com.mapega.incidenciaspalmeres.R;

public class Almacenexpose extends AppCompatActivity {
    private TextView idAlmTextView, idUserAlmTextView, productoTextView, cantidadTextView, nivelPrioridadTextView, descripcionTextView, fechaCreacionTextView;
    private CheckBox pedidoCheckBox;

    private IncidenciaAlmacen alm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.almacenexpose);

        Intent intent = getIntent();
        alm = (IncidenciaAlmacen) intent.getSerializableExtra("IncidenciaAlmacen");

        idAlmTextView = findViewById(R.id.idalm);
        idUserAlmTextView = findViewById(R.id.iduseralm);
        productoTextView = findViewById(R.id.producto);
        cantidadTextView = findViewById(R.id.cantidad);
        nivelPrioridadTextView = findViewById(R.id.nvlpri);
        descripcionTextView = findViewById(R.id.desalm);
        fechaCreacionTextView = findViewById(R.id.datecrea);
        pedidoCheckBox = findViewById(R.id.pedidoalm);

        idAlmTextView.setText(String.valueOf(alm.getId()));
        idUserAlmTextView.setText(String.valueOf(alm.getId_usuario_creador()));
        productoTextView.setText(alm.getProducto());
        cantidadTextView.setText(alm.getProducto());
        nivelPrioridadTextView.setText(alm.getNivel_prioridad());
        descripcionTextView.setText(alm.getDescripcion());
        fechaCreacionTextView.setText(alm.getFecha_creacion().toString());
        pedidoCheckBox.setChecked(alm.isPedido());

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean estadoCheckBox = pedidoCheckBox.isChecked();

                // Lanzar el AsyncTask para enviar el estado de la CheckBox
                Almacenexpose.PedidoAsyncTask asyncTask = new Almacenexpose.PedidoAsyncTask();
                asyncTask.execute(alm.getId(), estadoCheckBox);
            }
        });

    }
    private class PedidoAsyncTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {
            int objetoId = (int) params[0];
            boolean estadoCheckBox = (boolean) params[1];

            Conexion.pedido(objetoId, estadoCheckBox);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
        }
    }
}
