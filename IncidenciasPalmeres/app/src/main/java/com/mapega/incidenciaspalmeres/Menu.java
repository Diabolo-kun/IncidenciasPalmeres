package com.mapega.incidenciaspalmeres;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Menu extends AppCompatActivity {
    private String mEmail; // variable de instancia para guardar el correo electrónico del usuario
    private int permiso=0;

    private NavigationView navigationView;
    private android.view.Menu mMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // obtener el correo electrónico del usuario del Intent
        Intent intent = getIntent();
        mEmail = intent.getStringExtra("email");

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return Conexion.getUserPermission(mEmail);
            }

            @Override
            protected void onPostExecute(Integer permission) {
                // actualizar la interfaz de usuario dependiendo del permiso obtenido

                switch (permission) {
                    default:
                        permiso=0;
                        break;
                    case 1:
                        permiso=1;
                        break;
                    case 2:
                        permiso=2;
                        break;
                    case 3:
                        permiso=3;
                        break;
                }
                updateNavigationMenu();
            }
        }.execute();

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
                switch (item.getItemId()) {
                    case R.id.editar_perfil:
                        // Abrir la actividad para editar el perfil del usuario
                        return true;
                    case R.id.crear_incidencia_mantenimiento:
                        // Abrir la actividad para crear una incidencia de mantenimiento
                        return true;
                    case R.id.ver_incidencias_mantenimiento:
                        // Abrir la actividad para crear una incidencia de almacenamiento
                        return true;
                    case R.id.crear_incidencia_almacen:
                        // Abrir la actividad para ver las incidencias de mantenimiento
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
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
    private void updateNavigationMenu() {
        navigationView = findViewById(R.id.nav);
        mMenu = navigationView.getMenu();
        //dependiendo de el permiso del usuario se podran usar unas u otras cosas
        switch (permiso) {
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
    }
}