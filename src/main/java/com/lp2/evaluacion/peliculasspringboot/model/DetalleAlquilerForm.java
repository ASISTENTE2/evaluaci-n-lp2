package com.lp2.evaluacion.peliculasspringboot.model;

public class DetalleAlquilerForm {
    private Integer peliculaId;
    private Integer cantidad;

    public DetalleAlquilerForm() {}

    public DetalleAlquilerForm(Integer peliculaId, Integer cantidad) {
        this.peliculaId = peliculaId;
        this.cantidad = cantidad;
    }

    public Integer getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Integer peliculaId) {
        this.peliculaId = peliculaId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}