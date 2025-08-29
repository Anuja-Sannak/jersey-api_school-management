package com.api.resource;


import java.util.List;
import java.util.Map;

import com.api.dao.StudentRepository;
import com.api.dao.SubjectRepository;
import com.api.dao.TeacherRepository;
import com.api.service.*;
import com.api.model.*;
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
    public void getAllStudents(@QueryParam("page") @DefaultValue("1") int page, 
    							@Suspended final AsyncResponse asyncResponse) {
        AppExecutor.getExecutor().submit(() -> {
            try {
            		int size = 5;
            		
            	
                List<Student> students = studentService.getAllStudents(page, size);
                
                
                long total = studentService.getTotalStudents();
                long totalPages = (long) Math.ceil((double) total / size);

                asyncResponse.resume(Response.ok(
                        Map.of(
                                "page", page,
                                "size", size,
                                "total", total,
                                "totalPages", totalPages,
                                "students", students
                        )
                ).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
   }

@GET
@Path("/by-name/{name}")
@Produces(MediaType.APPLICATION_JSON)
public void getStudentsByName(@PathParam("name") String name, 
                              @Suspended final AsyncResponse asyncResponse) {
    AppExecutor.getExecutor().submit(() -> {
        try {
            List<Student> students = studentService.getStudentsByName(name);
            asyncResponse.resume(Response.ok(students).build());
        } catch (Exception e) {
            asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
        }
    });
}

    
    @GET
    @Path("/{id:\\d+}")
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
