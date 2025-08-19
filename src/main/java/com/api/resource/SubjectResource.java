package com.api.resource;

import com.api.dao.SubjectRepository;
import com.api.model.Subject;
import com.api.service.SubjectService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/subject")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubjectResource {

	private SubjectService subjectService = new SubjectService(new SubjectRepository());

    @GET
    public void getAllSubjects(@Suspended final AsyncResponse asyncResponse) {
    	new Thread(() -> {
            try {
                List<Subject> subjects = subjectService.getAllSubjects();
                asyncResponse.resume(Response.ok(subjects).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        }).start();
    }

    @GET
    @Path("/{id}")
    public void getSubjectById(@PathParam("id") int id, @Suspended final AsyncResponse asyncResponse) {
    	new Thread(() -> {
            try {
                Subject subject = subjectService.getSubjectById(id);
                asyncResponse.resume(Response.ok(subject).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
            }
        }).start();
    }

    @POST
    public void createSubject(Subject subject, @Suspended final AsyncResponse asyncResponse) {
    	new Thread(() -> {
            try {
                Subject subjects = subjectService.createSubject(subject);
                asyncResponse.resume(Response.ok(subjects).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        }).start();
    }

    @PUT
    @Path("/{id}")
    public void updateSubject(@PathParam("id") int id, Subject subject, @Suspended final AsyncResponse asyncResponse) {
    	new Thread(() -> {
            try {
            	Subject updatedSubject = subjectService.updateSubject(id, subject);
                asyncResponse.resume(Response.ok(updatedSubject).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.serverError().entity(e.getMessage()).build());
            }
        }).start();
    }

    @DELETE
    @Path("/{id}")
    public void deleteSubject(@PathParam("id") int id, @Suspended final AsyncResponse asyncResponse) {
        new Thread(() -> {
            try {
                subjectService.deleteSubject(id);
                asyncResponse.resume(Response.noContent().build());
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
            }
        }).start();
    }
}
