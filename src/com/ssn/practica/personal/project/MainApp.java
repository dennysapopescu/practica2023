package com.ssn.practica.personal.project;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainApp {
	Display display = new Display ();
	Shell shell = new Shell (display);
	//private DatabaseOperations db = new DatabaseOperations();

	public static void main (String [] args) {
		MainApp startApp = new MainApp();
		startApp.run();
		
	}
	
public void run() {
		
		shell.setText("Main Application");
		
		//Menu bar = new Menu (shell, SWT.BAR);
		//shell.setMenuBar (bar);
		
		/*MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
		fileItem.setText ("Administration");
		
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		fileItem.setMenu (submenu);
		
		//adding all options
		MenuItem stockItemsItem = new MenuItem (submenu, SWT.PUSH);
		stockItemsItem.setText ("Stock Items");
		stockItemsItem.addListener (SWT.Selection, e -> {
			new StockItemsDialog(display).open();
			});*/
		

		shell.setSize (200, 200);
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();   
	
	Picking picking = new Picking();
	//PickingDialog pickingDialog = new PickingDialog(display, picking);
	//pickingDialog.open();
	display.dispose();
	
	}
	
	
}
