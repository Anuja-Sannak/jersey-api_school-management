package com.api.service;

import java.util.List;

import com.api.dao.StudentRepository;
import com.api.dao.SubjectRepository;
import com.api.dao.TeacherRepository;
import com.api.model.Student;
import com.api.model.Subject;
import com.api.model.Teacher;

public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Student> getAllStudents(int page,int size) {
        return studentRepository.findAll(page, size);
    }

    public Student getStudentById(int id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        return student;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public long getTotalStudents() {
        return studentRepository.count();
    }
    
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(int id, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(id);
        if (existingStudent == null) {
            throw new RuntimeException("Student not found");
        }
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setAddress(updatedStudent.getAddress());
        return studentRepository.save(existingStudent);
    }

    public Student assignTeacherToStudent(int studentId, int teacherId) {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        Teacher teacher = teacherRepository.findById(teacherId);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        student.getTeacher().add(teacher);
        return studentRepository.save(student);
    }

    public Student assignSubjectToStudent(int studentId, int subjectId) {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        Subject subject = subjectRepository.findById(subjectId);
        if (subject == null) {
            throw new RuntimeException("Subject not found");
        }

        student.getSubject().add(subject);
        return studentRepository.save(student);
    }

    public Student addSubjectsToStudent(int studentId, List<String> subjectNames) {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        for (String subjectName : subjectNames) {
            Subject subject = subjectRepository.findByName(subjectName);
            if (subject == null) {
                throw new RuntimeException("Subject not found");
            }
            student.getSubject().add(subject);
        }

        return studentRepository.save(student);
    }
    
    public List<Student> getStudentsByName(String name) {
        List<Student> students = studentRepository.findAllStudentsByName(name);
        if (students.isEmpty()) {
            throw new RuntimeException("No students found with name: " + name);
        }
        return students;
    }
    
    

}
