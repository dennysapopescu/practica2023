package com.ssn.practica.personal.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class StockItem {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="article_id")
	private Article article;
	
	@Column(name="quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="lot_id")
	private Lot lot;

	public StockItem() {}
	
	public StockItem(Article article, int quantity, Lot lot) {
		super();
		this.article = article;
		this.quantity = quantity;
		this.lot = lot;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}
}
