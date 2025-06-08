package com.yupanqui.alquiler.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.yupanqui.alquiler.dao.ClienteDAO;
import com.yupanqui.alquiler.entidades.Cliente;
import com.yupanqui.alquiler.util.JPAUtil;

public class ClienteDAOImpl implements ClienteDAO {
    private EntityManager em;

    
    public ClienteDAOImpl() {
        this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public void save(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Cliente findById(Integer id) {
        return em.find(Cliente.class, id);
    }

    @Override
    public List<Cliente> findAll() {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }

    @Override
    public void update(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(cliente) ? cliente : em.merge(cliente));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Cliente findByEmail(String email) {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }
}