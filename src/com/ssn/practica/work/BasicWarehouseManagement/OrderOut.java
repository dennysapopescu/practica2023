package com.ssn.practica.work.BasicWarehouseManagement;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ssn.practica.work.BasicWarehouseManagement.main.Status;

@Entity
public class OrderOut {

	@Id
	private Long id;

	@Id
	private Long line;
	private String article;
	private int quantity;

	private Status status;

	public OrderOut(String article, int quantity, Status status) {
		super();
		this.article = article;
		this.quantity = quantity;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLine() {
		return line;
	}

	public void setLine(Long line) {
		this.line = line;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
