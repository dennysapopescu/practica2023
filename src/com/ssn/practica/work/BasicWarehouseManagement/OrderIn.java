package com.ssn.practica.work.BasicWarehouseManagement;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ssn.practica.work.BasicWarehouseManagement.main.Status;

@Entity
public class OrderIn {
	@Id
	private String id;
	@Id
	private Long line;
	private String article;
	private int quantity;

	private Status status;

	public OrderIn() {

	}

	public OrderIn(String article, int quantity, Status status) {
		super();
		this.article = article;
		this.quantity = quantity;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public String getArticle() {
		return article;
	}

	public int getQuantity() {
		return quantity;
	}

	public Status getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
