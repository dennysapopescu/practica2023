package com.ssn.practica.work.BasicWarehouseManagement;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ssn.practica.work.BasicWarehouseManagement.utils.DBOperations;

public class RemoteValidateBarcodeServiceImpl implements RemoteValidateBarcodeService{
	private static final long serialVersionUID = 1L;
	private DBOperations db = new DBOperations();
	private static SessionFactory sessionFactory;
	
	public RemoteValidateBarcodeServiceImpl() throws RemoteException {
		super();
	}
	
	@Override
	public String validateBarcodePalet(String barcode) throws RemoteException {
		if(barcodeIsValid(barcode) && !existingPalet(barcode)) {
			return "";
		} else {
			return "invalid barcode or existing palet";
		}
	}
	
	public boolean barcodeIsValid(String barcode) {
		return barcode != null && barcode.matches("^\\d{8}$");
	}
	
	public boolean existingPalet(String barcode) {
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		Long count = db.getLoadUnitCountByBarcode(barcode, null);
		return count>0;
//		return false;
	}
	
	 public static void main(String[] args) {
	      
	        try {
	            String name = "RemoteValidateBarcodeService";
	            final RemoteValidateBarcodeService service = new RemoteValidateBarcodeServiceImpl();
	            final RemoteValidateBarcodeService stub =
	                (RemoteValidateBarcodeService) UnicastRemoteObject.exportObject(service, 1099);
	            final Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
	            registry.bind(name, stub);
	            System.out.println("RemoteValidateBarcodeService bound");
	        } catch (Exception e) {
	            System.err.println("RemoteValidateBarcodeService exception:");
	            e.printStackTrace();
	        }
	    }
}
