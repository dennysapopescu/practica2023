package com.ssn.practica.personal.miniapp;

import java.util.List;
import java.util.Scanner;

public class MiniApp {
	
	private Scanner scan = new Scanner(System.in);
	private DatabaseOperations dbOps = new DatabaseOperations();
	private KeyboardUtils kb = new KeyboardUtils();
	
	public static void main(String[] args) throws Exception {
		MiniApp demo = new MiniApp();
		demo.run();
	}

	private void run() throws Exception {
		
		while(true)
		{
			showMenu();
			int option=scan.nextInt();
			scan.nextLine();
			
			switch(option)
			{
			case 1: //add article
				addArticle();
				break;
				
			case 2://add store
				addStore();
				break;
				
			case 3: //add price
				addPrice();
				break;
				
			case 4: //show stats
				dbOps.showStats();
				break;
				
			case 5: //delete article
				deleteArticle();
				break;
				
			case 6: //delete stoer
				deleteStore();
				break;
				
			case 7: //delete price
				deletePrice();
				break;
				
			case 8: //sort articles
				sortArticles();
				break;
				
			case 9: //sort stores
				sortStores();
				break;
				
			case 10: //sort prices
				sortPrices();
				break;
				
			case 11: //close
				System.out.println("The app is closing!");
				return;
				
			default:
				System.out.println("Invalid option!");
					
			}
		}
	}

	
	
	
	
	
	private static void showMenu() {
		System.out.println("MENU------------------------------------------------------------------------------------");
		System.out.println("1. Add article");
		System.out.println("2. Add store");
		System.out.println("3. Add price");
		System.out.println("4. Show statistics");
		System.out.println("5. Delete article");
		System.out.println("6. Delete store");
		System.out.println("7. Delete price");
		System.out.println("8. Sort articles");
		System.out.println("9. Sort stores");
		System.out.println("10. Sort prices");
		System.out.println("11. Close app");
		System.out.println("Choose an option: ");
	}
	
	
	private void addArticle() {
		String name = kb.getString("Article name: ");
		dbOps.addArticle(name);
	}
	
	private void addStore() {
		String name = kb.getString("Store name: ");
		dbOps.addStore(name);
	}
	
	private void addPrice() {
		String storeName = kb.getString("Store name: ");
		String articleName = kb.getString("Article name: ");
		int price = kb.getInt("Price: ");
		dbOps.addPrice(storeName, articleName, price);
	}
	
	private void deleteArticle() {
		String articleName = kb.getString("Article name: ");
		dbOps.deleteArticle(articleName);
	}
	
	private void deleteStore() {
		String storeName = kb.getString("Store name: ");
		dbOps.deleteStore(storeName);
	}
	
	private void deletePrice() {
		String articleName = kb.getString("Article name: ");
		String storeName = kb.getString("Store name: ");
		int price = kb.getInt("Price: ");
		dbOps.deletePrice(articleName, storeName);
	}
	
	private void sortArticles() {
		List<Article> sortedArticles = dbOps.getSortedArticles();
		System.out.println("Sorted articles: ");
		for(Article article : sortedArticles) {
			System.out.println(article.getName());
		}
	}

	private void sortStores() {
		List<Store> sortedStores = dbOps.getSortedStores();
		System.out.println("Sorted stores: ");
		for(Store store : sortedStores) {
			System.out.println(store.getName());
		}
	}
	
	private void sortPrices() {
		List<Price> sortedPrices = dbOps.getSortedPrices();
		System.out.println("Sorted prices: ");
		for(Price price : sortedPrices) {
			System.out.println("Article: " + price.getArticle().getName() + " Store: " + price.getStore().getName() + " Price: " + price.getValue());
		}
	}

}
