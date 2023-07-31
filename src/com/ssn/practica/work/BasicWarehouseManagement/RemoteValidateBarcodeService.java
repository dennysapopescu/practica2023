package com.ssn.practica.work.BasicWarehouseManagement;

import java.rmi.*;

public interface RemoteValidateBarcodeService extends Remote {
	String validateBarcodePalet(String barcode) throws RemoteException;
}
