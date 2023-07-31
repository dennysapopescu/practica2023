package com.ssn.practica.personal.project;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.practica.personal.utils.WithSessionAndTransaction;


@Entity
public class DBOperations {
	//private static Scanner scan = new Scanner(System.in);

	public void addArticle(String name) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				
				//System.out.println("type article's name: ");
				//String articleName = scan.nextLine();

				Article article = new Article(name, name, 0);
				session.save(article);
				
				//System.out.println("Article " + articleName + " added.");

			}
		}.run();
	}
	
	//articles
		private List<Article> getArticles(Session session) {
			Query<Article> query = session.createQuery("from Article", Article.class);
			return query.list();
		}

		public List<Article> getAllArticles() {
			return new WithSessionAndTransaction<List<Article>>() {
				@Override
				public void doAction(Session session) {
					setReturnValue(getArticles(session));
				}
			}.run();
		}
		
		private Article getArticleByName(String articleName, Session session) {
			Query<Article> query = session.createQuery("from Article where name = :name", Article.class);
			query.setParameter("name", articleName);
			Article article = query.uniqueResult();
			return article;
		}
		
		
		//delete
				public void deleteArticle(String name)
				{
					new WithSessionAndTransaction() {
						@Override
						public void doAction(Session session) {
							Article article = getArticleByName(name, session);
							if(article != null) {
								session.delete(article);
							}
						}
					}.run();
				}
			
				
		public void addStockItem(Article article, int quantity, Lot lot) {
			new WithSessionAndTransaction() {
				@Override
				public void doAction(Session session) {
					
					//System.out.println("type article's name: ");
					//String articleName = scan.nextLine();

					StockItem stockItem = new StockItem(article, quantity, lot);
					session.save(stockItem);
					
					//System.out.println("Article " + articleName + " added.");

				}
			}.run();
		}
	
	
		private List<StockItem> getStockItems(Session session) {
			Query<StockItem> query = session.createQuery("from StockItem", StockItem.class);
			return query.list();
		}

		public List<StockItem> getAllStockItems() {
			return new WithSessionAndTransaction<List<StockItem>>() {
				@Override
				public void doAction(Session session) {
					setReturnValue(getStockItems(session));
				}
			}.run();
		}
		
		private StockItem getStockItemById(Long stockItemId, Session session) {
			Query<StockItem> query = session.createQuery("from StockItem where name = :name", StockItem.class);
			query.setParameter("id", stockItemId);
			StockItem stockItem = query.uniqueResult();
			return stockItem;
		}
		
		
		//delete
				public void deleteStockItem(Long stockItemId)
				{
					new WithSessionAndTransaction() {
						@Override
						public void doAction(Session session) {
							StockItem stockItem = getStockItemById(stockItemId, session);
							if(stockItem != null) {
								session.delete(stockItem);
							}
						}
					}.run();
				}
			
	
	
	
}
