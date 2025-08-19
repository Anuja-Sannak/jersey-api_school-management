package com.api.dao;

import com.api.model.Teacher;

import jakarta.persistence.EntityManager;
import java.util.List;

public class TeacherRepository {

    public Teacher save(Teacher teacher) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        if (teacher.getTeacher_id() == 0) {
            em.persist(teacher);
        } else {
            teacher = em.merge(teacher);
        }
        em.getTransaction().commit();
        em.close();
        return teacher;
    }

    public Teacher findById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        try {
            return em.createQuery(
                "SELECT t FROM Teacher t " +
                "LEFT JOIN FETCH t.subject " +
                "LEFT JOIN FETCH t.student " +
                "WHERE t.teacher_id = :id", Teacher.class)
                .setParameter("id", id)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }


    public List<Teacher> findAll() {
        EntityManager em = EMFProvider.getEntityManager();
        try {
            return em.createQuery(
                "SELECT DISTINCT t FROM Teacher t " + "LEFT JOIN FETCH t.subject " + "LEFT JOIN FETCH t.student", Teacher.class)
                .getResultList();
        } finally {
            em.close();
        }
    }



    public Teacher findByName(String name) {
        EntityManager em = EMFProvider.getEntityManager();
        List<Teacher> list = em.createQuery("SELECT t FROM Teacher t WHERE t.name = :name", Teacher.class)
                               .setParameter("name", name)
                               .getResultList();
        em.close();
        return list.isEmpty() ? null : list.get(0);
    }

    public void deleteById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        Teacher t = em.find(Teacher.class, id);
        if (t != null) em.remove(t);
        em.getTransaction().commit();
        em.close();
    }
}
