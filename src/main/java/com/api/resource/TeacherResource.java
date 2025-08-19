package com.api.resource;

import java.util.List;

import com.api.model.Teacher;
import com.api.dao.*;
import com.api.service.TeacherService;

import jakarta.ws.rs.*;
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
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

   
    @GET
    @Path("/{id}")
    public Teacher getTeacherById(@PathParam("id") int id) {
        return teacherService.getTeacherById(id);
    }

    
    @POST
    public Teacher createTeacher(Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

   
    @PUT
    @Path("/{id}")
    public Teacher updateTeacher(@PathParam("id") int id, Teacher teacher) {
        return teacherService.updateTeacher(id, teacher);
    }

    
    @DELETE
    @Path("/{id}")
    public Response deleteTeacher(@PathParam("id") int id) {
        teacherService.deleteTeacher(id);
        return Response.noContent().build();
    }

   
    @POST
    @Path("/{teacherId}/assign-subject/{subjectId}")
    public Teacher assignSubject(@PathParam("teacherId") int teacherId, @PathParam("subjectId") int subjectId) {
        return teacherService.assignSubjectToTeacher(teacherId, subjectId);
    }

    @PUT
    @Path("/{teacherId}/update-subject/{subjectId}")
    public Teacher updateTeacherSubjects(@PathParam("teacherId") int teacherId, @PathParam("subjectId") int subjectId) {
        return teacherService.updateTeacherSubjects(teacherId, subjectId);
    }


   
    @PUT
    @Path("/{teacherId}/replace-subject")
    public Teacher replaceSubject(@PathParam("teacherId") int teacherId, @QueryParam("oldSubjectId") int oldSubjectId, @QueryParam("newSubjectId") int newSubjectId) {
        return teacherService.replaceSubjectForTeacher(teacherId, oldSubjectId, newSubjectId);
    }
}
