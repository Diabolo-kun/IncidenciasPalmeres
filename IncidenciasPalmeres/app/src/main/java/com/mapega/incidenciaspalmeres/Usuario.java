package com.mapega.incidenciaspalmeres;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private String gmail;
    private String dni;
    private int numero_telefono;
    private String contrasena;
    private String puesto;
    private String descripcion;
    private int tipo_permiso;

    public Usuario(int id, String nombre, String gmail, String dni, int numero_telefono, String contrasena, String puesto, String descripcion, int tipo_permiso) {
        this.id = id;
        this.nombre = nombre;
        this.gmail = gmail;
        this.dni = dni;
        this.numero_telefono = numero_telefono;
        this.contrasena = contrasena;
        this.puesto = puesto;
        this.descripcion = descripcion;
        this.tipo_permiso = tipo_permiso;
    }

    public Usuario() {
        // Se asignan valores por defecto a los atributos de la clase
        this.id = 0;
        this.nombre = "";
        this.gmail = "";
        this.dni = "";
        this.numero_telefono = 0;
        this.contrasena = "";
        this.puesto = "";
        this.descripcion = "";
        this.tipo_permiso = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getNumero_telefono() {
        return numero_telefono;
    }

    public void setNumero_telefono(int numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo_permiso() {
        return tipo_permiso;
    }

    public void setTipo_permiso(int tipo_permiso) {
        this.tipo_permiso = tipo_permiso;
    }
}
