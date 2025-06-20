package com.lp2.evaluacion.peliculasspringboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre", length = 100)
    private String nombre;

    @NotNull(message = "El apellido no puede ser nulo")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Column(name = "apellido", length = 100)
    private String apellido;

    @NotNull(message = "El email no puede ser nulo")
    @Size(min = 5, max = 100, message = "El email debe tener entre 5 y 100 caracteres")
    @Column(name = "email", length = 100, unique = true)
    private String email;

    public Cliente() {}

    public Cliente(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    // --- Getters y Setters ---
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" +
               "idCliente=" + idCliente +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}