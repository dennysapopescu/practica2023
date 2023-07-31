package com.ssn.practica.personal.project;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class StockItemsDialog {

	private Display display;
	private Shell shell;
	private DBOperations db = new DBOperations();
	private Table table;
	
	public StockItemsDialog(Display display) {
		super();
		this.display = display;
		shell = new Shell(display);
		table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
	}
	
	public void open() {
		shell.setText("Stock Items Dialog");
		shell.setLayout(new GridLayout());
	
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
        table.setLayoutData(data);

        String[] titles = { "article name", "quantity", "lot code", "expiration date" };
        for (String title : titles) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(title);
        }

        List<StockItem> stockItems = db.getAllStockItems();
        for (StockItem stockItem : stockItems) {
            TableItem item = new TableItem(table, SWT.NONE);
          //  item.setText(0, stockItem.getArticle().getName());
           // item.setText(1, String.valueOf(stockItem.getQuantity()));
            //item.setText(2, stockItem.getLot().getLotCode());
            //item.setText(3, stockItem.getLot().getExpirationDate().toString());
        }

        for (int i = 0; i < titles.length; i++) {
            table.getColumn(i).pack();
        }
        
        Button addStockItemButton = new Button(shell, SWT.PUSH);
        addStockItemButton.setText("Add Stock Item");
        addStockItemButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        addStockItemButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openAddStockItemDialog();
            }
        });

        shell.pack();
        shell.open();
	}
	
	 private void openAddStockItemDialog() {
	        Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	        dialogShell.setText("Add Stock Item");
	        dialogShell.setLayout(new GridLayout(2, false));

	        Label articleNameLabel = new Label(dialogShell, SWT.NONE);
	        articleNameLabel.setText("Article Name: ");
	        Text articleNameText = new Text(dialogShell, SWT.BORDER);

	        Label quantityLabel = new Label(dialogShell, SWT.NONE);
	        quantityLabel.setText("Quantity: ");
	        Text quantityText = new Text(dialogShell, SWT.BORDER);

	        Label lotCodeLabel = new Label(dialogShell, SWT.NONE);
	        lotCodeLabel.setText("Lot Code: ");
	        Text lotCodeText = new Text(dialogShell, SWT.BORDER);

	        Label expirationDateLabel = new Label(dialogShell, SWT.NONE);
	        expirationDateLabel.setText("Expiration Date (yyyy-MM-dd): ");
	        Text expirationDateText = new Text(dialogShell, SWT.BORDER);

	        Button addButton = new Button(dialogShell, SWT.PUSH);
	        addButton.setText("Add");
	        addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
	        addButton.addSelectionListener(new SelectionAdapter() {
	            @Override
	            public void widgetSelected(SelectionEvent e) {
	                String articleName = articleNameText.getText();
	                int quantity = Integer.parseInt(quantityText.getText());
	                String lotCode = lotCodeText.getText();
	                LocalDate expirationDate = LocalDate.parse(expirationDateText.getText());

	               // Article article = new Article(articleName);
	               // Lot lot = new Lot(article, lotCode, expirationDate);

	              //  db.addStockItem(article, quantity, lot);
	                dialogShell.close();
	              //  refreshStockItemsTable();
	            }
	        });
	        dialogShell.pack();
	        dialogShell.open();
	    }
	/*
	 private void refreshStockItemsTable() {
	        if (table != null && !table.isDisposed()) {
	            table.removeAll();
	            List<StockItem> stockItems = db.getAllStockItems();
	            for (StockItem stockItem : stockItems) {
	                TableItem item = new TableItem(table, SWT.NONE);
	                item.setText(0, stockItem.getArticle().getName());
	                item.setText(1, String.valueOf(stockItem.getQuantity()));
	                item.setText(2, stockItem.getLot().getLotCode());
	                item.setText(3, stockItem.getLot().getExpirationDate().toString());
	            }
	            for (int i = 0; i < table.getColumnCount(); i++) {
	                table.getColumn(i).pack();
	            }
	        }
	    }*/
	

	
}
