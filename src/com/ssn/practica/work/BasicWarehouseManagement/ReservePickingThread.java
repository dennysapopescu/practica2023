package com.ssn.practica.work.BasicWarehouseManagement;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ssn.practica.work.BasicWarehouseManagement.main.Status;
import com.ssn.practica.work.BasicWarehouseManagement.utils.DBOperations;

public class ReservePickingThread extends Thread {

    private DBOperations db;
    private static SessionFactory sessionFactory;
    
    
    public void ReservePickingThread() {
    	this.sessionFactory = sessionFactory;
    	this.db = db;
    }
    

    @Override
    public void run() {
    	Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		boolean running = true;
		boolean receivedResult = false;
    	while(running && !receivedResult) {
    		try {
    			Thread.sleep(10000);
    			
    			List<OrderOut> newOrders = db.getNewOrdersFromDB(session);
    			
    			for(OrderOut ord : newOrders) {
    				processPicking(ord, session);
    				if(receivedResult) {
    					running = false;
    					break;
    				}
    			}
    		} catch(InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    	session.close();
    }
 
    
    
    private void processPicking(OrderOut ord, Session session) {
    	boolean canBeReserved = db.checkStockForPicking(ord, session);
    	
    	if(canBeReserved) {
    		db.createReservationForPicking(ord, session);
    		db.pickForTransport(ord, session);
    		ord.setStatus(Status.STARTED);
    		db.updateOrderStatus(ord, session);
    	} else {
    		ord.setStatus(Status.ERROR);
    		db.updateOrderStatus(ord, session);
    	}
    }
    
}

	