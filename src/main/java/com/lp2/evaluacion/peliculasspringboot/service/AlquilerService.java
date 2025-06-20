package com.lp2.evaluacion.peliculasspringboot.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lp2.evaluacion.peliculasspringboot.model.Alquiler;
import com.lp2.evaluacion.peliculasspringboot.model.Cliente;
import com.lp2.evaluacion.peliculasspringboot.model.DetalleAlquiler;
import com.lp2.evaluacion.peliculasspringboot.model.DetalleAlquilerForm;
import com.lp2.evaluacion.peliculasspringboot.model.EstadoAlquiler;
import com.lp2.evaluacion.peliculasspringboot.model.Pelicula;
import com.lp2.evaluacion.peliculasspringboot.repository.AlquilerRepository;
import com.lp2.evaluacion.peliculasspringboot.repository.ClienteRepository;
import com.lp2.evaluacion.peliculasspringboot.repository.DetalleAlquilerRepository;
import com.lp2.evaluacion.peliculasspringboot.repository.PeliculaRepository;

@Service
public class AlquilerService {

    private final ClienteRepository clienteRepository;
    private final PeliculaRepository peliculaRepository;
    private final AlquilerRepository alquilerRepository;
    private final DetalleAlquilerRepository detalleAlquilerRepository;

    @Autowired
    public AlquilerService(ClienteRepository clienteRepository,
                           PeliculaRepository peliculaRepository,
                           AlquilerRepository alquilerRepository,
                           DetalleAlquilerRepository detalleAlquilerRepository) {
        this.clienteRepository = clienteRepository;
        this.peliculaRepository = peliculaRepository;
        this.alquilerRepository = alquilerRepository;
        this.detalleAlquilerRepository = detalleAlquilerRepository;
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public List<Pelicula> getAllPeliculas() {
        return peliculaRepository.findAll();
    }

    @Transactional
    public boolean registrarAlquiler(Integer clienteId, List<DetalleAlquilerForm> peliculasSeleccionadas) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            System.err.println("Error: Cliente no encontrado.");
            return false;
        }
        Cliente cliente = clienteOpt.get();

        double totalAlquiler = 0.0;

        for (DetalleAlquilerForm item : peliculasSeleccionadas) {
            Optional<Pelicula> peliculaOpt = peliculaRepository.findById(item.getPeliculaId());
            if (peliculaOpt.isEmpty()) {
                System.err.println("Error: Película con ID " + item.getPeliculaId() + " no encontrada.");
                return false;
            }
            Pelicula pelicula = peliculaOpt.get();

            if (item.getCantidad() <= 0) {
                System.err.println("Error: Cantidad inválida para la película '" + pelicula.getTitulo() + "'.");
                return false;
            }
            if (pelicula.getStock() < item.getCantidad()) {
                System.err.println("Error: Stock insuficiente para la película '" + pelicula.getTitulo() + "'. Stock disponible: " + pelicula.getStock());
                return false;
            }

            double precioUnitario = 5.00;
            totalAlquiler += (precioUnitario * item.getCantidad());
        }

        Alquiler alquiler = new Alquiler(cliente, new Date(), totalAlquiler, EstadoAlquiler.ACTIVO);
        alquiler = alquilerRepository.save(alquiler);

        if (alquiler.getIdAlquiler() == null) {
             System.err.println("Error: No se pudo generar el ID del alquiler.");
             return false;
        }

        for (DetalleAlquilerForm item : peliculasSeleccionadas) {
            Pelicula pelicula = peliculaRepository.findById(item.getPeliculaId()).orElse(null);
            if (pelicula == null) {
                throw new RuntimeException("Película no encontrada: " + item.getPeliculaId());
            }

            pelicula.setStock(pelicula.getStock() - item.getCantidad());
            peliculaRepository.save(pelicula);

            DetalleAlquiler detalle = new DetalleAlquiler(alquiler, pelicula, item.getCantidad());
            detalleAlquilerRepository.save(detalle);
        }

        System.out.println("Alquiler registrado con éxito para el cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        return true;
    }
}