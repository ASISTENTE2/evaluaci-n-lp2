package com.lp2.evaluacion.peliculasspringboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Integer idPelicula;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 1, max = 255, message = "El título debe tener entre 1 y 255 caracteres")
    @Column(name = "titulo", length = 255)
    private String titulo;

    @NotNull(message = "El género no puede ser nulo")
    @Size(min = 1, max = 100, message = "El género debe tener entre 1 y 100 caracteres")
    @Column(name = "genero", length = 100)
    private String genero;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock")
    private Integer stock;

    public Pelicula() {}

    public Pelicula(String titulo, String genero, Integer stock) {
        this.titulo = titulo;
        this.genero = genero;
        this.stock = stock;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
               "idPelicula=" + idPelicula +
               ", titulo='" + titulo + '\'' +
               ", genero='" + genero + '\'' +
               ", stock=" + stock +
               '}';
    }
}