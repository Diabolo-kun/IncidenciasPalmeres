package com.mapega.incidenciaspalmeres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private CheckBox mRemember;
    private Button mLogin, mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Verificar conexión usando una tarea asincrónica
        new TestConnectionTask().execute();

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRemember = findViewById(R.id.remember);
        mLogin = findViewById(R.id.buttonin);
        mRequest = findViewById(R.id.buttonsol);

        // Método para abrir el menu iniciando sesion
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                boolean remember = mRemember.isChecked();

                // Llamar a login() usando una tarea asincrónica
                new LoginTask(email, password, remember).execute();
            }
        });

        // Método para abrir la actividad de registro
        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Request.class);
                startActivity(intent);
            }
        });
    }

    // Tarea asincrónica para verificar conexión
    private class TestConnectionTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return Conexion.testConnection();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(Login.this, "Error en la conexión de prueba", Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(Login.this, "Conexión", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Tarea asincrónica para realizar inicio de sesión
    private class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private String mEmail, mPassword;
        private boolean mRemember;

        public LoginTask(String email, String password, boolean remember) {
            mEmail = email;
            mPassword = password;
            mRemember = remember;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return Conexion.login(mEmail, mPassword);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                if (mRemember) {
                    // guardar gmail y contrasena en SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                    preferences.edit().putString("gmail", mEmail).apply();
                    preferences.edit().putString("contrasena", mPassword).apply();
                }

                // abrir Menu y pasar el correo electrónico del usuario como extra
                Intent intent = new Intent(Login.this, Menu.class);
                intent.putExtra("gmail", mEmail); // mEmail es el correo electrónico del usuario
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(Login.this, "Error de inicio de sesión", Toast.LENGTH_SHORT).show();
            }
        }
    }
}