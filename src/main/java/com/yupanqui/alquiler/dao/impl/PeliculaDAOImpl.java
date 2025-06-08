package com.yupanqui.alquiler.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.yupanqui.alquiler.dao.PeliculaDAO;
import com.yupanqui.alquiler.entidades.Pelicula;
import com.yupanqui.alquiler.util.JPAUtil;

public class PeliculaDAOImpl implements PeliculaDAO {
    private EntityManager em;

    public PeliculaDAOImpl() {
        this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public void save(Pelicula pelicula) {
        try {
            em.getTransaction().begin();
            em.persist(pelicula);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Pelicula findById(Integer id) {
        return em.find(Pelicula.class, id);
    }

    @Override
    public List<Pelicula> findAll() {
        TypedQuery<Pelicula> query = em.createQuery("SELECT p FROM Pelicula p", Pelicula.class);
        return query.getResultList();
    }

    @Override
    public void update(Pelicula pelicula) {
        try {
            em.getTransaction().begin();
            em.merge(pelicula);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Pelicula pelicula) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(pelicula) ? pelicula : em.merge(pelicula));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}
