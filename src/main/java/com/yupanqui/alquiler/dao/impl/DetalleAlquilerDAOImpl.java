package com.yupanqui.alquiler.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.yupanqui.alquiler.dao.DetalleAlquilerDAO;
import com.yupanqui.alquiler.entidades.DetalleAlquiler;
import com.yupanqui.alquiler.entidades.DetalleAlquilerPK;
import com.yupanqui.alquiler.util.JPAUtil;

public class DetalleAlquilerDAOImpl implements DetalleAlquilerDAO {
    private EntityManager em;

    public DetalleAlquilerDAOImpl() {
        this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public void save(DetalleAlquiler detalleAlquiler) {
        try {
            em.getTransaction().begin();
            em.persist(detalleAlquiler);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public DetalleAlquiler findById(DetalleAlquilerPK id) {
        return em.find(DetalleAlquiler.class, id);
    }

    @Override
    public List<DetalleAlquiler> findAll() {
        TypedQuery<DetalleAlquiler> query = em.createQuery("SELECT da FROM DetalleAlquiler da", DetalleAlquiler.class);
        return query.getResultList();
    }

    @Override
    public void update(DetalleAlquiler detalleAlquiler) {
        try {
            em.getTransaction().begin();
            em.merge(detalleAlquiler);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DetalleAlquiler detalleAlquiler) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(detalleAlquiler) ? detalleAlquiler : em.merge(detalleAlquiler));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}