package com.mapega.incidenciaspalmeres.Request;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mapega.incidenciaspalmeres.Conexion;
import com.mapega.incidenciaspalmeres.R;

public class Request extends AppCompatActivity {
    private EditText mEmail, mDNI, mDescripcion;
    private Button mEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulariosolicitud);

        mEmail = findViewById(R.id.mail);
        mDNI = findViewById(R.id.dni);
        mDescripcion = findViewById(R.id.des);
        mEnviar = findViewById(R.id.env);

        mEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String dni = mDNI.getText().toString();
                String descripcion = mDescripcion.getText().toString();

                // Llamar a request() usando una tarea asincrónica
                new RequestTask(email, dni, descripcion).execute();
            }
        });
    }

    private class RequestTask extends AsyncTask<Void, Void, Boolean> {
        private String mEmail, mDNI, mDescripcion;

        public RequestTask(String email, String dni, String descripcion) {
            mEmail = email;
            mDNI = dni;
            mDescripcion = descripcion;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return Conexion.addRequest(mEmail, mDNI, mDescripcion);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(Request.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(Request.this, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
            }
        }
    }
}