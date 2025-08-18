package com.api.service;

import java.util.List;

import com.api.dao.SubjectRepository;
import com.api.dao.TeacherRepository;
import com.api.model.Subject;
import com.api.model.Teacher;


public class TeacherService {

	 //   private final StudentRepository studentRepository;
	    private final TeacherRepository teacherRepository;
	    private final SubjectRepository subjectRepository;

	    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
	//        this.studentRepository = studentRepository;
	        this.teacherRepository = teacherRepository;
	        this.subjectRepository = subjectRepository;
	    }
	    public List<Teacher> getAllTeachers(){
	    	return teacherRepository.findAll();
	    }
	    
	    
	    public Teacher getTeacherById(int id) {
	    	Teacher teacher = teacherRepository.findById(id).orElse(null);
	    	if(teacher == null) {
	    		throw new RuntimeException("Teacher not found");
	    	}
	    	return teacher;
	    }

	    public Teacher createTeacher(Teacher teacher) {
	        return teacherRepository.save(teacher);
	    }

	    public void deleteTeacher(int id) {
	        teacherRepository.deleteById(id);
	    }
	    
	    public Teacher updateTeacher(int id, Teacher updatedTeacher) {
	        Teacher existingTeacher = teacherRepository.findById(id);
	        if (existingTeacher == null) {
	            throw new RuntimeException("Teacher not found");
	        }
	        existingTeacher.setName(updatedTeacher.getName());
	        existingTeacher.setHire_date(updatedTeacher.getHire_date());
	        return teacherRepository.save(existingTeacher);
	    }
 
	  /*  public Teacher assignStudentToTeacher(int teacherId, int studentId) {
	        
	        Teacher teacher = teacherRepository.findById(studentId).orElse(null);
	        if (teacher == null) {
	            throw new RuntimeException("Teacher not found");
	        }
	        Student student = studentRepository.findById(studentId).orElse(null);
	        if (student == null) {
	            throw new RuntimeException("Student not found");
	        }
	        teacher.getStudent().add(student);

	        return teacherRepository.save(teacher);
	    }
   */
	    public Teacher assignSubjectToTeacher(int teacherId, int subjectId) {
	        Teacher teacher = teacherRepository.findById(teacherId);
	        if (teacher == null) {
	            throw new RuntimeException("Teacher not found");
	        }

	        Subject subject = subjectRepository.findById(subjectId);
	        if (subject == null) {
	            throw new RuntimeException("Subject not found");
	        }

	        teacher.getSubject().add(subject);
	        return teacherRepository.save(teacher);
	    }
	    
	    public Teacher updateTeacherSubjects(int teacherId, int subjectId) {
	        Teacher teacher = teacherRepository.findById(teacherId);
	        if (teacher == null) {
	            throw new RuntimeException("Teacher not found");
	        }

	        Subject subject = subjectRepository.findById(subjectId);
	        if (subject == null) {
	            throw new RuntimeException("Subject not found");
	        }

	        teacher.getSubject().clear();
	        teacher.getSubject().add(subject);
	        return teacherRepository.save(teacher);
	    }

	    
	    
	    public Teacher replaceSubjectForTeacher(int teacherId, int oldSubjectId, int newSubjectId) {
	        Teacher teacher = teacherRepository.findById(teacherId);
	        if (teacher == null) {
	            throw new RuntimeException("Teacher not found");
	        }

	        Subject oldSubject = null;
	        for (Subject s : teacher.getSubject()) {
	            if (s.getSubject_id() == oldSubjectId) {
	                oldSubject = s;
	                break;
	            }
	        }

	        if (oldSubject != null) {
	            teacher.getSubject().remove(oldSubject);
	        }

	        Subject newSubject = subjectRepository.findById(newSubjectId);
	        if (newSubject == null) {
	            throw new RuntimeException("New subject not found");
	        }

	        teacher.getSubject().add(newSubject);
	        return teacherRepository.save(teacher);
	    }

}