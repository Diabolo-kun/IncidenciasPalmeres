package com.mapega.incidenciaspalmeres.ObjectClass;

public class AvisoElement {
    public String titulo;
    public String date;
    public boolean important;

    public AvisoElement(String titulo, String date, boolean important) {
        this.titulo = titulo;
        this.date = date;
        this.important = important;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}
