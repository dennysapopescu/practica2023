package com.ssn.practica.work.BasicWarehouseManagement;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteLocationService extends Remote {
	void updateLoadUnitLocation(String barcode, String newLocationId) throws RemoteException;

	TransportOrders getOldestOrder() throws RemoteException;
}