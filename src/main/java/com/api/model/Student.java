package com.api.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int student_id;
	
	@Column(nullable = false)
	String name;
	
	String address;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Student_Teacher",
			joinColumns = @JoinColumn(name= "student_id"),
			inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	@JsonManagedReference
	private Set<Teacher> teacher = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Student_Subject",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@JsonManagedReference
    private Set<Subject> subject = new HashSet<>();
	
	
	public Student() {
		
	}


	public Student(int student_id, String name, String address, Set<Teacher> teacher, Set<Subject> subject) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.address = address;
		this.teacher = teacher;
		this.subject = subject;
	}


	public int getStudent_id() {
		return student_id;
	}


	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Set<Teacher> getTeacher() {
		return teacher;
	}


	public void setTeacher(Set<Teacher> teacher) {
		this.teacher = teacher;
	}


	public Set<Subject> getSubject() {
		return subject;
	}


	public void setSubject(Set<Subject> subject) {
		this.subject = subject;
	}


	
	

}
