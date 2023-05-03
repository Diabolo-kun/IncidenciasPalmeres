package com.mapega.incidenciaspalmeres.ObjectClass;

import java.io.Serializable;
import java.util.Date;

public class Aviso implements Serializable {
    private int id;
    private String titulo;
    private Date fechaCreacion;
    private int idUsuarioCreador;
    private String descripcion;
    private int nivel_prioridad;
    private boolean visible;

    public Aviso(int id, String titulo, Date fechaCreacion, int idUsuarioCreador, String descripcion, int nivel_prioridad, boolean visible) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.idUsuarioCreador = idUsuarioCreador;
        this.descripcion = descripcion;
        this.nivel_prioridad = nivel_prioridad;
        this.visible = visible;
    }

    public Aviso() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(int idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNivel_prioridad() {return nivel_prioridad;}

    public void setNivel_prioridad(int nivel_prioridad) {
        this.nivel_prioridad = nivel_prioridad;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
