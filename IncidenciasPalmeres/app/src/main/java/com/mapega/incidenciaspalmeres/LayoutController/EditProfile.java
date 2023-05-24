package com.mapega.incidenciaspalmeres.LayoutController;

    import android.app.AlertDialog;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.view.LayoutInflater;
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
        gmailActualTextView.setText(user.getGmail());
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
        cancelarButton = findViewById(R.id.btncancel);
        guardarButton = findViewById(R.id.btnsave);

        // Configurar el clic del TextView "nombreActualTextView"
        nombreActualTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogName();
            }
        });

        // Configurar el clic del TextView "gmailActualTextView"
        gmailActualTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogGmail();
            }
        });

        // Configurar el clic del TextView "ntelActualTextView"
        ntelActualTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogTel();
            }
        });

        // Configurar el clic del TextView "passActualTextView"
        passActualTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogPass();
            }
        });


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
                String nuevoNombre = nombreActualTextView.getText().toString();
                String nuevoGmail = gmailActualTextView.getText().toString();
                String nuevoNTel = ntelActualTextView.getText().toString();
                String nuevoPass = passActualTextView.getText().toString();

                // Realizar acciones con los valores obtenidos
                new UpdateProfileTask(nuevoNombre, nuevoGmail, nuevoNTel, nuevoPass, user.getId()).execute();

            }
        });
    }

    private void showAlertDialogPass() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditProfile.this);
        LayoutInflater inflater = LayoutInflater.from(EditProfile.this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_pass, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();

        final EditText editCurrentPassword = dialogView.findViewById(R.id.editpassact);
        final EditText editNewPassword = dialogView.findViewById(R.id.editpassnew);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = editCurrentPassword.getText().toString().trim();
                String newPassword = editNewPassword.getText().toString().trim();

                if (currentPassword.equals(user.getContrasena()) && !newPassword.isEmpty() && !newPassword.trim().isEmpty()) {
                    passActualTextView.setText(newPassword);
                    dialog.dismiss();
                }else{
                    Toast.makeText(EditProfile.this, R.string.incorrectvalue, Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }

    private void showAlertDialogTel() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditProfile.this);
        LayoutInflater inflater = LayoutInflater.from(EditProfile.this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_item, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.newntel);

        final AlertDialog dialog = dialogBuilder.create();

        final EditText editTel = dialogView.findViewById(R.id.edit);
        TextView actualTel = dialogView.findViewById(R.id.actual);
        Button cancelBtn = dialogView.findViewById(R.id.cancelname);
        Button saveBtn = dialogView.findViewById(R.id.savename);

        actualTel.setText(ntelActualTextView.getText().toString());

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = editTel.getText().toString().trim();
                if (!newEmail.isEmpty()) {
                    gmailActualTextView.setText(newEmail);
                    dialog.dismiss();
                }else{
                    Toast.makeText(EditProfile.this, R.string.incorrectvalue, Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void showAlertDialogGmail() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditProfile.this);
        LayoutInflater inflater = LayoutInflater.from(EditProfile.this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_item, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.newgmail);

        final AlertDialog dialog = dialogBuilder.create();

        final EditText editEmail = dialogView.findViewById(R.id.edit);
        TextView actualEmail = dialogView.findViewById(R.id.actual);
        Button cancelBtn = dialogView.findViewById(R.id.cancelname);
        Button saveBtn = dialogView.findViewById(R.id.savename);

        actualEmail.setText(gmailActualTextView.getText().toString());

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = editEmail.getText().toString().trim();
                if (!newEmail.isEmpty()) {
                    gmailActualTextView.setText(newEmail);
                    dialog.dismiss();
                }else{
                    Toast.makeText(EditProfile.this, R.string.incorrectvalue, Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void showAlertDialogName() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditProfile.this);
        LayoutInflater inflater = LayoutInflater.from(EditProfile.this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_item, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.newnombre);

        final AlertDialog dialog = dialogBuilder.create();

        final EditText editName = dialogView.findViewById(R.id.edit);
        TextView actualname=dialogView.findViewById(R.id.actual);
        Button cancelBtn = dialogView.findViewById(R.id.cancelname);
        Button saveBtn = dialogView.findViewById(R.id.savename);

        actualname.setText(nombreActualTextView.getText().toString());
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Configurar el clic del botón "Guardar"
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el texto ingresado en el EditText
                String newNombre = editName.getText().toString().trim();

                // Verificar que el texto no esté vacío o lleno de espacios en blanco
                if (!newNombre.isEmpty()) {
                    // Actualizar el texto de nombreActualTextView
                    nombreActualTextView.setText(newNombre);
                    dialog.dismiss();
                }else{
                    Toast.makeText(EditProfile.this, R.string.incorrectvalue, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
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
