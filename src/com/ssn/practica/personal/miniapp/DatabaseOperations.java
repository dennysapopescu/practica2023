package com.ssn.practica.personal.miniapp;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.practica.personal.utils.WithSessionAndTransaction;


@Entity
public class DatabaseOperations {
	//private static Scanner scan = new Scanner(System.in);

	public void addArticle(String name) {
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				
				//System.out.println("type article's name: ");
				//String articleName = scan.nextLine();

				Article article = new Article(name);
				session.save(article);
				
				//System.out.println("Article " + articleName + " added.");

			}
		}.run();
	}
	
	
	
	public void addStore(String name)
	{
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				
				//System.out.println("type store's name: ");
				//String storeName = scan.nextLine();

				Store store = getStoreByName(name, session);
				if (store != null) {
					throw new RuntimeException("Magazinul exista deja: " + name);
				}

				Store st = new Store(name);
				session.save(st);
				
				//System.out.println("Store " + storeName + " added.");

			}
		}.run();
	}
	
	
	
	public void addPrice(String articleName, String storeName, int price)
	{
		new WithSessionAndTransaction() {
			@Override
			public void doAction(Session session) {
				/*
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
				*/
				Store store = getStoreByName(storeName, session);
				if(store==null)
				{
					throw new RuntimeException("Magazin inexistent:  "+storeName);
				}
				
				Article article = getArticleByName(articleName, session);
				if(article==null)
				{
					throw new RuntimeException("Articol inexistent:  "+ articleName);
				}
				
				Price p = new Price(price, store, article);
				session.save(p);
				
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
		}.run();*/
		
	}
	
	
	
	private Store getStoreByName(String name, Session session) {
		Query<Store> query = session.createQuery("from Store where name = :name", Store.class);
		query.setParameter("name", name);
		Store store = query.uniqueResult();
		return store;
	}
	
	private Article getArticleByName(String articleName, Session session) {
		Query<Article> query = session.createQuery("from Article where name = :name", Article.class);
		query.setParameter("name", articleName);
		Article article = query.uniqueResult();
		return article;
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
		
	
	
	//stores
	private List<Store> getStores(Session session) {
		Query<Store> query = session.createQuery("from Store", Store.class);
		return query.list();
	}

	 public List<Store> getAllStores() {
	        return new WithSessionAndTransaction<List<Store>>() {
	            @Override
	            public void doAction(Session session) {
	                setReturnValue(getStores(session));
	            }
	        }.run();
	    }
	
	
	
	//prices
		private List<Price> getPrices(Session session) {
			Query<Price> query = session.createQuery("from Price", Price.class);
			return query.list();
		}

		public List<Price> getAllPrices(){
			return new WithSessionAndTransaction<List<Price>>() {
				@Override
				public void doAction(Session session) {
					setReturnValue(getPrices(session));
				}
			}.run();
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
	
		
		
		public void deleteStore(String name)
		{
			new WithSessionAndTransaction() {
				@Override
				public void doAction(Session session) {
					Store store = getStoreByName(name, session);
					if(store != null) {
						session.delete(store);
					}
				}
			}.run();
		}
	
		
		
		public void deletePrice(String articleName, String storeName)
		{
			new WithSessionAndTransaction() {
				@Override
				public void doAction(Session session) {
					Article article = getArticleByName(articleName, session);
					Store store = getStoreByName(storeName, session);
					if(article != null && store != null) {
						Query<Price> query = session.createQuery("from Price where article=:article and store=:store", Price.class);
						query.setParameter("article", article);
						query.setParameter("store", store);
						List<Price> prices = query.list();
						if(!prices.isEmpty()) {
							for(Price p : prices) {
								session.delete(p);
							}
						}
					}
					
				}
			}.run();
		}
		
		
		
		//sort
		public List<Article> getSortedArticles(){
			return new WithSessionAndTransaction<List<Article>>() {
				@Override
				public void doAction(Session session) {
					Query<Article> query = session.createQuery("from Article order by name", Article.class);
					setReturnValue(query.list());
					
				}
			}.run();
		}
	
	
		
		public List<Store> getSortedStores(){
			return new WithSessionAndTransaction<List<Store>>() {
				@Override
				public void doAction(Session session) {
					Query<Store> query = session.createQuery("from Store order by name", Store.class);
					setReturnValue(query.list());
					
				}
			}.run();
		}
		
		
		
		public List<Price> getSortedPrices(){
			return new WithSessionAndTransaction<List<Price>>() {
				@Override
				public void doAction(Session session) {
					Query<Price> query = session.createQuery("from Price order by value", Price.class);
					setReturnValue(query.list());
					
				}
			}.run();
		}
	
}
