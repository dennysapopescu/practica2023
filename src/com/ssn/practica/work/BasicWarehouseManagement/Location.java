package com.ssn.practica.work.BasicWarehouseManagement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Location {
	@Id
	private String id;
	private int culoar;
	private int x;
	private int y;
	private int z;

	private Tip type;

	public enum Tip {
		STORAGE, TRANSITION
	}

	@OneToMany
	@JoinColumn(name = "tipul", nullable = false)
	private LoadUnit loadUnit;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCuloar() {
		return culoar;
	}

	public void setCuloar(int culoar) {
		this.culoar = culoar;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Tip getType() {
		return type;
	}

	public void setType(Tip type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", culoar=" + culoar + ", x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	public Location(String id, int culoar, int x, int y, int z, Tip Type) {
		super();
		this.id = id;
		this.culoar = culoar;
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = Type;
	}

	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}

}
