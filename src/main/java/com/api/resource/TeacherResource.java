package com.api.resource;

import java.util.List;
import com.api.model.Teacher;
import com.api.dao.*;
import com.api.service.TeacherService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/teacher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {
	
	
	 
	 private final TeacherRepository teacherRepository = new TeacherRepository();
	 private final SubjectRepository subjectRepository = new SubjectRepository();
	 
	 private final TeacherService teacherService = new TeacherService(teacherRepository, subjectRepository);
	 
    
    @GET
    public void getAllTeachers(@Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
             try {
                 List<Teacher> teachers = teacherService.getAllTeachers();
                 asyncResponse.resume(Response.ok(teachers).build());
             } catch (Exception e) {
                 asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
             }
         });
    }

   
    @GET
    @Path("/{id}")
    public void getTeacherById(@PathParam("id") int id, @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Teacher teacher = teacherService.getTeacherById(id);
                asyncResponse.resume(Response.ok(teacher).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
            }
        });
    }

    
    @POST
    public void createTeacher(Teacher teacher, @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Teacher createdTeacher = teacherService.createTeacher(teacher);
                asyncResponse.resume(Response.status(Response.Status.CREATED).entity(createdTeacher).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
    }

   
    @PUT
    @Path("/{id}")
    public void updateTeacher(@PathParam("id") int id, Teacher teacher, @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
                asyncResponse.resume(Response.status(Response.Status.CREATED).entity(updatedTeacher).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
    }

    
    @DELETE
    @Path("/{id}")
    public void deleteTeacher(@PathParam("id") int id, @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                teacherService.deleteTeacher(id);
                asyncResponse.resume(Response.noContent().build());
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
            }
        });
    }

   
    @POST
    @Path("/{teacherId}/assign-subject/{subjectId}")
    public void assignSubject(@PathParam("teacherId") int teacherId, @PathParam("subjectId") int subjectId,
            				  @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
    		try {
    			Teacher teacher = teacherService.assignSubjectToTeacher(teacherId, subjectId);
    			asyncResponse.resume(Response.ok(teacher).build());
    			} catch (Exception e) {
    				asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
    			}
    		});
}

    @PUT
    @Path("/{teacherId}/update-subject/{subjectId}")
    public void updateTeacherSubjects(@PathParam("teacherId") int teacherId, @PathParam("subjectId") int subjectId, 
    									 @Suspended final AsyncResponse asyncResponse) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Teacher teacher = teacherService.updateTeacherSubjects(teacherId, subjectId);
                asyncResponse.resume(Response.ok(teacher).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
    }


   
    @PUT
    @Path("/{teacherId}/replace-subject")
    public void replaceSubject(@PathParam("teacherId") int teacherId, @QueryParam("oldSubjectId") int oldSubjectId, 
    		@QueryParam("newSubjectId") int newSubjectId, @Suspended final AsyncResponse asyncResponse ) {
    	 AppExecutor.getExecutor().submit(() -> {
            try {
                Teacher teacher = teacherService.replaceSubjectForTeacher(teacherId, oldSubjectId, newSubjectId);
                asyncResponse.resume(Response.ok(teacher).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        });
    }
    
    
}
