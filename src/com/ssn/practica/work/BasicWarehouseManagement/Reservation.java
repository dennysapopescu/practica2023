package com.ssn.practica.work.BasicWarehouseManagement;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private long reservationID;

	@OneToOne
	@JoinColumn(name = "stockItemID")
	private StockItem stockItemID;

	@OneToOne(mappedBy = "reservation")
	private OrderOut orderOutID;

	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation(int quantityReserved, LocalDateTime reservedDate) {
		super();
		this.quantityReserved = quantityReserved;
		this.reservedDate = reservedDate;
	}

	public long getReservationID() {
		return reservationID;
	}

	public void setReservationID(long reservationID) {
		this.reservationID = reservationID;
	}

	public StockItem getStockItemID() {
		return stockItemID;
	}

	public OrderOut getOrderOutID() {
		return orderOutID;
	}

	public int getQuantityReserved() {
		return quantityReserved;
	}

	public void setQuantityReserved(int quantityReserved) {
		this.quantityReserved = quantityReserved;
	}

	public LocalDateTime getReservedDate() {
		return reservedDate;
	}

	public void setReservedDate(LocalDateTime reservedDate) {
		this.reservedDate = reservedDate;
	}

	private int quantityReserved;
	private LocalDateTime reservedDate;

	public void connectStockItem(StockItem stockItem) {
		this.stockItemID = stockItem;
	}

	public void connectOrderOut(OrderOut orderOut) {
		this.orderOutID = orderOut;
	}
}
