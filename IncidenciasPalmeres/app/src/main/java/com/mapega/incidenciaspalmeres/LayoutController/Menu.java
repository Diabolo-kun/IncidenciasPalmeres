package com.mapega.incidenciaspalmeres.LayoutController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.mapega.incidenciaspalmeres.Almacen;
import com.mapega.incidenciaspalmeres.Conexion;
import com.mapega.incidenciaspalmeres.Mantenimiento;
import com.mapega.incidenciaspalmeres.ObjectClass.AlmacenListAdapter;
import com.mapega.incidenciaspalmeres.ObjectClass.Aviso;
import com.mapega.incidenciaspalmeres.ObjectClass.AvisoListAdapter;
import com.mapega.incidenciaspalmeres.ObjectClass.IncidenciaAlmacen;
import com.mapega.incidenciaspalmeres.ObjectClass.IncidenciaMantenimiento;
import com.mapega.incidenciaspalmeres.ObjectClass.MantenimientoListAdapter;
import com.mapega.incidenciaspalmeres.ObjectClass.Usuario;
import com.mapega.incidenciaspalmeres.R;

import java.util.List;

public class Menu extends AppCompatActivity {
    private Usuario user; // variable de instancia para guardar el usuario
    private NavigationView navigationView;
    private android.view.Menu mMenu;
    public  int lista= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Obtener el usuario del Intent
        Intent intent = getIntent();
        user = (Usuario) intent.getSerializableExtra("usuario");

        // Imprimir los detalles del usuario en la consola
        System.out.println("ID: " + user.getId());
        System.out.println("Nombre: " + user.getNombre());
        System.out.println("Email: " + user.getGmail());
        System.out.println("DNI: " + user.getDni());
        System.out.println("Teléfono: " + user.getNumero_telefono());
        System.out.println("Puesto: " + user.getPuesto());
        System.out.println("Descripción: " + user.getDescripcion());
        System.out.println("Tipo permiso: " + user.getTipo_permiso());
        new GetUsuarioTask().execute(user.getId());

        updateNavigationMenu();
        avisosList();
        // Menu lateral
        final DrawerLayout drawerLayout= findViewById(R.id.drawerLay);

        findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.imgreload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        navigationView = findViewById(R.id.nav);
        mMenu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Manejar la selección de item
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.editar_perfil:
                        // Abrir la actividad para editar el perfil del usuario
                        intent = new Intent(Menu.this, EditProfile.class);
                        intent.putExtra("usuario", user);
                        startActivity(intent);
                        return true;
                    case R.id.inicio:
                        // Abrir la actividad para ver avisos
                        avisosList();
                        lista=1;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.crear_incidencia_mantenimiento:
                        // Abrir la actividad para crear una incidencia de mantenimiento
                        intent = new Intent(Menu.this, Mantenimiento.class);
                        intent.putExtra("usuario", user);
                        startActivity(intent);
                        return true;
                    case R.id.ver_incidencias_mantenimiento:
                        // Abrir la actividad para crear una incidencia de almacenamiento
                        mantenimientoList();
                        lista=2;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.crear_incidencia_almacen:
                        // Abrir la actividad para ver las incidencias de mantenimiento
                        intent = new Intent(Menu.this, Almacen.class);
                        intent.putExtra("usuario", user);
                        startActivity(intent);
                        return true;
                    case R.id.ver_incidencias_almacen:
                        // Abrir la actividad para ver las incidencias de almacenamiento
                        almacenList();
                        lista=3;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.infoerror:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showDialog();
                        break;
                    case R.id.cerrar_sesion:
                        // Cerrar la sesión del usuario y volver a la pantalla de inicio de sesión
                        // Iniciamos la clase Login
                        intent = new Intent(Menu.this, Login.class);
                        startActivity(intent);
                        // Finalizamos la tarea actual
                        finish();
                        return true;
                    default:
                        return false;
                }
                return false;
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Obtener el usuario de forma asíncrona
        new GetUsuarioTask().execute(user.getId());
        System.out.println("ID: " + user.getId());
        System.out.println("Nombre: " + user.getNombre());
        System.out.println("Email: " + user.getGmail());
        System.out.println("DNI: " + user.getDni());
        System.out.println("Teléfono: " + user.getNumero_telefono());
        System.out.println("Puesto: " + user.getPuesto());
        System.out.println("Descripción: " + user.getDescripcion());
        System.out.println("Tipo permiso: " + user.getTipo_permiso());
    }
    private class GetUsuarioTask extends AsyncTask<Integer, Void, Usuario> {
        @Override
        protected Usuario doInBackground(Integer... params) {
            int userId = params[0];
            return Conexion.getUserId(userId);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            // Realizar las acciones necesarias con el usuario obtenido
            if (usuario != null) {
                user=usuario;
            } else {
                Toast.makeText(Menu.this, "Error con recarga de usuario", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showDialog() {
        // Inflar el diseño del diálogo desde XML
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_informe_error, null);

        final EditText editTextInforme = dialogView.findViewById(R.id.informe);
        // Crear el diálogo con el diseño inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Llamar al método informar() al pulsar el botón "Enviar"
                        String informe = editTextInforme.getText().toString();
                        new AsyncTask<Void, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(Void... voids) {
                                return Conexion.addError(user.getId(), informe);
                            }
                            @Override
                            protected void onPostExecute(Boolean result) {
                                if (!result) {
                                    Toast.makeText(Menu.this, "Error al agregar el informe de error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cerrar el diálogo
                        dialog.dismiss();
                    }
                });
        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void reload(){
        if (lista == 1) {
            avisosList();
        }else if (lista == 2) {
            mantenimientoList();
        }else if (lista == 3) {
            almacenList();
        }
    }
    private void updateNavigationMenu() {
        navigationView = findViewById(R.id.nav);
        mMenu = navigationView.getMenu();
        mMenu.findItem(R.id.editar_perfil).setVisible(true);
        mMenu.findItem(R.id.inicio).setVisible(true);
        mMenu.findItem(R.id.crear_incidencia_almacen).setVisible(true);
        mMenu.findItem(R.id.crear_incidencia_mantenimiento).setVisible(true);
        mMenu.findItem(R.id.infoerror).setVisible(true);
        mMenu.findItem(R.id.cerrar_sesion).setVisible(true);
        mMenu.findItem(R.id.ver_incidencias_mantenimiento).setVisible(false);
        mMenu.findItem(R.id.ver_incidencias_almacen).setVisible(false);
        //dependiendo de el permiso del usuario se podran usar unas u otras cosas
        switch (user.getTipo_permiso()) {
            default://usuario normal
                break;
            case 1://usuario mantenimiento
                mMenu.findItem(R.id.ver_incidencias_mantenimiento).setVisible(true);
                break;
            case 2://usuario almacen
                mMenu.findItem(R.id.ver_incidencias_almacen).setVisible(true);
                break;
            case 3://administrador
                mMenu.findItem(R.id.ver_incidencias_mantenimiento).setVisible(true);
                mMenu.findItem(R.id.ver_incidencias_almacen).setVisible(true);
                break;
        }
    }
    public void avisosList(){
        new GetAvisosTask().execute();// Ejecuto el metodo asincrono para generar la lista de avisos
    }
    public class GetAvisosTask extends AsyncTask<Void, Void, List<Aviso>> {
        @Override
        protected List<Aviso> doInBackground(Void... voids) {
            return Conexion.getAvisosList();
        }

        @Override
        protected void onPostExecute(List<Aviso> avisos) {
            AvisoListAdapter avsListAdp=new AvisoListAdapter(avisos, Menu.this);
            RecyclerView recyclerView= findViewById(R.id.RView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(avsListAdp);
        }
    }
    public void almacenList(){
        new GetAlmacenTask().execute();// Ejecuto el metodo asincrono para generar la lista de almacen
    }
    private class GetAlmacenTask extends AsyncTask<Void, Void, List<IncidenciaAlmacen>> {
        @Override
        protected List<IncidenciaAlmacen> doInBackground(Void... voids) {
            return Conexion.getAlmacenList();
        }

        @Override
        protected void onPostExecute(List<IncidenciaAlmacen> almacen) {
            AlmacenListAdapter almListAdp=new AlmacenListAdapter(almacen,Menu.this);
            RecyclerView recyclerView= findViewById(R.id.RView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(almListAdp);
        }
    }
    public void mantenimientoList(){
        new GetMantenimientoTask().execute();// Ejecuto el metodo asincrono para generar la lista de almacen
    }
    private class GetMantenimientoTask extends AsyncTask<Void, Void, List<IncidenciaMantenimiento>> {
        @Override
        protected List<IncidenciaMantenimiento> doInBackground(Void... voids) {
            return Conexion.getMantenimentoList();
        }

        @Override
        protected void onPostExecute(List<IncidenciaMantenimiento> mante) {
            MantenimientoListAdapter manListAdp=new MantenimientoListAdapter(mante,Menu.this);
            RecyclerView recyclerView= findViewById(R.id.RView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(manListAdp);
            //el codigo llega hasta aqui bien, luego termina el asincrono y vuelve al principio y peta
        }
    }
}