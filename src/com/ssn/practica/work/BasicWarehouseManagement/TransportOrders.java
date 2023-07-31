package com.ssn.practica.work.BasicWarehouseManagement;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class TransportOrders {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "order_id")
	private Long orderId;
	@Column(name = "state", length = 10, nullable = false)
	private OrderState state;
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@OneToOne(mappedBy = "Location")
	private Location location;

	@OneToOne(mappedBy = "LoadUnit")
	private LoadUnit loadUnit;

	public TransportOrders() {

	}

	public TransportOrders(OrderState state, LoadUnit loadUnit, Location location) {
		this.state = state;
		this.createdAt = LocalDateTime.now();
		this.connectLoadUnit(loadUnit);
		this.connectLocation(location);
	}

	public enum OrderState {
		NEW, FINISHED, ERROR
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public void connectLoadUnit(LoadUnit loadUnit) {
		this.loadUnit = loadUnit;
	}

	public void connectLocation(Location location) {
		this.location = location;
	}

}
