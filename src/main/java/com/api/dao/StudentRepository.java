package com.api.dao;

import com.api.model.Student;
import jakarta.persistence.EntityManager;
import java.util.List;

public class StudentRepository {

    public Student save(Student student) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        if (student.getStudent_id() == 0) {
            em.persist(student);
        } else {
            student = em.merge(student);
        }
        em.getTransaction().commit();
        em.close();
        return student;
    }


    public Student findById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        Student student = em.createQuery(
            "SELECT s FROM Student s LEFT JOIN FETCH s.teacher t LEFT JOIN FETCH t.subject LEFT JOIN FETCH s.subject "
            + "WHERE s.student_id = :id", Student.class)
            .setParameter("id", id)
            .getSingleResult();
        em.close();
        return student;
    }

        
    public List<Student> findAll() {
        EntityManager em = EMFProvider.getEntityManager();
        List<Student> list = em.createQuery(
            "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.teacher t LEFT JOIN FETCH t.subject "
            + "LEFT JOIN FETCH s.subject", Student.class) 
            .getResultList();
        em.close();
        return list;
    }


    public Student findByName(String name) {
        EntityManager em = EMFProvider.getEntityManager();
        List<Student> list = em.createQuery(
            "SELECT s FROM Student s LEFT JOIN FETCH s.teacher LEFT JOIN FETCH s.subject "
            + "WHERE s.name = :name", Student.class)
            .setParameter("name", name)
            .getResultList();
        em.close();
        return list.isEmpty() ? null : list.get(0);
    }

    public void deleteById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        Student s = em.find(Student.class, id);
        if (s != null) em.remove(s);
        em.getTransaction().commit();
        em.close();
    }
}
