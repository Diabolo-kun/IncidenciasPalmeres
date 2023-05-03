package com.mapega.incidenciaspalmeres.ObjectClass;

import java.util.Date;

public class IncidenciaMantenimiento {
        private int id;
        private int id_usuario_creador;
        private String titulo;
        private String descripcion;
        private boolean done;
        private Date fecha_creacion;
        private  Date fecha_finalizacion;
        private int nivel_prioridad;

    public IncidenciaMantenimiento(int id, int id_usuario_creador, String titulo, String descripcion, boolean done, Date fecha_creacion, Date fecha_finalizacion, int nivel_prioridad) {
        this.id = id;
        this.id_usuario_creador = id_usuario_creador;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.done = done;
        this.fecha_creacion = fecha_creacion;
        this.fecha_finalizacion = fecha_finalizacion;
        this.nivel_prioridad = nivel_prioridad;
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

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_finalizacion() {
        return fecha_finalizacion;
    }

    public void setFecha_finalizacion(Date fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
    }

    public int getNivel_prioridad() {
        return nivel_prioridad;
    }

    public void setNivel_prioridad(int nivel_prioridad) {
        this.nivel_prioridad = nivel_prioridad;
    }
}
