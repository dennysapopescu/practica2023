package com.ssn.practica.work.BasicWarehouseManagement.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.practica.work.BasicWarehouseManagement.Article;
import com.ssn.practica.work.BasicWarehouseManagement.LoadUnit;
import com.ssn.practica.work.BasicWarehouseManagement.Location;
import com.ssn.practica.work.BasicWarehouseManagement.Location.Tip;
import com.ssn.practica.work.BasicWarehouseManagement.Lot;
import com.ssn.practica.work.BasicWarehouseManagement.OrderIn;
import com.ssn.practica.work.BasicWarehouseManagement.OrderOut;
import com.ssn.practica.work.BasicWarehouseManagement.Reservation;
import com.ssn.practica.work.BasicWarehouseManagement.StockItem;
import com.ssn.practica.work.BasicWarehouseManagement.TransportOrders;
import com.ssn.practica.work.BasicWarehouseManagement.TransportOrders.OrderState;
import com.ssn.practica.work.BasicWarehouseManagement.main.Status;
import com.ssn.practica.work.utils.WithSessionAndTransaction;

public class DBOperations {

	public void addLocation(String id, int culoar, int x, int y, int z, Tip type) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				Location l = new Location(id, culoar, x, y, z, type);
				session.save(l);
			}
		}.run();

	}

	public void addArticle(String barcode, String description, float weight) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				Article a = new Article(barcode, description, weight);
				session.save(a);
			}
		}.run();

	}

	public void addLot(String date, Article barcode) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				Article article = getArticleByBarcode(barcode, session);

				if (barcode == null) {
					throw new RuntimeException("Articol inexistent: " + barcode);
				}

				Lot lot = new Lot(date, barcode);
				session.save(lot);

			}
		}.run();
	}

	public void addLoadUnit(String barcode, String tipul, StockItem stockItem, Location location_id) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				LoadUnit loadUnit = getLoadUnitByBarcode(barcode, session);
				if (barcode == null) {
					throw new RuntimeException("Load unit inexistent: " + barcode);
				}

				LoadUnit loadUnit2 = new LoadUnit(barcode, tipul);
				loadUnit2.connectStockItem(stockItem);
				loadUnit2.connectLocation(location_id);
				session.save(loadUnit2);
			}

		}.run();

	}

	public void addOrderIn(String article, int quantity, Status status) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				OrderIn orderIn = new OrderIn(article, quantity, status);
				session.save(orderIn);
			}
		}.run();

	}

	public void addOrderOut(String article, int quantity, Status status) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				OrderOut orderOut = new OrderOut(article, quantity, status);
				session.save(orderOut);
			}
		}.run();

	}

	public void addReservation(StockItem stockItemID, OrderOut orderOutID, int quantityReserved,
			LocalDateTime reservedDate) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				Reservation reservation = new Reservation(quantityReserved, reservedDate);
				reservation.connectOrderOut(orderOutID);
				reservation.connectStockItem(stockItemID);
				session.save(reservation);
			}
		}.run();
	}

	public void addStockItem(Article id, int quantity, Lot lot) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				StockItem stockItem = getStockItemById(id, session);
				if (id == null) {
					throw new RuntimeException("Stock item inexistent: " + id);
				}
				StockItem stockItem2 = new StockItem(id, quantity, lot);
				session.save(stockItem2);
			}
		}.run();
	}

	public void addTransportOrders(OrderState state, LoadUnit loadUnit, Location id) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				Location location = getLocationByID(id, session);
				if (id == null) {
					throw new RuntimeException("Locatie inexistenta:" + id);
				}

				TransportOrders transportOrders = new TransportOrders(state, loadUnit, id);
				session.save(transportOrders);
			}
		}.run();
	}

	private Article getArticleByBarcode(Article barcode, Session session) {
		Query<Article> query = session.createQuery("from Article where barcode = :barcode", Article.class);
		query.setParameter("barcode", barcode);
		Article article = query.uniqueResult();
		return article;
	}

	private StockItem getStockItemById(Article id, Session session) {
		Query<StockItem> query = session.createQuery("from StockItem where id = :id", StockItem.class);
		query.setParameter("id", id);
		StockItem stockItem = query.uniqueResult();
		return stockItem;
	}

	private OrderState getOrderStatebyState(OrderState state, Session session) {
		Query<OrderState> query = session.createQuery("from OrderState where state = :state", OrderState.class);
		query.setParameter("state", state);
		OrderState orderState = query.uniqueResult();
		return orderState;
	}

	public LoadUnit getLoadUnitByBarcode(String barcode, Session session) {
		Query<LoadUnit> query = session.createQuery("from LoadUnit where barcode = :barcode", LoadUnit.class);
		query.setParameter("barcode", barcode);
		LoadUnit loadUnit = query.uniqueResult();
		return loadUnit;
	}

	private Location getLocationByID(Location id, Session session) {
		Query<Location> query = session.createQuery("from Location where id = :id", Location.class);
		query.setParameter("id", id);
		Location location = query.uniqueResult();
		return location;
	}

	public Location findFreeStorageLocation(Session session) {
		try {
			TypedQuery<Location> query = session.createQuery("SELECT l FROM Location l WHERE l.type = :type",
					Location.class);
			query.setParameter("type", Tip.STORAGE);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean checkStockForPicking(OrderOut ord, Session session) {
	    String articleBarcode = ord.getArticle();
	    int quantity = ord.getQuantity();

	    Article article = getArticleByBarcode(new Article(articleBarcode), session);

	    return isStockAvailable(article, quantity, session);
	}

	public boolean isStockAvailable(Article article, int quantity, Session session) {
	    StockItem stockItem = getStockItemById(article, session);

	    if (stockItem == null) {
	        return false; 
	    }

	    return stockItem.getQuantity() >= quantity;
	}

	

	public List<OrderOut> getNewOrdersFromDB(Session session) {
        List<OrderOut> newOrders = new ArrayList<>();
        try {
            Query<OrderOut> query = session.createQuery("FROM OrderOut WHERE status = :status", OrderOut.class);
            query.setParameter("status", Status.NEW); 
            newOrders = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return newOrders;
    }
	
	

	public void createReservationForPicking(OrderOut ord, Session session) {
        String articleBarcode = ord.getArticle();
        int quantity = ord.getQuantity();

        Article article = getArticleByBarcode(new Article(articleBarcode), session);
        StockItem stockItem = getStockItemById(article, session);

        if (stockItem == null) {
            throw new RuntimeException("StockItem inexistent pentru articolul cu codul de bare: " + articleBarcode);
        }

        if (stockItem.getQuantity() < quantity) {
            throw new RuntimeException("Nu există suficient stoc disponibil pentru preluarea comenzii cu id-ul: " + ord.getId());
        }

       //update stockitem
        stockItem.setQuantity(stockItem.getQuantity() - quantity);
        session.update(stockItem);

        //new reservation
        Reservation reservation = new Reservation();
        reservation.connectStockItem(stockItem);
       // reservation.connectOrderOut(null); 
        reservation.setQuantityReserved(quantity);
        reservation.setReservedDate(LocalDateTime.now());
        session.save(reservation);
    }

	
	
	
	 public void pickForTransport(OrderOut ord, Session session) {
	        try {
	            Reservation reservation = getReservationForOrder(ord, session);

	            if (reservation == null) {
	                throw new RuntimeException("Nu există o rezervare pentru preluarea comenzii cu id-ul: " + ord.getId());
	            }

	           
	            ord.setStatus(Status.PICKED);
	            session.update(ord);

	           
	        } catch (Exception e) {
	           
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	    }

	    private Reservation getReservationForOrder(OrderOut ord, Session session) {
	        Query<Reservation> query = session.createQuery("FROM Reservation WHERE orderOutID = :orderId", Reservation.class);
	        query.setParameter("orderId", ord.getId());
	        return query.uniqueResult();
	    }

	    
	    
	    public void updateOrderStatus(OrderOut ord, Session session) {
	        try {
	        	OrderOut existingOrder = session.get(OrderOut.class, ord.getId());
	            if (existingOrder == null) {
	                throw new RuntimeException("Comanda cu id-ul " + ord.getId() + " nu există în baza de date.");
	            }
	            session.update(existingOrder);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public Long getLoadUnitCountByBarcode(String barcode, Session session) {
	    	Query<Long> query = session.createQuery("select count(l)from LoadUnit l where l.barcode=:barcode", Long.class);
	    	query.setParameter("barcode", barcode);
	    	return query.uniqueResult();
	    }
	    
	    
		
}
