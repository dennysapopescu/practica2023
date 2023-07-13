package com.ssn.practica.personal.miniapp;

import java.util.List;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.practica.personal.utils.WithSessionAndTransaction;


@Entity
public class DatabaseOperations {
	private static Scanner scan = new Scanner(System.in);

	static void addArticle() {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				
				System.out.println("type article's name: ");
				String articleName = scan.nextLine();

				Article article = new Article(articleName);
				session.persist(article);
				
				System.out.println("Article " + articleName + " added.");

			}
		}.run();
	}
	
	
	
	static void addStore()
	{
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				
				System.out.println("type store's name: ");
				String storeName = scan.nextLine();

				Store store = new Store(storeName);
				session.persist(store);
				
				System.out.println("Store " + storeName + " added.");

			}
		}.run();
	}
	
	
	
	static void addPrice()
	{
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				
				System.out.println("type article's name: ");
				String articleName = scan.nextLine();
				System.out.println("type store's name: ");
				String storeName = scan.nextLine();
				System.out.println("type the price: ");
				int price = scan.nextInt();
				scan.nextLine();
				
				TypedQuery<Article> articleQuery = session.createQuery("select a from Article a where a.name = :name", Article.class);
				articleQuery.setParameter("name", articleName);
				List<Article> articles = articleQuery.getResultList();

				TypedQuery<Store> storeQuery = session.createQuery("select s from Store s where s.name = :name", Store.class);
				storeQuery.setParameter("name", storeName);
				List<Store> stores = storeQuery.getResultList();
				
				
				if(articles.isEmpty() && stores.isEmpty())
				{
					System.out.println("The article or the store doesn't exist.");
				}
				else
				{
					Article article = articles.get(0);
					Store store = stores.get(0);
					
					Price priceInstance = new Price(price, store, article);
					session.persist(priceInstance);
					
					System.out.println("Price for the article " + articleName + " added.");
				}
				
			}
		}.run();
	}
	
	
	
	static void showStats()
	{
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {

				Query<Article> articleQuery = session.createQuery("select a from Article a", Article.class);
				List<Article> articles = articleQuery.getResultList();

				for(Article art : articles) {
					Query<Integer> priceQuery = session.createQuery("select MIN(p.value) from Price p where p.article=:article", Integer.class);
					priceQuery.setParameter("article", art);
					Integer minPrice = priceQuery.uniqueResult();
					
					if(minPrice==null)
					{
						continue;
					}
					
					Query<Store> storeQuery = session.createQuery("select p.store from Price p where p.article=:article and p.value=:price", Store.class);
					storeQuery.setParameter("article", art);
					storeQuery.setParameter("price", minPrice);
					Store store = storeQuery.uniqueResult();
				
					System.out.println("Article: " + art.getName());
					System.out.println("Store: " + store.getName());
					System.out.println("Minimum price: " + minPrice);
				}

			}
		}.run();
		
		
		/*
		new WithSessionAndTransaction() {
		    @Override
		    public void doAction(Session session) {
		        Query<Article> query = session.createQuery("select a from Article a " +
		                "join a.prices p " +
		                "join p.store s " +
		                "where p.value = (select MIN(p2.value) from Price p2 where p2.article = a) " +
		                "order by a.name", Article.class);

		        List<Article> articles = query.getResultList();

		        for (Article art : articles) {
		            Query<Price> priceQuery = session.createQuery("select p from Price p " +
		                    "where p.article = :article and p.value = (select MIN(p2.value) from Price p2 where p2.article = :article)", Price.class);
		            priceQuery.setParameter("article", art);
		            Price minPrice = priceQuery.uniqueResult();

		            if (minPrice == null) {
		                continue;
		            }

		            Store store = minPrice.getStore();

		            System.out.println("Article: " + art.getName());
		            System.out.println("Store: " + store.getName());
		            System.out.println("Minimum price: " + minPrice);
		        }
		    }
		}.run();
		*/
	}
	
}
