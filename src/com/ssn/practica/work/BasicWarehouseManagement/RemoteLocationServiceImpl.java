package com.ssn.practica.work.BasicWarehouseManagement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class RemoteLocationServiceImpl extends UnicastRemoteObject implements RemoteLocationService {

	private final SessionFactory sessionFactory;
	private Timer timer;

	public RemoteLocationServiceImpl() throws RemoteException {
		super();
		sessionFactory = new Configuration().configure().buildSessionFactory();
		startCheckingOldestOrder();
	}

	private void startCheckingOldestOrder() {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new OldestOrderChecker(), 0, 10000);
	}

	private class OldestOrderChecker extends TimerTask {
		@Override
		public void run() {
			try {
				TransportOrders oldestOrder = getOldestOrder();
				if (oldestOrder != null) {
					System.out.println("Oldest Order: " + oldestOrder);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateLoadUnitLocation(String barcode, String newLocationId) throws RemoteException {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();

				LoadUnit loadUnit = session.get(LoadUnit.class, barcode);
				if (loadUnit == null) {
				} else {
					Location newLocation = session.get(Location.class, newLocationId);
					if (newLocation == null) {
					} else {
						loadUnit.connectLocation(newLocation);
						session.update(loadUnit);
						transaction.commit();
					}
				}
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				throw new RemoteException("Error updating load unit location: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public TransportOrders getOldestOrder() {
		Session session = sessionFactory.openSession();
		TypedQuery<TransportOrders> query = session.createQuery("FROM TransportOrders t ORDER BY t.createdAt ASC",
				TransportOrders.class);
		query.setMaxResults(1);
		return query.getResultList().stream().findFirst().orElse(null);
	}
}

// Main implementation
//    public static void main(String[] args) {
//        try {
//            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
//            RemoteLocationService remoteLocationService = (RemoteLocationService) registry.lookup("RemoteLocationService");
//
//
//            remoteLocationService.updateLoadUnitLocation(loadUnitBarcode, newLocationId);
//
//        } catch (Exception e) {
//            System.err.println("RMI Client exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
