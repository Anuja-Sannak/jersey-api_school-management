package com.api.dao;


import com.api.model.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

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

/*
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
    */
    
    public Teacher findById(int id) {
    	EntityManager em = EMFProvider.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
        
        Root<Teacher> teacher = criteriaQuery.from(Teacher.class);
        teacher.fetch("subject",JoinType.LEFT);
        teacher.fetch("student", JoinType.LEFT);
        
        criteriaQuery.select(teacher).where(criteriaBuilder.equal(teacher.get("teacher_id"), id));
        Teacher result =em.createQuery(criteriaQuery).getSingleResult();
        em.close();
        
        return result;
    }


 /*  public List<Teacher> findAll() {
        EntityManager em = EMFProvider.getEntityManager();
        try {
            return em.createQuery(
                "SELECT DISTINCT t FROM Teacher t LEFT JOIN FETCH t.subject LEFT JOIN FETCH t.student", Teacher.class)
                .getResultList();
        } finally {
            em.close();
        }
    }
*/
    
   public List<Teacher> findAll(){
	   EntityManager em = EMFProvider.getEntityManager();
       CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
       CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
       
       Root<Teacher> teacher = criteriaQuery.from(Teacher.class);
       teacher.fetch("subject",JoinType.LEFT);
       teacher.fetch("student", JoinType.LEFT);
       criteriaQuery.select(teacher).distinct(true);
       
       List<Teacher> result = em.createQuery(criteriaQuery).getResultList();
       
       em.close();
       
       return result;
   }
    
 /*   public Teacher findByName(String name) {
    	
    	EntityManager em = EMFProvider.getEntityManager();
    	List<Teacher> list = em.createQuery("SELECT DISTINCT t FROM Teacher t  LEFT JOIN FETCH t.subject LEFT JOIN FETCH t.student "
			+ "WHERE t.name = :name", Teacher.class).setParameter("name", name).getResultList();
	
    	em.clear();
    	return list.isEmpty()? null : list.get(0);

}*/
   
   
   public Teacher findByName(String name) {
	   EntityManager em = EMFProvider.getEntityManager();
       CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
       CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
       
       Root<Teacher> teacher = criteriaQuery.from(Teacher.class);
       teacher.fetch("subject",JoinType.LEFT);
       teacher.fetch("student", JoinType.LEFT);
       
       criteriaQuery.select(teacher).distinct(true).where(criteriaBuilder.equal(teacher.get("name"), name));
       Teacher result =em.createQuery(criteriaQuery).getSingleResult();
       em.close();
       
       return result;
   }
   
   public List<Teacher> findTeachersByName(String name) {
	    EntityManager em = EMFProvider.getEntityManager();
	    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	    CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);

	    Root<Teacher> teacher = criteriaQuery.from(Teacher.class);
	    teacher.fetch("subject", JoinType.LEFT);
	    teacher.fetch("student", JoinType.LEFT);

	    criteriaQuery.select(teacher)
	                 .distinct(true)
	                 .where(criteriaBuilder.equal(teacher.get("name"), name));

	    List<Teacher> results = em.createQuery(criteriaQuery).getResultList();
	    em.close();

	    return results;
	}

    

/*    public void deleteById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        Teacher t = em.find(Teacher.class, id);
        if (t != null) em.remove(t);
        em.getTransaction().commit();
        em.close();
    }
    */
   
  
   
   public void deleteById(int id) {
	   
	   EntityManager em = EMFProvider.getEntityManager();
	   em.getTransaction().begin();
	   
       CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
       
       CriteriaDelete<Teacher> criteriaDelete =criteriaBuilder.createCriteriaDelete(Teacher.class);
       Root<Teacher> teacher = criteriaDelete.from(Teacher.class);  
       criteriaDelete.where(criteriaBuilder.equal(teacher.get("teacher_id"),id));
       
       em.createQuery(criteriaDelete).executeUpdate();
       em.getTransaction().commit();
       em.close();
	   
   }
}
