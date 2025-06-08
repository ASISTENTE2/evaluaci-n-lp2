package com.yupanqui.alquiler.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.yupanqui.alquiler.dao.AlquilerDAO;
import com.yupanqui.alquiler.dao.ClienteDAO;
import com.yupanqui.alquiler.dao.DetalleAlquilerDAO;
import com.yupanqui.alquiler.dao.PeliculaDAO;
import com.yupanqui.alquiler.dao.impl.AlquilerDAOImpl;
import com.yupanqui.alquiler.dao.impl.ClienteDAOImpl;
import com.yupanqui.alquiler.dao.impl.DetalleAlquilerDAOImpl;
import com.yupanqui.alquiler.dao.impl.PeliculaDAOImpl;
import com.yupanqui.alquiler.entidades.Alquiler;
import com.yupanqui.alquiler.entidades.Cliente;
import com.yupanqui.alquiler.entidades.DetalleAlquiler;
import com.yupanqui.alquiler.entidades.DetalleAlquilerForm;
import com.yupanqui.alquiler.entidades.EstadoAlquiler;
import com.yupanqui.alquiler.entidades.Pelicula;

public class AlquilerService {

    private ClienteDAO clienteDAO;
    private PeliculaDAO peliculaDAO;
    private AlquilerDAO alquilerDAO;
    private DetalleAlquilerDAO detalleAlquilerDAO;

    public AlquilerService() {
        this.clienteDAO = new ClienteDAOImpl();
        this.peliculaDAO = new PeliculaDAOImpl();
        this.alquilerDAO = new AlquilerDAOImpl();
        this.detalleAlquilerDAO = new DetalleAlquilerDAOImpl();
    }

    public List<Cliente> getAllClientes() {
        return clienteDAO.findAll();
    }

    public List<Pelicula> getAllPeliculas() {
        return peliculaDAO.findAll();
    }

     //AQUI SE HACE EL REGISTRO DE ALQUILER LUEGO VALIDA LOS DATOS Y ACTUALIZA EL STOCK QUE TENEMOS DE PELICULAS
     
    public boolean registrarAlquiler(Integer clienteId, List<DetalleAlquilerForm> peliculasSeleccionadas) {
        Cliente cliente = clienteDAO.findById(clienteId);
        if (cliente == null) {
            System.err.println("Error: Cliente no encontrado.");
            return false;
        }

        double totalAlquiler = 0.0;
       
        for (DetalleAlquilerForm item : peliculasSeleccionadas) {
            Pelicula pelicula = peliculaDAO.findById(item.getPeliculaId());
            if (pelicula == null) {
                System.err.println("Error: Película con ID " + item.getPeliculaId() + " no encontrada.");
                return false;
            }
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

        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = com.yupanqui.alquiler.util.JPAUtil.getEntityManagerFactory().createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            
            em.persist(alquiler);
            em.flush();

            
            for (DetalleAlquilerForm item : peliculasSeleccionadas) {
                
                Pelicula peliculaReal = em.find(Pelicula.class, item.getPeliculaId());

                if (peliculaReal == null) {
                    
                    System.err.println("Error interno: Película no encontrada al procesar detalle del alquiler.");
                    transaction.rollback();
                    return false;
                }

                
                peliculaReal.setStock(peliculaReal.getStock() - item.getCantidad());
                em.merge(peliculaReal);

                
                DetalleAlquiler detalle = new DetalleAlquiler();
                detalle.setAlquiler(alquiler);
                detalle.setPelicula(peliculaReal);
                detalle.setCantidad(item.getCantidad());

                em.persist(detalle);
            }

            transaction.commit();
            System.out.println("Alquiler registrado con éxito para el cliente: " + cliente.getNombre() + " " + cliente.getApellido());
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al registrar el alquiler: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}