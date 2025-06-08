package com.yupanqui.alquiler.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class DetalleAlquilerPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idAlquiler;
    private Integer idPelicula;

    public DetalleAlquilerPK() {}

    public DetalleAlquilerPK(Integer idAlquiler, Integer idPelicula) {
        this.idAlquiler = idAlquiler;
        this.idPelicula = idPelicula;
    }

    public Integer getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(Integer idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleAlquilerPK that = (DetalleAlquilerPK) o;
        return Objects.equals(idAlquiler, that.idAlquiler) &&
               Objects.equals(idPelicula, that.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlquiler, idPelicula);
    }
}