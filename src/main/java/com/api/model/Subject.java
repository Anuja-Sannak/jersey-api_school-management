package com.api.model;

import java.util.HashSet;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;


@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int subject_id;
	
	@Column(nullable = false)
	String name;
	
	@ManyToMany(mappedBy = "subject")
	@JsonIgnore
	private Set<Student> student = new HashSet<>();
	
	@ManyToMany(mappedBy = "subject")
	@JsonIgnore
	private Set<Teacher> teacher = new HashSet<>();
	
	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Student> getStudent() {
		return student;
	}

	public void setStudent(Set<Student> student) {
		this.student = student;
	}

	public Set<Teacher> getTeacher() {
		return teacher;
	}

	public void setTeacher(Set<Teacher> teacher) {
		this.teacher = teacher;
	}

	public Subject() {
		
	}
	
	public Subject(int subject_id, String name, Set<Student> student, Set<Teacher> teacher) {
		super();
		this.subject_id = subject_id;
		this.name = name;
		this.student = student;
		this.teacher = teacher;
	}

	
	
	

}
