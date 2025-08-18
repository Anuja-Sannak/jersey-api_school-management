package com.api.resource;

import com.api.dao.SubjectRepository;
import com.api.model.Subject;
import com.api.service.SubjectService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/subject")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubjectResource {

	private SubjectService subjectService = new SubjectService(new SubjectRepository());

    @GET
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GET
    @Path("/{id}")
    public Subject getSubjectById(@PathParam("id") int id) {
        return subjectService.getSubjectById(id);
    }

    @POST
    public Subject createSubject(Subject subject) {
        return subjectService.createSubject(subject);
    }

    @PUT
    @Path("/{id}")
    public Subject updateSubject(@PathParam("id") int id, Subject subject) {
        return subjectService.updateSubject(id, subject);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSubject(@PathParam("id") int id) {
        subjectService.deleteSubject(id);
    }
}
