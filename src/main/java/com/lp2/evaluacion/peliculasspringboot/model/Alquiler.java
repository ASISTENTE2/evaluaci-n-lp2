package com.lp2.evaluacion.peliculasspringboot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity; // Aunque en Spring Boot no siempre es estrictamente necesario, se mantiene por consistencia y puede ser útil para ciertos escenarios de caché o distribución.
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "alquileres")
public class Alquiler implements Serializable { // Mantener Serializable según tu código original

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alquiler")
    private Integer idAlquiler;

    @NotNull(message = "La fecha del alquiler no puede ser nula")
    @Temporal(TemporalType.DATE) // Solo fecha, sin hora
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY) // Relación Muchos a Uno con Cliente
    @JoinColumn(name = "id_cliente", nullable = false) // Columna de clave foránea en la tabla alquileres
    @NotNull(message = "El cliente no puede ser nulo") // Validación para asegurar que hay un cliente asociado
    private Cliente cliente;

    @NotNull(message = "El total no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El total no puede ser negativo") // Validación para total >= 0
    @Column(name = "total", nullable = false)
    private Double total;

    @NotNull(message = "El estado del alquiler no puede ser nulo")
    @Enumerated(EnumType.STRING) // Persiste el enum como String en la base de datos
    @Column(name = "estado", nullable = false)
    private EstadoAlquiler estado;

    // Relación Uno a Muchos con DetalleAlquiler
    // mappedBy indica que el campo "alquiler" en la entidad DetalleAlquiler es el dueño de la relación.
    // CascadeType.ALL: Operaciones (persist, merge, remove) en Alquiler se propagan a sus DetalleAlquiler.
    // orphanRemoval = true: Si se elimina un DetalleAlquiler de la lista de un Alquiler, se elimina de la BD.
    @OneToMany(mappedBy = "alquiler", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleAlquiler> detalles;

    public Alquiler() {
        this.fecha = new Date(); // Inicializa la fecha al crear una nueva instancia
        this.estado = EstadoAlquiler.ACTIVO; // Estado por defecto
    }

    public Alquiler(Cliente cliente, Date fecha, Double total, EstadoAlquiler estado) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    // --- Getters y Setters ---
    public Integer getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(Integer idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }

    public List<DetalleAlquiler> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleAlquiler> detalles) {
        this.detalles = detalles;
    }

    // Puedes añadir un método para añadir detalles fácilmente
    public void addDetalle(DetalleAlquiler detalle) {
        if (this.detalles == null) {
            this.detalles = new java.util.ArrayList<>();
        }
        this.detalles.add(detalle);
        detalle.setAlquiler(this); // Asegura la bidireccionalidad
    }

    public void removeDetalle(DetalleAlquiler detalle) {
        if (this.detalles != null) {
            this.detalles.remove(detalle);
            detalle.setAlquiler(null); // Rompe la bidireccionalidad
        }
    }

    @Override
    public String toString() {
        return "Alquiler{" +
               "idAlquiler=" + idAlquiler +
               ", fecha=" + fecha +
               ", cliente=" + (cliente != null ? cliente.getNombre() : "N/A") +
               ", total=" + total +
               ", estado=" + estado +
               '}';
    }
}