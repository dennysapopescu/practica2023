package com.ssn.practica.work.BasicWarehouseManagement;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ssn.practica.personal.utils.WithSessionAndTransaction;
import com.ssn.practica.work.BasicWarehouseManagement.TransportOrders.OrderState;
import com.ssn.practica.work.BasicWarehouseManagement.utils.DBOperations;

public class RemoteCreatePaletServiceImpl implements RemoteCreatePaletService{

	private static final long serialVersionUID = 1L;
	private DBOperations db = new DBOperations();
	private RemoteValidateBarcodeService validate;  
	private static SessionFactory sessionFactory;
	
	public RemoteCreatePaletServiceImpl() throws RemoteException {
		super();
		validate = new  RemoteValidateBarcodeServiceImpl();
	}
	
	

	
	@Override
	public String createPalet(String barcode, String article, int quantity) throws RemoteException {
		new WithSessionAndTransaction() {
		@Override
		    public void doAction(Session session) {
		        // Move the palet creation logic here.
		        String validationMessage = null;
				try {
					validationMessage = validate.validateBarcodePalet(barcode);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        if (validationMessage.isEmpty()) {
		            LoadUnit existingLoadUnit = db.getLoadUnitByBarcode(barcode, session);
		            if (existingLoadUnit == null) {
		                Location freeStorageLocation = db.findFreeStorageLocation(session);
		                if (freeStorageLocation != null) {
		                    LoadUnit newLoadUnit = new LoadUnit(barcode, "");
		                    db.addLoadUnit(barcode, "", null, freeStorageLocation);
		                    db.addTransportOrders(OrderState.NEW, newLoadUnit, freeStorageLocation);
		                    setReturnValue("Palet with barcode " + barcode + " created successfully and added to transport order.");
		                } else {
		                    setReturnValue("No free storage locations found.");
		                }
		            } else {
		                setReturnValue("Palet with barcode " + barcode + " already exists in the database.");
		            }
		        } else {
		            setReturnValue("Error: " + validationMessage);
		        }
		    }
		}.run();
		return article;
	}
	
	
	
	 public static void main(String[] args) {
	      
	        try {
	            String name = "RemoteCreatePaletService";
	            final RemoteCreatePaletService service = new RemoteCreatePaletServiceImpl();
	            final RemoteCreatePaletService stub =
	                (RemoteCreatePaletService) UnicastRemoteObject.exportObject(service, 1099);
	            final Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
	            registry.bind(name, stub);
	            System.out.println("RemoteCreatePaletService bound");
	        } catch (Exception e) {
	            System.err.println("RemoteCreatePaletService exception:");
	            e.printStackTrace();
	        }
	    }
	    
}
