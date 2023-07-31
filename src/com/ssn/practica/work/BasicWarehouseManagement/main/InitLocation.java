package com.ssn.practica.work.BasicWarehouseManagement.main;

import org.hibernate.Session;

import com.ssn.practica.work.BasicWarehouseManagement.Location;
import com.ssn.practica.work.BasicWarehouseManagement.Location.Tip;
import com.ssn.practica.work.utils.WithSessionAndTransaction;

public class InitLocation {

	protected static final Object Locations = null;

	public static void main(String[] args) {
		InitLocation init = new InitLocation();
		initId();
	}

	private static void initId() {
		new WithSessionAndTransaction() {
			private String id;

			@Override
			public void doAction(Session session) {
				for (int culoar = 1; culoar <= 5; culoar++) {
					for (int x = 1; x <= 100; x++) {
						for (int y = 1; y <= 5; y++) {
							for (int z = 1; z <= 2; z++) {
								Location location = new Location(
										"HBW" + String.format("%02d", culoar) + String.format("%03d", x)
												+ String.format("%02d", y) + String.format("%02d", z),
										culoar, x, y, z, Tip.STORAGE);
								session.save(location);
							}
						}
					}

				}
				Location location = new Location("IN", 0, 0, 0, 0, Tip.TRANSITION);
				location.setId("IN");
				session.save(location);

				Location location2 = new Location("OUT", 0, 0, 0, 0, Tip.TRANSITION);
				location2.setId("OUT");
				session.save(location2);

				Location location3 = new Location("PICKING", 0, 0, 0, 0, Tip.TRANSITION);
				location3.setId("PICKED");
				session.save(location3);

			}

		}.run();

	}

}
