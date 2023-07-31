package com.ssn.practica.work.BasicWarehouseManagement;

import java.util.ArrayList;
import java.util.List;

import com.ssn.practica.personal.project.StockItem;

public class Palet {
	    private String barcode;
	    private List<StockItem> stockItems;

	    public Palet(String barcode) {
	        this.barcode = barcode;
	        this.stockItems = new ArrayList<>();
	    }

	    public String getBarcode() {
	        return barcode;
	    }

	    public void addStockItem(StockItem stockItem) {
	        stockItems.add(stockItem);
	    }

	    public StockItem findStockItemByArticle(String article) {
	        for (StockItem stockItem : stockItems) {
	            if (stockItem.getArticle().equals(article)) {
	                return stockItem;
	            }
	        }
	        return null;
	    }
	

}
