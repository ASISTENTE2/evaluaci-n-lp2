package com.yupanqui.alquiler.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_alquiler")
public class DetalleAlquiler implements Serializable {
    @EmbeddedId
    private DetalleAlquilerPK id;

    @ManyToOne
    @MapsId("idAlquiler")
    @JoinColumn(name = "id_alquiler", insertable = false, updatable = false)
    private Alquiler alquiler;

    @ManyToOne
    @MapsId("idPelicula")
    @JoinColumn(name = "id_pelicula", insertable = false, updatable = false)
    private Pelicula pelicula;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    public DetalleAlquiler() {
        this.id = new DetalleAlquilerPK();
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
        this.id.setIdAlquiler(alquiler.getIdAlquiler());
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        if (this.id == null) {
            this.id = new DetalleAlquilerPK();
        }
        this.id.setIdPelicula(pelicula.getIdPelicula());
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}