package com.mapega.incidenciaspalmeres.LayoutController;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.mapega.incidenciaspalmeres.Conexion;
    import com.mapega.incidenciaspalmeres.ObjectClass.Usuario;
    import com.mapega.incidenciaspalmeres.R;

public class EditProfile extends AppCompatActivity {
    private TextView idActualTextView, nombreActualTextView, gmailActualTextView, dniActualTextView, ntelActualTextView, passActualTextView, puestoActualTextView, permisosActualTextView;
    private EditText nombreNuevoEditText, gmailNuevoEditText, ntelNuevoEditText, passNuevoEditText;
    private Button cancelarButton, guardarButton;
    private Usuario user; // variable de instancia para guardar el usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_edit_profile);

        // Obtener el usuario del Intent
        Intent intent = getIntent();
        user = (Usuario) intent.getSerializableExtra("usuario");

        // Obtener referencias de los componentes de la interfaz
        idActualTextView = findViewById(R.id.id_actual);
        idActualTextView.setText(String.valueOf(user.getId()));
        nombreActualTextView = findViewById(R.id.Nombre_actual);
        nombreActualTextView.setText(user.getNombre());
        gmailActualTextView = findViewById(R.id.Gmail_actual);
        String texto = user.getGmail();
        int posicionArroba = texto.indexOf("@");
        if (posicionArroba != -1) {
            String parte1 = texto.substring(0, posicionArroba); // Incluye el "@"
            String parte2 = texto.substring(posicionArroba);
            // Concatenar las partes con el salto de línea
            String gmailmodificado = parte1 + "\n" + parte2;
            gmailActualTextView.setText(gmailmodificado);
        }
        dniActualTextView = findViewById(R.id.DNI_actual);
        dniActualTextView.setText(user.getDni());
        ntelActualTextView = findViewById(R.id.Ntel_actual);
        ntelActualTextView.setText(String.valueOf(user.getNumero_telefono()));
        passActualTextView = findViewById(R.id.pass_actual);
        passActualTextView.setText(user.getContrasena());
        puestoActualTextView = findViewById(R.id.puesto_actual);
        puestoActualTextView.setText(user.getPuesto());
        permisosActualTextView = findViewById(R.id.permisos_actual);
        permisosActualTextView.setText(String.valueOf(user.getTipo_permiso()));
        nombreNuevoEditText = findViewById(R.id.Nombre_nuevo);
        gmailNuevoEditText = findViewById(R.id.Gmail_nuevo);
        ntelNuevoEditText = findViewById(R.id.Ntel_nuevo);
        passNuevoEditText = findViewById(R.id.pass_nuevo);
        cancelarButton = findViewById(R.id.btncancel);
        guardarButton = findViewById(R.id.btnsave);


        // Configurar el clic del botón "Cancelar"
        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Configurar el clic del botón "Guardar"
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados en los EditText
                String nuevoNombre = nombreNuevoEditText.getText().toString();
                String nuevoGmail = gmailNuevoEditText.getText().toString();
                String nuevoNTel = ntelNuevoEditText.getText().toString();
                String nuevoPass = passNuevoEditText.getText().toString();

                // Realizar acciones con los valores obtenidos
                new UpdateProfileTask(nuevoNombre, nuevoGmail, nuevoNTel, nuevoPass, user.getId()).execute();

            }
        });
    }
    public class UpdateProfileTask extends AsyncTask<Void, Void, Void> {
        private String nuevoNombre;
        private String nuevoGmail;
        private String nuevoNTel;
        private String nuevoPass;
        private int userId;

        public UpdateProfileTask(String nuevoNombre, String nuevoGmail, String nuevoNTel, String nuevoPass, int userId) {
            this.nuevoNombre = nuevoNombre;
            this.nuevoGmail = nuevoGmail;
            this.nuevoNTel = nuevoNTel;
            this.nuevoPass = nuevoPass;
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Conexion.updateProfile(nuevoNombre, nuevoGmail, nuevoNTel, nuevoPass, userId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Aquí puedes realizar cualquier acción después de que se complete la actualización del perfil
            finish();
        }
    }

}
