package com.api.service;
import java.util.List;

import com.api.dao.*;
import com.api.model.*;


public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(int id) {
        Teacher teacher = teacherRepository.findById(id);
        if (teacher == null) {
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

    public Teacher assignSubjectToTeacher(int teacherId, int subjectId) {
        Teacher teacher = getTeacherById(teacherId);
        Subject subject = subjectRepository.findById(subjectId);
        if (subject == null) {
            throw new RuntimeException("Subject not found");
        }
        teacher.getSubject().add(subject);
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacherSubjects(int teacherId, int subjectId) {
        Teacher teacher = getTeacherById(teacherId);
        Subject subject = subjectRepository.findById(subjectId);
        if (subject == null) {
            throw new RuntimeException("Subject not found");
        }
        teacher.getSubject().clear();
        teacher.getSubject().add(subject);
        return teacherRepository.save(teacher);
    }

    public Teacher replaceSubjectForTeacher(int teacherId, int oldSubjectId, int newSubjectId) {
        Teacher teacher = getTeacherById(teacherId);

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

        // load new subject
        Subject newSubject = subjectRepository.findById(newSubjectId);
        if (newSubject == null) {
            throw new RuntimeException("New subject not found");
        }

        // add new subject
        teacher.getSubject().add(newSubject);

        return teacherRepository.save(teacher);
    }

}
