package com.api.service;

import java.util.List;
import com.api.dao.SubjectRepository;
import com.api.model.Subject;

public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(int id) {
        Subject subject = subjectRepository.findById(id);
        if (subject == null) {
            throw new RuntimeException("Subject not found");
        }
        return subject;
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(int id, Subject updatedSubject) {
        Subject existingSubject = subjectRepository.findById(id);
        if (existingSubject == null) {
            throw new RuntimeException("Subject not found");
        }
        existingSubject.setName(updatedSubject.getName());
        return subjectRepository.save(existingSubject);
    }

    public void deleteSubject(int id) {
        subjectRepository.deleteById(id);
    }
}
