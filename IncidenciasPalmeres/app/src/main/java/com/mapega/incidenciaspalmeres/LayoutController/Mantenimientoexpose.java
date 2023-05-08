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
import com.mapega.incidenciaspalmeres.ObjectClass.IncidenciaMantenimiento;
import com.mapega.incidenciaspalmeres.R;

public class Mantenimientoexpose extends AppCompatActivity {
    private IncidenciaMantenimiento inci;
    private TextView idMantenTextView,idUserTextView,titleMantenTextView,dataMantenTextView,descriptionValueTextView;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mantenimientoexpose);

        Intent intent = getIntent();
        inci = (IncidenciaMantenimiento) intent.getSerializableExtra("IncidenciaMantenimiento");

        idMantenTextView = findViewById(R.id.idManten);
        idUserTextView = findViewById(R.id.idUser);
        titleMantenTextView = findViewById(R.id.titleManten);
        dataMantenTextView = findViewById(R.id.datamanten);
        descriptionValueTextView = findViewById(R.id.textDescriptionValue);
        checkBox = findViewById(R.id.checkBox);


        idMantenTextView.setText(String.valueOf(inci.getId()));
        idUserTextView.setText(String.valueOf(inci.getId_usuario_creador()));
        titleMantenTextView.setText(inci.getTitulo());
        dataMantenTextView.setText(String.valueOf(inci.getFecha_creacion().toString()));
        descriptionValueTextView.setText(inci.getDescripcion());
        checkBox.setChecked(inci.isDone());

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean estadoCheckBox = checkBox.isChecked();

                // Lanzar el AsyncTask para enviar el estado de la CheckBox
                DoneUpdaterAsyncTask asyncTask = new DoneUpdaterAsyncTask();
                asyncTask.execute(inci.getId(), estadoCheckBox);
            }
        });
    }

    private class DoneUpdaterAsyncTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {
            int objetoId = (int) params[0];
            boolean estadoCheckBox = (boolean) params[1];

            Conexion.doneUpdaterManten(objetoId, estadoCheckBox);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
        }
    }
}
