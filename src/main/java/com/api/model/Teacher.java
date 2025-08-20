package com.api.model;



import java.util.HashSet;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


@Entity

public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int teacher_id;
	@Column(nullable = false)
	String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate hire_date;
	
	@ManyToMany(mappedBy = "teacher")
	@JsonIgnore
	private Set<Student> student = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "Subject_Teacher",
			joinColumns = @JoinColumn(name = "teacher_id"),
			inverseJoinColumns = @JoinColumn(name = "subject_id"))
	
	private Set<Subject> subject = new HashSet<>();
	
	public Teacher() {
		
	}
	
	public Teacher(int teacher_id, String name, LocalDate hire_date, Set<Student> student, Set<Subject> subject) {
		super();
		this.teacher_id = teacher_id;
		this.name = name;
		this.hire_date = hire_date;
		this.student = student;
		this.subject = subject;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getHire_date() {
		return hire_date;
	}

	public void setHire_date(LocalDate hire_date) {
		this.hire_date = hire_date;
	}

	public Set<Student> getStudent() {
		return student;
	}

	public void setStudent(Set<Student> student) {
		this.student = student;
	}

	public Set<Subject> getSubject() {
		return subject;
	}

	public void setSubject(Set<Subject> subject) {
		this.subject = subject;
	}

	
	
}
