package com.ssn.practica.personal.swt;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.ssn.practica.personal.miniapp.DatabaseOperations;

public class SWTApplication {
	Display display = new Display ();
	Shell shell = new Shell (display);
	private DatabaseOperations dbOps = new DatabaseOperations();

	public static void main (String [] args) {
		SWTApplication swtApp = new SWTApplication();
		swtApp.run();
	}
	
	
		
	public void run() {
		
		shell.setText("SWT Application");
		
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		
		MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
		fileItem.setText ("Administration");
		
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		fileItem.setMenu (submenu);
		
		//adding all options
		MenuItem articlesItem = new MenuItem (submenu, SWT.PUSH);
		articlesItem.setText ("Articles");
		articlesItem.addListener (SWT.Selection, e -> {
			new ArticlesDialog(display).open();
			});
		
		MenuItem storesItem = new MenuItem(submenu, SWT.PUSH);
        storesItem.setText("Stores");
        storesItem.addListener(SWT.Selection, e -> {
            new StoresDialog(display).open();
        });
		
        MenuItem pricesItem = new MenuItem(submenu, SWT.PUSH);
        pricesItem.setText("Prices");
        pricesItem.addListener(SWT.Selection, e -> {
            new PricesDialog(display).open();
        });


        //add buttons
       // createButtons();
        
        
		//item.setAccelerator (SWT.MOD1 + 'A');
		shell.setSize (200, 200);
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();   
	}
	
	

	
	

	
	
}
