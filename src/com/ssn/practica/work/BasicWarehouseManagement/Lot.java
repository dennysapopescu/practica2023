package com.ssn.practica.work.BasicWarehouseManagement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Lot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private String date;

	@OneToMany
	@JoinColumn(name = "barcode", nullable = false)
	private Article article;

	public Lot(String date, Article article) {
		super();
		this.date = date;
		this.connectArticle(article);
	}

	public Lot() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setValue(String date) {
		this.date = date;
	}

	public Article getArticle() {
		return article;
	}

	public void connectArticle(Article article) {
		this.article = article;
	}

}
