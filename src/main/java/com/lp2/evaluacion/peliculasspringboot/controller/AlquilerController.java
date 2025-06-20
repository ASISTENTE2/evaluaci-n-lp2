package com.lp2.evaluacion.peliculasspringboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lp2.evaluacion.peliculasspringboot.model.Cliente;
import com.lp2.evaluacion.peliculasspringboot.model.DetalleAlquilerForm;
import com.lp2.evaluacion.peliculasspringboot.model.Pelicula;
import com.lp2.evaluacion.peliculasspringboot.service.AlquilerService;

@Controller
@RequestMapping("/")
public class AlquilerController {

    private final AlquilerService alquilerService;

    @Autowired
    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = alquilerService.getAllClientes();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

    @GetMapping("/peliculas")
    public String listarPeliculas(Model model) {
        List<Pelicula> peliculas = alquilerService.getAllPeliculas();
        model.addAttribute("peliculas", peliculas);
        return "peliculas";
    }

    @GetMapping("/alquilar")
    public String mostrarFormularioAlquiler(Model model) {
        List<Cliente> clientes = alquilerService.getAllClientes();
        List<Pelicula> peliculas = alquilerService.getAllPeliculas();
        model.addAttribute("clientes", clientes);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("detalleAlquilerForm", new DetalleAlquilerForm());
        return "alquilar";
    }

    @PostMapping("/alquilar")
    public String procesarAlquiler(
            @RequestParam("clienteId") Integer clienteId,
            @RequestParam("peliculasIds") List<Integer> peliculasIds,
            @RequestParam("cantidades") List<Integer> cantidades,
            RedirectAttributes redirectAttributes) {

        if (peliculasIds.size() != cantidades.size()) {
            redirectAttributes.addFlashAttribute("error", "Error: Cantidad de películas y cantidades no coinciden.");
            return "redirect:/alquilar";
        }

        List<DetalleAlquilerForm> peliculasSeleccionadas = new ArrayList<>();
        for (int i = 0; i < peliculasIds.size(); i++) {
            peliculasSeleccionadas.add(new DetalleAlquilerForm(peliculasIds.get(i), cantidades.get(i)));
        }

        boolean exito = alquilerService.registrarAlquiler(clienteId, peliculasSeleccionadas);

        if (exito) {
            redirectAttributes.addFlashAttribute("success", "Alquiler registrado con éxito!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el alquiler. Verifica el stock o los IDs.");
        }

        return "redirect:/alquilar";
    }
}