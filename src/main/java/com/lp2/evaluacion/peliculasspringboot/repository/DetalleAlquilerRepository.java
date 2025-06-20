package com.lp2.evaluacion.peliculasspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lp2.evaluacion.peliculasspringboot.model.DetalleAlquiler;
import com.lp2.evaluacion.peliculasspringboot.model.DetalleAlquilerPK;

@Repository
public interface DetalleAlquilerRepository extends JpaRepository<DetalleAlquiler, DetalleAlquilerPK> {

}