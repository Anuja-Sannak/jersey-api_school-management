package com.api.resource;


import java.util.List;

import com.api.dao.*;
import com.api.model.Student;
import com.api.service.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

	private final StudentRepository studentRepository = new StudentRepository();
    private final TeacherRepository teacherRepository = new TeacherRepository();
    private final SubjectRepository subjectRepository = new SubjectRepository();
    
    private final StudentService studentService = new StudentService(studentRepository, teacherRepository, subjectRepository);

    // Get all students
    @GET
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get student by ID
    @GET
    @Path("/{id}")
    public Student getStudentById(@PathParam("id") int id) {
        return studentService.getStudentById(id);
    }

    // Create a new student
    @POST
    public Student createStudent(Student student) {
        return studentService.createStudent(student);
    }

    // Update student by ID
    @PUT
    @Path("/{id}")
    public Student updateStudent(@PathParam("id") int id, Student student) {
        return studentService.updateStudent(id, student);
    }

    // Delete student by ID
    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") int id) {
        studentService.deleteStudent(id);
        return Response.noContent().build();
    }

    // Assign teacher to student
    @POST
    @Path("/{studentId}/assign-teacher/{teacherId}")
    public Student assignTeacher(@PathParam("studentId") int studentId, @PathParam("teacherId") int teacherId) {
        return studentService.assignTeacherToStudent(studentId, teacherId);
    }

    // Assign subject to student
    @POST
    @Path("/{studentId}/assign-subject/{subjectId}")
    public Student assignSubject(@PathParam("studentId") int studentId, @PathParam("subjectId") int subjectId) {
        return studentService.assignSubjectToStudent(studentId, subjectId);
    }

    // Add multiple subjects to student by name
    @PUT
    @Path("/{studentId}/subjects-by-name")
    public Student addSubjectsToStudentByName(@PathParam("studentId") int studentId, List<String> subjectNames) {
        return studentService.addSubjectsToStudent(studentId, subjectNames);
    }
}
