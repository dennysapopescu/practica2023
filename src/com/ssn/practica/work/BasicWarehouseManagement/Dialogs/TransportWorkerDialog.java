package com.ssn.practica.work.BasicWarehouseManagement.Dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.ssn.practica.work.BasicWarehouseManagement.LoadUnit;

public class TransportWorkerDialog {

	private Shell shell;
	private Display display;
	private ShippingWorkerDialog shippingDialog;
	public List<LoadUnit> loadedUnits = new ArrayList<>();

	public TransportWorkerDialog() {
		super();
	}

	public TransportWorkerDialog(Display display, LoadUnit loadedUnit) {
		this.display = display;
		loadedUnits.add(loadedUnit);
		run();
	}

	public void run() {
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2, true));
		shell.setText("Shipping");
		shell.setSize(800, 550);

		// Zones :
		Composite infoZone = new Composite(shell, SWT.NONE);
		Composite todoZone = new Composite(shell, SWT.NONE);
		Composite actionZone = new Composite(shell, SWT.NONE);
		Composite someActions = new Composite(shell, SWT.NONE);

		infoZone.setBackground(new Color(218, 218, 218));
		todoZone.setBackground(new Color(218, 218, 218));
		actionZone.setBackground(new Color(218, 218, 218));
		someActions.setBackground(new Color(218, 218, 218));

		// Layout for each zone :
		infoZone.setLayout(new GridLayout(10, false));
		todoZone.setLayout(new GridLayout(10, false));
		actionZone.setLayout(new GridLayout(9, false));
		someActions.setLayout(new GridLayout(10, false));

		// Sizes of Zones :
		GridData infoZoneData = new GridData(SWT.FILL, SWT.FILL, true, true);
		infoZoneData.horizontalSpan = 2;
		infoZone.setLayoutData(infoZoneData);

		GridData todoZoneData = new GridData(SWT.FILL, SWT.FILL, true, false);
		todoZone.setLayoutData(todoZoneData);

		GridData actionZoneData = new GridData(SWT.FILL, SWT.FILL, true, false);
		actionZoneData.heightHint = 200;
		actionZone.setLayoutData(actionZoneData);

		GridData someActionsData = new GridData(SWT.FILL, SWT.FILL, true, false);
		someActionsData.heightHint = 40;
		someActionsData.horizontalSpan = 2;
		someActions.setLayoutData(someActionsData);

		// Zones content :
		InfoZone(infoZone);
		UserToDoZone(todoZone);
		UserActionZone(actionZone);
		SomeActions(someActions);

		// ====

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	// ====================================================

	public void InfoZone(Composite parent) { // Zona unde se afiseaza informatii

		GridData tableInfoData = new GridData(SWT.FILL, SWT.FILL, true, true);

		Table tableInfo = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tableInfo.setLinesVisible(true);
		tableInfo.setHeaderVisible(true);

		tableInfoData.horizontalSpan = 10;
		tableInfo.setLayoutData(tableInfoData);

		String[] titlesOrderOut = { "LU_barcode", "ART_barcode", "quantity", "description", "weight" };
		for (String title : titlesOrderOut) {
			TableColumn column = new TableColumn(tableInfo, SWT.NONE);
			column.setText(title);
		}

		for (int i = 0; i < loadedUnits.size(); i++) {
			LoadUnit unit = loadedUnits.get(i);

			TableItem item = new TableItem(tableInfo, SWT.NONE);
			item.setText(0, unit.getBarcode());
			item.setText(1, unit.getStockItems().getArticle().getBarcode());
			item.setText(2, unit.getStockItems().getQuantity() + "");
			item.setText(3, unit.getStockItems().getArticle().getDescription());
			item.setText(4, unit.getStockItems().getArticle().getWeight() + "");
		}

		for (int i = 0; i < titlesOrderOut.length; i++) {
			tableInfo.getColumn(i).pack();
		}

	}

	public void UserToDoZone(Composite parent) { // Zona unde este informat userul ce trebuie sa faca

		GridData todoInfoData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		Text todoInfo = new Text(parent, SWT.BORDER);
		todoInfo.setEnabled(false);

		todoInfoData.horizontalSpan = 10;
		todoInfoData.heightHint = 200;

		todoInfo.setText("To Do Information Zone :"); // facem logica local pentru fiecare dialog ?
														// sau facem flexibil sa primim din extern

		todoInfoData.verticalAlignment = SWT.CENTER;

		Font customFont = new Font(display, "Arial", 15, SWT.BOLD);
		todoInfo.setFont(customFont);

		todoInfo.setLayoutData(todoInfoData);

	}

	public void UserActionZone(Composite parent) { // Zona unde userul face actiuni (ex: scanare barcode...)

	}

	public void SomeActions(Composite parent) { // Zona de confirmare a actiunilor

		GridData buttonGridData = new GridData(SWT.FILL, SWT.FILL, false, true);
		buttonGridData.widthHint = 90;
		buttonGridData.heightHint = 30;

		Button actionButton1 = new Button(parent, SWT.PUSH);
		actionButton1.setText("confirm");
		actionButton1.setLayoutData(buttonGridData);
		actionButton1.setBackground(new Color(168, 218, 220));

		actionButton1.addListener(SWT.Selection, e -> {
			// dummy rmi method
		});

		Button actionButton2 = new Button(parent, SWT.PUSH);
		actionButton2.setText("back");
		actionButton2.setLayoutData(buttonGridData);
		actionButton2.setBackground(new Color(168, 218, 220));

		actionButton2.addListener(SWT.Selection, e -> {
			shell.setVisible(false);
			shippingDialog = new ShippingWorkerDialog(display);
			shell.dispose();
		});

	}
}
