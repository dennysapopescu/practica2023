package com.ssn.practica.personal.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Trainee {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private String name;
	private int age;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Trainee_Course", //
			joinColumns = { @JoinColumn(name = "trainee_id") }, //
			inverseJoinColumns = { @JoinColumn(name = "course_id") })
	private List<Course> courses = new ArrayList<>();

	@OneToMany(mappedBy = "trainee")
	private List<Evaluation> evaluations = new ArrayList<>();

	public Trainee() {

	}

	public Trainee(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	@Override
	public String toString() {
		return "Trainee [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
