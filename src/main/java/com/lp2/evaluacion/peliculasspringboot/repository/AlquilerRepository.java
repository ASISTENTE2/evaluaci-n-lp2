package com.lp2.evaluacion.peliculasspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lp2.evaluacion.peliculasspringboot.model.Alquiler;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {

}