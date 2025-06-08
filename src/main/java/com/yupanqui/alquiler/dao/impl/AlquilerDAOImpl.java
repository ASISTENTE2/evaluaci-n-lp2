package com.yupanqui.alquiler.dao.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.yupanqui.alquiler.dao.AlquilerDAO;
import com.yupanqui.alquiler.entidades.Alquiler;
import com.yupanqui.alquiler.util.JPAUtil;

public class AlquilerDAOImpl implements AlquilerDAO {
    private EntityManager em;

    public AlquilerDAOImpl() {
        this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public void save(Alquiler alquiler) {
        try {
            em.getTransaction().begin();
            em.persist(alquiler);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Alquiler findById(Integer id) {
        return em.find(Alquiler.class, id);
    }

    @Override
    public List<Alquiler> findAll() {
        TypedQuery<Alquiler> query = em.createQuery("SELECT a FROM Alquiler a", Alquiler.class);
        return query.getResultList();
    }

    @Override
    public void update(Alquiler alquiler) {
        try {
            em.getTransaction().begin();
            em.merge(alquiler);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Alquiler alquiler) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(alquiler) ? alquiler : em.merge(alquiler));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}