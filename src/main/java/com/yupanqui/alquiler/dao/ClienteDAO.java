package com.yupanqui.alquiler.dao;

import com.yupanqui.alquiler.entidades.Cliente;

public interface ClienteDAO extends GenericDAO<Cliente, Integer> {
    Cliente findByEmail(String email);
}