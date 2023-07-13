package com.ssn.practica.personal.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Event {

	private long id;
	private String title;
	private Date date;

	public Event() {

	}

	public Event(String title, Date date) {
		this.title = title;
		this.date = date;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EVENT_DATE")
	public Date getDate() {
		return date;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
