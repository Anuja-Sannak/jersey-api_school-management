package com.api.dao;

import com.api.model.Subject;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

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

/*    public Subject findById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        Subject subject = em.find(Subject.class, id);
        em.close();
        return subject;
    }
*/
    
   
    
    public Subject findById(int id) {
    	EntityManager em = EMFProvider.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Subject> criteriaQuery = criteriaBuilder.createQuery(Subject.class);
        
        Root<Subject> subject = criteriaQuery.from(Subject.class);
        criteriaQuery.select(subject).where(criteriaBuilder.equal(subject.get("subject_id"),id));
        Subject result = em.createQuery(criteriaQuery).getSingleResult();
        
        em.close();
        return result;
    }
    
    
 /*   public List<Subject> findAll() {
        EntityManager em = EMFProvider.getEntityManager();
        try {
            return em.createQuery(
                "SELECT DISTINCT s FROM Subject s " + "LEFT JOIN FETCH s.student " + "LEFT JOIN FETCH s.teacher", Subject.class)
                .getResultList();
        } finally {
            em.close();
        }
    }
*/
    
    public List<Subject> findAll(){
 	   EntityManager em = EMFProvider.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Subject> criteriaQuery = criteriaBuilder.createQuery(Subject.class);
        
        Root<Subject> subject = criteriaQuery.from(Subject.class);
        subject.fetch("student",JoinType.LEFT);
        subject.fetch("teacher", JoinType.LEFT);
        criteriaQuery.select(subject).distinct(true);
        
        List<Subject> result = em.createQuery(criteriaQuery).getResultList();
        
        em.close();
        
        return result;
    }

 /*   public Subject findByName(String name) {
        EntityManager em = EMFProvider.getEntityManager();
        List<Subject> list = em.createQuery("SELECT s FROM Subject s WHERE s.name = :name", Subject.class)
                               .setParameter("name", name)
                               .getResultList();
        em.close();
        return list.isEmpty() ? null : list.get(0);
    }
*/
    
   public Subject findByName(String name) {
	   
	   EntityManager em = EMFProvider.getEntityManager();
       CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
       CriteriaQuery<Subject> criteriaQuery = criteriaBuilder.createQuery(Subject.class);
       
       Root<Subject> subject = criteriaQuery.from(Subject.class);
       
       subject.fetch("student",JoinType.LEFT);
       subject.fetch("teacher", JoinType.LEFT);
       
       criteriaQuery.select(subject).distinct(true).where(criteriaBuilder.equal(subject.get("name"), name));
       Subject result = em.createQuery(criteriaQuery).getSingleResult();
       em.close();
       
       return result;
   }
    
    
/*    public void deleteById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        Subject s = em.find(Subject.class, id);
        if (s != null) em.remove(s);
        em.getTransaction().commit();
        em.close();
    }
    
    */
   
   
public void deleteById(int id) {
	   
	   EntityManager em = EMFProvider.getEntityManager();
	   em.getTransaction().begin();
	   
       CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
       
       CriteriaDelete<Subject> criteriaDelete =criteriaBuilder.createCriteriaDelete(Subject.class);
       Root<Subject> subject = criteriaDelete.from(Subject.class);  
       criteriaDelete.where(criteriaBuilder.equal(subject.get("subject_id"),id));
       
       em.createQuery(criteriaDelete).executeUpdate();
       em.getTransaction().commit();
       em.close();
	   
   }

   
}
