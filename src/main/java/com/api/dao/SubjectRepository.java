package com.api.dao;

import com.api.model.Subject;

import jakarta.persistence.EntityManager;
import java.util.List;

public class SubjectRepository {

    public Subject save(Subject subject) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        if (subject.getSubject_id() == 0) {
            em.persist(subject);
        } else {
            subject = em.merge(subject);
        }
        em.getTransaction().commit();
        em.close();
        return subject;
    }

    public Subject findById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        Subject subject = em.find(Subject.class, id);
        em.close();
        return subject;
    }

    public List<Subject> findAll() {
        EntityManager em = EMFProvider.getEntityManager();
        try {
            return em.createQuery(
                "SELECT DISTINCT s FROM Subject s " + "LEFT JOIN FETCH s.student " + "LEFT JOIN FETCH s.teacher", Subject.class)
                .getResultList();
        } finally {
            em.close();
        }
    }


    public Subject findByName(String name) {
        EntityManager em = EMFProvider.getEntityManager();
        List<Subject> list = em.createQuery("SELECT s FROM Subject s WHERE s.name = :name", Subject.class)
                               .setParameter("name", name)
                               .getResultList();
        em.close();
        return list.isEmpty() ? null : list.get(0);
    }

    public void deleteById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        Subject s = em.find(Subject.class, id);
        if (s != null) em.remove(s);
        em.getTransaction().commit();
        em.close();
    }
}
