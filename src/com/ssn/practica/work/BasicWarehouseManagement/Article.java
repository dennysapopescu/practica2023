package com.ssn.practica.work.BasicWarehouseManagement;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Article {
	@Id
	private String barcode;

	private String description;
	private float weight;

	public Article() {

	}

	public Article(String barcode, String description, float weight) {
		super();
		this.barcode = barcode;
		this.description = description;
		this.weight = weight;
	}

	public Article(String articleBarcode) {
		this.barcode=articleBarcode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

}
