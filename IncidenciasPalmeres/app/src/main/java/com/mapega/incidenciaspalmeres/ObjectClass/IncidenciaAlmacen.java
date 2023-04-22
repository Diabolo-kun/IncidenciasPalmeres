package com.mapega.incidenciaspalmeres.ObjectClass;

public class IncidenciaAlmacen {
    private int id;
    private int id_usuario_creador;
    private String producto;
    private int cantidad;
    private String descripcion;
    private boolean pedido;

    public IncidenciaAlmacen(int id, int id_usuario_creador, String producto, int cantidad, String descripcion, boolean pedido) {
        this.id = id;
        this.id_usuario_creador = id_usuario_creador;
        this.producto = producto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.pedido = pedido;
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
}
