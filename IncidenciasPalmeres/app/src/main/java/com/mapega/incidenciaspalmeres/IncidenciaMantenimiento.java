package com.mapega.incidenciaspalmeres;

public class IncidenciaMantenimiento {
        private int id;
        private int id_usuario_creador;
        private String titulo;
        private String descripcion;
        private boolean done;

        // Constructor, getters y setters

    public IncidenciaMantenimiento(int id, int id_usuario_creador, String titulo, String descripcion, boolean done) {
        this.id = id;
        this.id_usuario_creador = id_usuario_creador;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.done = done;
    }

    public IncidenciaMantenimiento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario_creador() {
        return id_usuario_creador;
    }

    public void setId_usuario_creador(int id_usuario_creador) {
        this.id_usuario_creador = id_usuario_creador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
