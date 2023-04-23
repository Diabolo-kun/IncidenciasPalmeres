package com.mapega.incidenciaspalmeres.LayoutController;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import com.mapega.incidenciaspalmeres.ObjectClass.Aviso;
import com.mapega.incidenciaspalmeres.ObjectClass.AvisoListAdapter;
import com.mapega.incidenciaspalmeres.ObjectClass.Usuario;
import com.mapega.incidenciaspalmeres.R;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    private Usuario user; // variable de instancia para guardar el usuario
    private NavigationView navigationView;
    private android.view.Menu mMenu;

    List<Aviso> avisosList;
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

        avisosList();
        // Menu lateral
        final DrawerLayout drawerLayout= findViewById(R.id.drawerLay);

        findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
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
                        return true;
                    case R.id.inicio:
                        // Abrir la actividad para ver avisos
                        avisosList();
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
                        return true;
                    case R.id.crear_incidencia_almacen:
                        // Abrir la actividad para ver las incidencias de mantenimiento
                        intent = new Intent(Menu.this, Almacen.class);
                        intent.putExtra("usuario", user);
                        startActivity(intent);
                        return true;
                    case R.id.ver_incidencias_almacen:
                        // Abrir la actividad para ver las incidencias de almacenamiento
                        return true;
                    case R.id.crear_aviso:
                        // Abrir la actividad para crear un aviso
                        return true;
                    case R.id.ver_solicitudes:
                        // Abrir la actividad para ver las solicitudes
                        return true;
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
            }
        });

    }
    public void avisosList(){
        new GetAvisosTask().execute();// Ejecuto el metodo asincrono para generar la lista de avisos
    }
    private void updateNavigationMenu() {
        navigationView = findViewById(R.id.nav);
        mMenu = navigationView.getMenu();
        //dependiendo de el permiso del usuario se podran usar unas u otras cosas
        switch (user.getTipo_permiso()) {
            default://usuario normal
                mMenu.findItem(R.id.ver_incidencias_mantenimiento).setVisible(false);
                mMenu.findItem(R.id.ver_incidencias_almacen).setVisible(false);
                mMenu.findItem(R.id.crear_aviso).setVisible(false);
                mMenu.findItem(R.id.ver_solicitudes).setVisible(false);
                break;
            case 1://usuario mantenimiento
                mMenu.findItem(R.id.ver_incidencias_mantenimiento).setVisible(true);
                mMenu.findItem(R.id.ver_incidencias_almacen).setVisible(false);
                mMenu.findItem(R.id.crear_aviso).setVisible(false);
                mMenu.findItem(R.id.ver_solicitudes).setVisible(false);
                break;
            case 2://usuario almacen
                mMenu.findItem(R.id.ver_incidencias_mantenimiento).setVisible(false);
                mMenu.findItem(R.id.ver_incidencias_almacen).setVisible(true);
                mMenu.findItem(R.id.crear_aviso).setVisible(false);
                mMenu.findItem(R.id.ver_solicitudes).setVisible(false);
                break;
            case 3://administrador
                mMenu.findItem(R.id.ver_incidencias_mantenimiento).setVisible(true);
                mMenu.findItem(R.id.ver_incidencias_almacen).setVisible(true);
                mMenu.findItem(R.id.crear_aviso).setVisible(true);
                mMenu.findItem(R.id.ver_solicitudes).setVisible(true);
                break;
        }
    }private class GetAvisosTask extends AsyncTask<Void, Void, List<Aviso>> {

        @Override
        protected List<Aviso> doInBackground(Void... voids) {
            return Conexion.getAvisosList();
        }

        @Override
        protected void onPostExecute(List<Aviso> avisos) {
            AvisoListAdapter avsListAdp=new AvisoListAdapter(avisos,Menu.this);
            RecyclerView recyclerView= findViewById(R.id.RView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(avsListAdp);

        }
    }

}