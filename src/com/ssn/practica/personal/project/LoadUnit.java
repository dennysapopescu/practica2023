package com.ssn.practica.personal.project;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class LoadUnit {
	@Id

	private String barcode;
	private String tipul;

	@OneToOne(mappedBy = "StockItem")
	private StockItem stockItems;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	public LoadUnit(String barcode, String tipul, String location_id, StockItem stockItem) {
		super();
		this.connectLocation(location);
		this.connectStockItem(stockItem);
		this.barcode = barcode;
		this.tipul = tipul;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getTipul() {
		return tipul;
	}

	public void setTipul(String tipul) {
		this.tipul = tipul;
	}

	public void connectStockItem(StockItem stockItem) {
		this.stockItems = stockItem;
	}

	public void connectLocation(Location location) {
		this.location = location;
	}
}
