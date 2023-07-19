package com.ssn.practica.personal.swt;

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

import com.ssn.practica.personal.miniapp.Article;
import com.ssn.practica.personal.miniapp.DatabaseOperations;

public class ArticlesDialog {

    private Display display;
    private Shell shell;
    private DatabaseOperations db = new DatabaseOperations();
    private Table table;
    /*
    private List<Article> articles;
    private String filterArticleName = " ";
    private String filterStoreName = " ";*/

    public ArticlesDialog(Display display) {
        this.display = display;
        shell = new Shell(display);
        table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        //articles = db.getAllArticles();
    }

    public void open() {
        shell.setText("Articles dialog");
        shell.setLayout(new GridLayout());

        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 200;
        table.setLayoutData(data);

        String[] titles = { "Name" };
        for (String title : titles) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(title);
        }

        List<Article> articles = db.getAllArticles();
        for (Article a : articles) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, a.getName());
        }

        for (int i = 0; i < titles.length; i++) {
            table.getColumn(i).pack();
        }

        Button addArticleButton = new Button(shell, SWT.PUSH);
        addArticleButton.setText("Add Article");
        addArticleButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        addArticleButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openAddArticleDialog();
            }
        });

        Button deleteArticleButton = new Button(shell, SWT.PUSH);
        deleteArticleButton.setText("Delete Article");
        deleteArticleButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        deleteArticleButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openDeleteArticleDialog();
            }
        });
        /*
        Button filterArticleButton = new Button(shell, SWT.PUSH);
        filterArticleButton.setText("Filter Articles");
        filterArticleButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        filterArticleButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openFilterArticleDialog();
            }
        });*/

        shell.pack();
        shell.open();
    }

    private void openAddArticleDialog() {
        Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        dialogShell.setText("Add Article");
        dialogShell.setLayout(new GridLayout(2, false));

        Label nameLabel = new Label(dialogShell, SWT.NONE);
        nameLabel.setText("Article Name: ");
        Text nameText = new Text(dialogShell, SWT.BORDER);

        Button addButton = new Button(dialogShell, SWT.PUSH);
        addButton.setText("Add");
        addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String articleName = nameText.getText();
                addArticleToDB(articleName);
                dialogShell.close();
                refreshArticlesTable();
            }
        });
        dialogShell.pack();
        dialogShell.open();
    }

    private void addArticleToDB(String articleName) {
        db.addArticle(articleName);
    }

    private void openDeleteArticleDialog() {
        Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        dialogShell.setText("Delete Article");
        dialogShell.setLayout(new GridLayout(2, false));

        Label nameLabel = new Label(dialogShell, SWT.NONE);
        nameLabel.setText("Article Name: ");
        Text nameText = new Text(dialogShell, SWT.BORDER);

        Button deleteButton = new Button(dialogShell, SWT.PUSH);
        deleteButton.setText("Delete");
        deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        deleteButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String articleName = nameText.getText();
                deleteArticleFromDB(articleName);
                dialogShell.close();
                refreshArticlesTable();
            }
        });
        dialogShell.pack();
        dialogShell.open();
    }

    private void deleteArticleFromDB(String articleName) {
        db.deleteArticle(articleName);
    }

    private void refreshArticlesTable() {
        if (table != null && !table.isDisposed()) {
            table.removeAll();
            List<Article> articles = db.getAllArticles();
            for (Article a : articles) {
                TableItem item = new TableItem(table, SWT.NONE);
                item.setText(0, a.getName());
            }
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumn(i).pack();
            }
        }
    }

    /*
    private void openFilterArticleDialog() {
    	Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    	dialogShell.setText("Filter Articles");
    	dialogShell.setLayout(new GridLayout(2, false));
    	
    	Label articleLabel = new Label(dialogShell, SWT.NONE);
    	articleLabel.setText("Article Name: ");
    	Combo articleCombo = new Combo(dialogShell, SWT.DROP_DOWN | SWT.READ_ONLY);
    	articleCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    	articleCombo.add("");
    	
    	for(Article a : articles) {
    		articleCombo.add(a.getName());
    	}
    	
    	Button filterButton = new Button(dialogShell, SWT.PUSH);
    	filterButton.setText("Filter");
    	filterButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
    	filterButton.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			filterArticleName = articleCombo.getText();
    			dialogShell.close();
    			refreshFilteredArticlesTable();
    		}
    	});
    	dialogShell.pack();
    	dialogShell.open();	
    }
    
    
    private void refreshFilteredArticlesTable() {
    	if(table != null && !table.isDisposed()) {
    		table.removeAll();
    		List<Article> filteredArticles = new ArrayList<>();
    		for(Article a : articles) {
    			String articleName = a.getName();
    			if(filterArticleName.isEmpty() || articleName.equals(filterArticleName)){
    				filteredArticles.add(a);
    			}
    		}
    		
    		for(Article a : filteredArticles) {
    			TableItem item = new TableItem(table, SWT.NONE);
    			item.setText(0, a.getName());
    		}
    		
    		for(int i = 0; i<table.getColumnCount(); i++) {
    			table.getColumn(i).pack();
    		}
    	}
    }
    */
    
    
    
    
    
    
    
    
    
    
    
    
    
}
