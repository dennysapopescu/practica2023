package com.ssn.practica.work.BasicWarehouseManagement;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteCreatePaletService extends Remote{
	public String createPalet(String barcode, String article, int quantity) throws RemoteException;
}
