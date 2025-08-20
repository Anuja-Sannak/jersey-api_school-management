package com.api.resource;


import java.util.List;


import com.api.dao.*;

import com.api.model.Student;
import com.api.service.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
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

   

@GET
    public void getAllStudents(@Suspended final AsyncResponse asyncResponse) {
        AppExecutor.getExecutor().submit(() -> {
            try {
                List<Student> students = studentService.getAllStudents();
                asyncResponse.resume(Response.ok(students).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
   }
    
    @GET
    @Path("/{id}")
    public void getStudentById(@PathParam("id") int id,  @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Student student = studentService.getStudentById(id);
                asyncResponse.resume(Response.ok(student).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
            }
        });
    }

    
    @POST
    public void createStudent(Student student,  @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Student createdStudent = studentService.createStudent(student);
                asyncResponse.resume(Response.status(Response.Status.CREATED).entity(createdStudent).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
    }

   
    @PUT
    @Path("/{id}")
    public void updateStudent(@PathParam("id") int id, Student student,@Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Student updatedStudent = studentService.updateStudent(id, student);
                asyncResponse.resume(Response.ok(updatedStudent).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
            }
        });
    }

   
    @DELETE
    @Path("/{id}")
    public void deleteStudent(@PathParam("id") int id, @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                studentService.deleteStudent(id);
                asyncResponse.resume(Response.noContent().build());
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
            }
        });
    }

    
    @POST
    @Path("/{studentId}/assign-teacher/{teacherId}")
    public void assignTeacher(@PathParam("studentId") int studentId, @PathParam("teacherId") int teacherId, 
    		@Suspended final AsyncResponse asyncResponse) {
    	
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Student student = studentService.assignTeacherToStudent(studentId, teacherId);
                asyncResponse.resume(Response.ok(student).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
       
    }

   
    @POST
    @Path("/{studentId}/assign-subject/{subjectId}")
    public void assignSubject(@PathParam("studentId") int studentId, @PathParam("subjectId") int subjectId,
                              @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Student student = studentService.assignSubjectToStudent(studentId, subjectId);
                asyncResponse.resume(Response.ok(student).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
    }

    
    @PUT
    @Path("/{studentId}/subjects-by-name")
    public void addSubjectsToStudentByName(@PathParam("studentId") int studentId, List<String> subjectNames,
                                           @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Student student = studentService.addSubjectsToStudent(studentId, subjectNames);
                asyncResponse.resume(Response.ok(student).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
    }
}
