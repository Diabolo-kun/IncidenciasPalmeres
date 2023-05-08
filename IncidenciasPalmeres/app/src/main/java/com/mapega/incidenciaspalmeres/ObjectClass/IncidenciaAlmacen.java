package com.mapega.incidenciaspalmeres.ObjectClass;

import java.io.Serializable;
import java.util.Date;

public class IncidenciaAlmacen implements Serializable {
    private int id;
    private int id_usuario_creador;
    private String producto;
    private int cantidad;
    private String descripcion;
    private boolean pedido;
    private Date fecha_creacion;
    private  Date fecha_finalizacion;
    private int nivel_prioridad;

    public IncidenciaAlmacen(int id, int id_usuario_creador, String producto, int cantidad, String descripcion, boolean pedido, Date fecha_creacion, Date fecha_finalizacion, int nivel_prioridad) {
        this.id = id;
        this.id_usuario_creador = id_usuario_creador;
        this.producto = producto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.pedido = pedido;
        this.fecha_creacion = fecha_creacion;
        this.fecha_finalizacion = fecha_finalizacion;
        this.nivel_prioridad = nivel_prioridad;
    }

    public IncidenciaAlmacen() {
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isPedido() {
        return pedido;
    }

    public void setPedido(boolean pedido) {
        this.pedido = pedido;
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
