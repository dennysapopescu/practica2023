package com.ssn.practica.personal.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.ssn.practica.personal.miniapp.Article;
import com.ssn.practica.personal.miniapp.DatabaseOperations;
import com.ssn.practica.personal.miniapp.Price;
import com.ssn.practica.personal.miniapp.Store;

public class PricesDialog {

	private Display display;
    private Shell shell;
    private DatabaseOperations db = new DatabaseOperations();
    private Table table;
    
    private List<Price> prices;
    private int minPrice = Integer.MIN_VALUE;
    private int maxPrice = Integer.MAX_VALUE;
    private String filterArticleName = "";
    private String filterStoreName = "";
    

    public PricesDialog(Display display) {
        this.display = display;
        shell = new Shell(display);
        table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        prices=db.getAllPrices();
    }

    public void open() {
        shell.setText("Prices dialog");
        shell.setLayout(new GridLayout());

       
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 200;
        table.setLayoutData(data);

        String[] titles = { "Value", "Store", "Article" };
        for (String title : titles) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(title);
        }

        List<Price> prices = db.getAllPrices();
        for (Price price : prices) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, String.valueOf(price.getValue()));
            item.setText(1, price.getStore().getName());
            item.setText(2, price.getArticle().getName());
        }

        for (int i = 0; i < titles.length; i++) {
            table.getColumn(i).pack();
        }
        
        Button addPriceButton = new Button(shell, SWT.PUSH);
        addPriceButton.setText("Add Price");
        addPriceButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        addPriceButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		openAddPriceDialog();
        	}
        });
        
        Button deletePriceButton = new Button(shell, SWT.PUSH);
        deletePriceButton.setText("Delete Price");
        deletePriceButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        deletePriceButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		openDeletePriceDialog();
        	}
        });
        
        
        //filter
        Button filterButton = new Button(shell, SWT.PUSH);
        filterButton.setText("Filter");
        filterButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        filterButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		openFilterDialog();
        	}
        });

        shell.pack();
        shell.open();
    }
    
    public void showSortedPrices() {
        List<Price> sortedPrices = db.getSortedPrices();
    }
    
    private void openAddPriceDialog() {
		Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialogShell.setText("Add Price");
		dialogShell.setLayout(new GridLayout(2,false));
		
		Label articleLabel = new Label(dialogShell, SWT.NONE);
		articleLabel.setText("Select Article: ");
		Combo articleCombo = new Combo(dialogShell, SWT.DROP_DOWN | SWT.READ_ONLY);
		articleCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label storeLabel = new Label(dialogShell, SWT.NONE);
		storeLabel.setText("Select Store: ");
		Combo storeCombo = new Combo(dialogShell, SWT.DROP_DOWN | SWT.READ_ONLY);
		storeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label priceLabel = new Label(dialogShell, SWT.NONE);
		priceLabel.setText("Price value: ");
		Text priceText = new Text(dialogShell, SWT.BORDER);
		
		populateCombos(articleCombo, storeCombo);
		
		
		Button addButton = new Button(dialogShell, SWT.PUSH);
		addButton.setText("Add");
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedArticle = articleCombo.getText();
				String selectedStore = storeCombo.getText();
				int price = Integer.parseInt(priceText.getText());
				addPriceToDB(selectedArticle, selectedStore, price);
				dialogShell.close();
				refreshPricesTable();
			}
		});
		dialogShell.pack();
		dialogShell.open();
	}
	
	private void populateCombos(Combo articleCombo, Combo storeCombo) {
		//DatabaseOperations dbOps = new DatabaseOperations();
		List<Article> articles = db.getAllArticles();
		List<Store> stores = db.getAllStores();
		
		for(Article a : articles) {
			articleCombo.add(a.getName());
		}
		
		for(Store s : stores) {
			storeCombo.add(s.getName());
		}
	}
	
	private void addPriceToDB(String articleName, String storeName, int price)
	{
		//DatabaseOperations dbOps = new DatabaseOperations();
		db.addPrice(articleName, storeName, price);
	}
	
	//delete price
	private void openDeletePriceDialog() {
	    Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	    dialogShell.setText("Delete Price");
	    dialogShell.setLayout(new GridLayout(2, false));

	    Label articleLabel = new Label(dialogShell, SWT.NONE);
	    articleLabel.setText("Select Article: ");
	    Combo articleCombo = new Combo(dialogShell, SWT.DROP_DOWN | SWT.READ_ONLY);
	    articleCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

	    Label storeLabel = new Label(dialogShell, SWT.NONE);
	    storeLabel.setText("Select Store: ");
	    Combo storeCombo = new Combo(dialogShell, SWT.DROP_DOWN | SWT.READ_ONLY);
	    storeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

	    populateCombos(articleCombo, storeCombo);

	    Button deleteButton = new Button(dialogShell, SWT.PUSH);
	    deleteButton.setText("Delete");
	    deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
	    deleteButton.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	            String selectedArticle = articleCombo.getText();
	            String selectedStore = storeCombo.getText();
	            deletePriceFromDB(selectedArticle, selectedStore);
	            dialogShell.close();
	            refreshPricesTable(); // Utilizează variabila de instanță 'table' a clasei
	        }
	    });
	    dialogShell.pack();
	    dialogShell.open();
	}

		
		private void deletePriceFromDB(String articleName, String storeName)
		{
			//DatabaseOperations dbOps = new DatabaseOperations();
			db.deletePrice(articleName, storeName);
		}

		private void refreshPricesTable() {
		    if (table != null && !table.isDisposed()) {
		        table.removeAll();
		        List<Price> prices = db.getAllPrices();
		        for (Price price : prices) {
		            TableItem item = new TableItem(table, SWT.NONE);
		            item.setText(0, String.valueOf(price.getValue()));
		            item.setText(1, price.getStore().getName());
		            item.setText(2, price.getArticle().getName());
		        }
		        for (int i = 0; i < table.getColumnCount(); i++) {
		            table.getColumn(i).pack();
		        }
		    }
		}

		private void openFilterDialog() {
			Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		    dialogShell.setText("Filter");
		    dialogShell.setLayout(new GridLayout(2, false));
		    
		    Label minPriceLabel = new Label(dialogShell, SWT.NONE);
		    minPriceLabel.setText("Min Price: ");
		    Text minPriceText = new Text(dialogShell, SWT.BORDER);
		    minPriceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		    
		    Label maxPriceLabel = new Label(dialogShell, SWT.NONE);
		    maxPriceLabel.setText("Max Price: ");
		    Text maxPriceText = new Text(dialogShell, SWT.BORDER);
		    maxPriceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		    
		    Label storeLabel = new Label(dialogShell, SWT.NONE);
		    storeLabel.setText("Store Name: ");
		    Combo storeCombo = new Combo(dialogShell, SWT.DROP_DOWN | SWT.READ_ONLY);
		    storeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		    storeCombo.add("");
		    
		    
		    for(Price p : prices) {
		    	String storeName = p.getStore().getName();
		    	boolean storeNameExists = false;
		    	for(String existingStoreName : storeCombo.getItems()) {
		    		if(storeName.equals(existingStoreName)) {
		    			storeNameExists = true;
		    			break;
		    		}
		    	}
		    	if(!storeNameExists) {
		    		storeCombo.add(storeName);
		    		storeCombo.setData(storeName, p.getStore().getId());
		    	}
		    }
		    
		    Label articleLabel = new Label(dialogShell, SWT.NONE);
		    articleLabel.setText("Article Name: ");
		    Combo articleCombo = new Combo(dialogShell, SWT.DROP_DOWN | SWT.READ_ONLY);
		    articleCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		    articleCombo.add("");
		    
		    
		    for(Price p : prices) {
		    	String articleName = p.getArticle().getName();
		    	boolean articleNameExists = false;
		    	for(String existingArticleName : articleCombo.getItems()) {
		    		if(articleName.equals(existingArticleName)) {
		    			articleNameExists = true;
		    			break;
		    		}
		    	}
		    	if(!articleNameExists) {
		    		articleCombo.add(articleName);
		    		articleCombo.setData(articleName, p.getStore().getId());
		    	}
		    }
		    
		    
		    Button filterButton = new Button(dialogShell, SWT.PUSH);
		    filterButton.setText("Filter");
		    filterButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		    filterButton.addSelectionListener(new SelectionAdapter() {
		    	@Override
		    	public void widgetSelected(SelectionEvent e) {
		    		minPrice = Integer.parseInt(minPriceText.getText());
		    		maxPrice = Integer.parseInt(maxPriceText.getText());
		    		filterStoreName = storeCombo.getText();
		    		filterArticleName = articleCombo.getText();
		    		dialogShell.close();
		    		refreshFilteredPricesTable();
		    	}
		    });
		    dialogShell.pack();
		    dialogShell.open();
		    
		}
		
		private void refreshFilteredPricesTable() {
		    if (table != null && !table.isDisposed()) {
		        table.removeAll();
		        List<Price> filteredPrices = new ArrayList<>();
		        for (Price price : prices) {
		            int priceValue = price.getValue();
		            String storeName = price.getStore().getName();
		            String articleName = price.getArticle().getName();

		            if (priceValue >= minPrice && priceValue <= maxPrice &&
		                    (filterStoreName.isEmpty() || storeName.equals(filterStoreName)) &&
		                    (filterArticleName.isEmpty() || articleName.equals(filterArticleName))) {
		                filteredPrices.add(price);
		            }
		        }

		        for (Price price : filteredPrices) {
		            TableItem item = new TableItem(table, SWT.NONE);
		            item.setText(0, String.valueOf(price.getValue()));
		            item.setText(1, price.getStore().getName());
		            item.setText(2, price.getArticle().getName());
		        }

		        for (int i = 0; i < table.getColumnCount(); i++) {
		            table.getColumn(i).pack();
		        }
		    }
		}

}
