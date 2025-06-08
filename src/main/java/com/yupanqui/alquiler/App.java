package com.yupanqui.alquiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yupanqui.alquiler.entidades.Cliente;
import com.yupanqui.alquiler.entidades.DetalleAlquilerForm;
import com.yupanqui.alquiler.entidades.Pelicula;
import com.yupanqui.alquiler.service.AlquilerService;
import com.yupanqui.alquiler.util.JPAUtil;

public class App {
    public static void main(String[] args) {
        
        AlquilerService alquilerService = new AlquilerService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Sistema de Gestión de Alquiler de Películas ---");

        try {
            
            System.out.println("\n--- Clientes Disponibles ---");
            List<Cliente> clientes = alquilerService.getAllClientes();
            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados.");
                return;
            }
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println((i + 1) + ". " + clientes.get(i).getNombre() + " " + clientes.get(i).getApellido());
            }

            System.out.print("Seleccione el ID del cliente (número): ");
            int clienteIndex = scanner.nextInt();
            scanner.nextLine();

            if (clienteIndex <= 0 || clienteIndex > clientes.size()) {
                System.out.println("Selección de cliente inválida.");
                return;
            }
            Cliente clienteSeleccionado = clientes.get(clienteIndex - 1);
            System.out.println("Cliente seleccionado: " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellido());

            
            System.out.println("\n--- Películas Disponibles ---");
            List<Pelicula> peliculas = alquilerService.getAllPeliculas();
            if (peliculas.isEmpty()) {
                System.out.println("No hay películas registradas.");
                return;
            }
            for (int i = 0; i < peliculas.size(); i++) {
                System.out.println((i + 1) + ". " + peliculas.get(i).getTitulo() + " (Género: " + peliculas.get(i).getGenero() + ", Stock: " + peliculas.get(i).getStock() + ")");
            }

            List<DetalleAlquilerForm> peliculasParaAlquilar = new ArrayList<>();
            String agregarMas = "s";

            while (agregarMas.equalsIgnoreCase("s")) {
                System.out.print("Seleccione el ID de la película (número): ");
                int peliculaIndex = scanner.nextInt();
                scanner.nextLine();

                if (peliculaIndex <= 0 || peliculaIndex > peliculas.size()) {
                    System.out.println("Selección de película inválida.");
                    
                    continue;
                }
                Pelicula peliculaSeleccionada = peliculas.get(peliculaIndex - 1);
                System.out.println("Película seleccionada: " + peliculaSeleccionada.getTitulo());

                System.out.print("Ingrese la cantidad a alquilar de '" + peliculaSeleccionada.getTitulo() + "': ");
                int cantidad = scanner.nextInt();
                scanner.nextLine();

                peliculasParaAlquilar.add(new DetalleAlquilerForm(peliculaSeleccionada.getIdPelicula(), cantidad));

                System.out.print("¿Desea agregar más películas al alquiler? (s/n): ");
                agregarMas = scanner.nextLine();
            }

            
            if (alquilerService.registrarAlquiler(clienteSeleccionado.getIdCliente(), peliculasParaAlquilar)) {
                System.out.println("\n--- Proceso de alquiler completado con éxito ---");
            } else {
                System.out.println("\n--- Fallo al registrar el alquiler ---");
            }

        } catch (java.util.InputMismatchException e) {
            System.err.println("Error de entrada: Por favor, ingrese un número válido.");
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            JPAUtil.shutdown();
        }
    }
}