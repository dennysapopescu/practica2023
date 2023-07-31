package com.ssn.practica.work.BasicWarehouseManagement;

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

	@OneToOne
	private StockItem stockItems;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	public LoadUnit(String barcode, String tipul) {
		super();
		this.barcode = barcode;
		this.tipul = tipul;
	}
	
	public LoadUnit() {}
	
	

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	//public StockItem getStockItems() {
	//	return stockItems;
	//}

	public void setStockItems(StockItem stockItems) {
		this.stockItems = stockItems;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void connectStockItem(StockItem stockItem) {
		this.stockItems = stockItem;
	}

	public void connectLocation(Location location) {
		this.location = location;
	}



	public StockItem getStockItems() {
		return stockItems;
	}
	
	
}
