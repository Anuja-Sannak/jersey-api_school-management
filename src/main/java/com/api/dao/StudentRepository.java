package com.api.dao;

import com.api.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

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


 /*   public Student findById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        Student student = em.createQuery(
            "SELECT s FROM Student s LEFT 
            JOIN FETCH s.teacher t 
            LEFT JOIN FETCH t.subject 
            LEFT JOIN FETCH s.subject "
            + "WHERE s.student_id = :id", Student.class)
            .setParameter("id", id)
            .getSingleResult();
        em.close();
        return student;
    }
*/
    
    public Student findById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        
        Root<Student> student = criteriaQuery.from(Student.class);
        var teacherJoin = student.fetch("teacher", JoinType.LEFT);
        teacherJoin.fetch("subject", JoinType.LEFT);

        student.fetch("subject", JoinType.LEFT);

        criteriaQuery.select(student)
          .where(criteriaBuilder.equal(student.get("student_id"), id));
        Student result = em.createQuery(criteriaQuery).getSingleResult();
        em.close();
        
        return result;
    } 
        
 /*   public List<Student> findAll() {
        EntityManager em = EMFProvider.getEntityManager();
        List<Student> list = em.createQuery(
            "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.teacher t LEFT JOIN FETCH t.subject "
            + "LEFT JOIN FETCH s.subject", Student.class) 
            .getResultList();
        em.close();
        return list;
    }
 */
    
    public List<Student> findAll(int page, int size){
    	EntityManager em = EMFProvider.getEntityManager();
    	
    	CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    	CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
    	Root<Student> student = criteriaQuery.from(Student.class);
    	
    	criteriaQuery.select(student);
    	
    	 List<Student> students = em.createQuery(criteriaQuery)
                 .setFirstResult((page - 1) * size) 
                 .setMaxResults(size)               
                 .getResultList();

         
         students.forEach(s -> {
             s.getTeacher().size();  
             s.getSubject().size();   
             s.getTeacher().forEach(t -> t.getSubject().size());
         }); 
         
         
         System.out.println("=== Page " + page + " (size " + size + ") ===");
         students.forEach(s -> {
             System.out.println("Student: " + s.getName() + " | ID: " + s.getStudent_id());
             
             System.out.println("  Subjects:");
             s.getSubject().forEach(sub -> 
                 System.out.println("    " + sub.getName() + " (ID: " + sub.getSubject_id() + ")")
             );

             System.out.println("  Teachers:");
             s.getTeacher().forEach(t -> {
                 System.out.println("    " + t.getName() + " (ID: " + t.getTeacher_id() + ")");
                 System.out.println("      Subjects:");
                 t.getSubject().forEach(tsub ->
                     System.out.println("        " + tsub.getName() + " (ID: " + tsub.getSubject_id() + ")")
                 );
             });

             System.out.println("-------------------------------------");
         });

             
    	em.close();
    	return students;
        
    	
    }
       
    public long count() {
    	
    	EntityManager em = EMFProvider.getEntityManager();
    	
    	CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    	CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    	Root<Student> student = criteriaQuery.from(Student.class);
    	
    	criteriaQuery.select(criteriaBuilder.countDistinct(student));
    	
    	Long total = em.createQuery(criteriaQuery).getSingleResult();
    	
    	em.close();
    	return total;
    }


/*    public Student findByName(String name) {
        EntityManager em = EMFProvider.getEntityManager();
        List<Student> list = em.createQuery(
            "SELECT s FROM Student s LEFT JOIN FETCH s.teacher LEFT JOIN FETCH s.subject "
            + "WHERE s.name = :name", Student.class)
            .setParameter("name", name)
            .getResultList();
        em.close();
        return list.isEmpty() ? null : list.get(0);
    }

 */
    
    public Student findByName(String name) {
    	EntityManager em = EMFProvider.getEntityManager();
    	
    	CriteriaBuilder criteraBuilder =em.getCriteriaBuilder();
    	
    	CriteriaQuery<Student> criteriaQuery = criteraBuilder.createQuery(Student.class);
    	Root<Student> student = criteriaQuery.from(Student.class);
    	student.fetch("teacher", JoinType.LEFT);
    	student.fetch("subject", JoinType.LEFT);
    	criteriaQuery.select(student).where(criteraBuilder.equal(student.get("name"),name));
    	
    	Student result = em.createQuery(criteriaQuery).getSingleResult();
    	em.close();
    	
    	return result;
    }
    
    public List<Student> findAllStudentsByName(String name) {
        EntityManager em = EMFProvider.getEntityManager();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> student = criteriaQuery.from(Student.class);

       
        student.fetch("teacher", JoinType.LEFT);
        student.fetch("subject", JoinType.LEFT);

        criteriaQuery.select(student)
                     .where(criteriaBuilder.equal(student.get("name"), name));

        List<Student> result = em.createQuery(criteriaQuery).getResultList();
        em.close();

        return result; 
    }

    
 /*   public void deleteById(int id) {
        EntityManager em = EMFProvider.getEntityManager();
        em.getTransaction().begin();
        Student s = em.find(Student.class, id);
        if (s != null) em.remove(s);
        em.getTransaction().commit();
        em.close();
    }
}
*/
    
    public void deleteById(int id) {
    	 EntityManager em = EMFProvider.getEntityManager();
    	 em.getTransaction().begin();
    	 
    	 CriteriaBuilder criteraBuilder =em.getCriteriaBuilder();
    	 
    	 CriteriaDelete<Student> criteriaDelete = criteraBuilder.createCriteriaDelete(Student.class);
     	 Root<Student> student = criteriaDelete.from(Student.class);
     	 criteriaDelete.where(criteraBuilder.equal(student.get("student_id"), id));
     	 
     	 em.createQuery(criteriaDelete).executeUpdate();
     	 em.getTransaction().commit();
     	 em.close();
    	 
    	}
    
    
    
 }
