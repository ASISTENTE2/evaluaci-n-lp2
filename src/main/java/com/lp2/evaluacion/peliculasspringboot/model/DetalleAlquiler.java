package com.lp2.evaluacion.peliculasspringboot.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "detalle_alquiler")
public class DetalleAlquiler implements Serializable {

    @EmbeddedId
    private DetalleAlquilerPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAlquiler")
    @JoinColumn(name = "id_alquiler")
    @NotNull(message = "El alquiler no puede ser nulo")
    private Alquiler alquiler;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPelicula")
    @JoinColumn(name = "id_pelicula")
    @NotNull(message = "La película no puede ser nula")
    private Pelicula pelicula;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    public DetalleAlquiler() {
        this.id = new DetalleAlquilerPK();
    }

    public DetalleAlquiler(Alquiler alquiler, Pelicula pelicula, Integer cantidad) {
        this.alquiler = alquiler;
        this.pelicula = pelicula;
        this.cantidad = cantidad;
        this.id = new DetalleAlquilerPK(alquiler.getIdAlquiler(), pelicula.getIdPelicula());
    }

    public DetalleAlquilerPK getId() {
        return id;
    }

    public void setId(DetalleAlquilerPK id) {
        this.id = id;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
        if (this.id == null) {
            this.id = new DetalleAlquilerPK();
        }
        if (alquiler != null && alquiler.getIdAlquiler() != null) {
            this.id.setIdAlquiler(alquiler.getIdAlquiler());
        }
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        if (this.id == null) {
            this.id = new DetalleAlquilerPK();
        }
        if (pelicula != null && pelicula.getIdPelicula() != null) {
            this.id.setIdPelicula(pelicula.getIdPelicula());
        }
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleAlquiler that = (DetalleAlquiler) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "DetalleAlquiler{" +
               "id=" + id +
               ", cantidad=" + cantidad +
               '}';
    }
}