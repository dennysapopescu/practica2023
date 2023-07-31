package com.ssn.practica.work.BasicWarehouseManagement.main;

import org.hibernate.Session;

import com.ssn.practica.work.utils.WithSessionAndTransaction;

public class App {

	public static void main(String[] args) {
		App app = new App();
		app.initDatabase();

	}

	private void initDatabase() {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
			}
		}.run();
	}
}
