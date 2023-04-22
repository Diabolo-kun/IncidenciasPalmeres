package com.mapega.incidenciaspalmeres.LayoutController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.mapega.incidenciaspalmeres.Conexion;
import com.mapega.incidenciaspalmeres.R;
import com.mapega.incidenciaspalmeres.Request.Request;
import com.mapega.incidenciaspalmeres.ObjectClass.Usuario;

public class Login extends AppCompatActivity {

    private EditText mGmail, mContrasena;
    private CheckBox mRemember;
    private Button mLogin, mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Verificar conexión usando una tarea asincrónica
        new TestConnectionTask().execute();

        mGmail = findViewById(R.id.mail);
        mContrasena = findViewById(R.id.contrasena);
        mRemember = findViewById(R.id.remember);
        mLogin = findViewById(R.id.buttonin);
        mRequest = findViewById(R.id.buttonsol);

        //Rellenar si antes pusiste algo
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        if (!email.equals("") && !password.equals("")) {
            mGmail.setText(email);
            mContrasena.setText(password);
            mRemember.setChecked(true);
        }


        // Método para abrir el menu iniciando sesion
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mGmail.getText().toString();
                String password = mContrasena.getText().toString();

                // Llamar a login() usando una tarea asincrónica
                new LoginTask(email, password).execute();
            }
        });

        // Recordar usuario y contraseña o no
        mRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                if (isChecked) {
                    // Si se activó el "Recuérdame", guardar el correo electrónico y la contraseña
                    preferences.edit().putString("email", mGmail.getText().toString())
                            .putString("password", mContrasena.getText().toString())
                            .apply();
                } else {
                    // Si se desactivó el "Recuérdame", borrar los datos guardados previamente
                    preferences.edit().remove("email").remove("password").apply();
                }
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
    private class LoginTask extends AsyncTask<Void, Void, Usuario> {
        private String mGmail, mContrasena;

        public LoginTask(String gmail, String contrasena) {
            mGmail = gmail;
            mContrasena = contrasena;

        }

        @Override
        protected Usuario doInBackground(Void... voids) {
            return Conexion.getUser(mGmail);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            if (usuario != null && usuario.getContrasena().equals(mContrasena)) {
                // abrir Menu y pasar el objeto Usuario como extra
                Intent intent = new Intent(Login.this, Menu.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "Error de inicio de sesión", Toast.LENGTH_SHORT).show();
            }
        }
    }
}