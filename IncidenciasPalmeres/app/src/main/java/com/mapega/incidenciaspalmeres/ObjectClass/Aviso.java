package com.mapega.incidenciaspalmeres.ObjectClass;

import java.util.Date;

public class Aviso {
    private int id;
    private String titulo;
    private Date fechaCreacion;
    private int idUsuarioCreador;
    private String descripcion;
    private boolean importante;
    private boolean visible;

    public Aviso(int id, String titulo, Date fechaCreacion, int idUsuarioCreador, String descripcion, boolean importante, boolean visible) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.idUsuarioCreador = idUsuarioCreador;
        this.descripcion = descripcion;
        this.importante = importante;
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

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
