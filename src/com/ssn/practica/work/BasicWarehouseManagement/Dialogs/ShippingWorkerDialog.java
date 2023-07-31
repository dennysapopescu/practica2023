package com.ssn.practica.work.BasicWarehouseManagement.Dialogs;

import java.time.LocalDateTime;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.ssn.practica.work.BasicWarehouseManagement.Article;
import com.ssn.practica.work.BasicWarehouseManagement.LoadUnit;
import com.ssn.practica.work.BasicWarehouseManagement.Location;
import com.ssn.practica.work.BasicWarehouseManagement.Location.Tip;
import com.ssn.practica.work.BasicWarehouseManagement.Lot;
import com.ssn.practica.work.BasicWarehouseManagement.OrderOut;
import com.ssn.practica.work.BasicWarehouseManagement.Reservation;
import com.ssn.practica.work.BasicWarehouseManagement.StockItem;
import com.ssn.practica.work.BasicWarehouseManagement.main.Status;

public class ShippingWorkerDialog {

	private Shell shell;
	private Display display;
	private TransportWorkerDialog transportDialog;

	public static void main(String[] args) {
		ShippingWorkerDialog app = new ShippingWorkerDialog();
		app.run();
	}

	public ShippingWorkerDialog() {
		super();
		this.display = new Display();
	}

	public ShippingWorkerDialog(Display display) {
		this.display = display;
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

	// ---------- Temp Test ---------------
	public List<LoadUnit> loadUnits = new ArrayList<>();
	public List<StockItem> stockItems = new ArrayList<>();
	public List<Article> articles = new ArrayList<>();
	public List<Lot> lots = new ArrayList<>();

	public List<OrderOut> ordersOut = new ArrayList<>();
	public List<Reservation> reservations = new ArrayList<>();

	public List<Location> locations = new ArrayList<>();
	// -----------------------------------------

	public void InfoZone(Composite parent) { // Zona unde se afiseaza informatii

		// ---------- Temp Test ---------------
		Article a1 = new Article("111", "mere", 20f);
		Article a2 = new Article("222", "pere", 23f);
		Article a3 = new Article("333", "cirese", 26f);
		articles.add(a1);
		articles.add(a2);
		articles.add(a3);

		StockItem s1 = new StockItem(a1, 15, null);
		StockItem s2 = new StockItem(a2, 20, null);
		StockItem s3 = new StockItem(a3, 25, null);
		stockItems.add(s1);
		stockItems.add(s2);
		stockItems.add(s3);

		Location l1 = new Location("OUT", 1, 001, 01, 01, Tip.STORAGE);
		Location l2 = new Location("IN", 1, 002, 01, 01, Tip.STORAGE);
		Location l3 = new Location("OUT", 1, 003, 01, 01, Tip.TRANSITION);
		locations.add(l1);
		locations.add(l2);
		locations.add(l3);

		LoadUnit lu1 = new LoadUnit("lu111", "cutie");
		lu1.connectStockItem(s1);
		lu1.connectLocation(l1);

		LoadUnit lu2 = new LoadUnit("lu222", "cutie");
		lu2.connectStockItem(s2);
		lu2.connectLocation(l2);

		LoadUnit lu3 = new LoadUnit("lu333", "cutie");
		lu3.connectStockItem(s3);
		lu3.connectLocation(l3);

		loadUnits.add(lu1);
		loadUnits.add(lu2);
		loadUnits.add(lu3);

		OrderOut o1 = new OrderOut("mere", 15, Status.NEW);
		OrderOut o2 = new OrderOut("pere", 20, Status.PICKED);
		OrderOut o3 = new OrderOut("cirese", 30, Status.NEW);
		ordersOut.add(o1);
		ordersOut.add(o2);
		ordersOut.add(o3);

		Reservation r1 = new Reservation(10, LocalDateTime.now());
		r1.connectStockItem(s1);
		r1.connectOrderOut(o1);

		Reservation r2 = new Reservation(15, LocalDateTime.now());
		r1.connectStockItem(s2);
		r1.connectOrderOut(o2);

		Reservation r3 = new Reservation(20, LocalDateTime.now());
		r1.connectStockItem(s3);
		r1.connectOrderOut(o3);

		reservations.add(r1);
		reservations.add(r2);
		reservations.add(r3);

		// -----------------------------------------

		// ==========

		GridData tableInfoData = new GridData(SWT.FILL, SWT.FILL, true, true);
		Table tableLoadUnit = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tableLoadUnit.setLinesVisible(true);
		tableLoadUnit.setHeaderVisible(true);

		tableInfoData.horizontalSpan = 10;
		tableLoadUnit.setLayoutData(tableInfoData);

		String[] titlesLoadUnit = { "LU_barcode", "ART_barcode", "quantity" };
		for (String title : titlesLoadUnit) {
			TableColumn column = new TableColumn(tableLoadUnit, SWT.NONE);
			column.setText(title);
		}

		for (int i = 0; i < loadUnits.size(); i++) {
			LoadUnit unit = loadUnits.get(i);

			TableItem item = new TableItem(tableLoadUnit, SWT.NONE);
			item.setText(0, unit.getBarcode());
			item.setText(1, unit.getStockItems().getArticle().getBarcode());
			item.setText(2, unit.getStockItems().getArticle().getDescription());
			item.setText(3, unit.getStockItems().getQuantity() + "");
		}

		for (int i = 0; i < titlesLoadUnit.length; i++) {
			tableLoadUnit.getColumn(i).pack();
		}

	}

	public void UserToDoZone(Composite parent) { // Zona unde este informat userul ce trebuie sa faca

		GridData todoInfoData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		Text todoInfo = new Text(parent, SWT.BORDER | SWT.WRAP);
		todoInfo.setEnabled(false);

		todoInfoData.horizontalSpan = 10;
		todoInfoData.heightHint = 200;

		String toDoMessage = "To Do Information Zone :\n";
		todoInfo.setText(toDoMessage + "scan barcode");
		todoInfoData.verticalAlignment = SWT.CENTER;

		Font customFont = new Font(display, "Arial", 13, SWT.BOLD);
		todoInfo.setFont(customFont);

		todoInfo.setLayoutData(todoInfoData);

	}

	private Text textField;

	public void UserActionZone(Composite parent) { // Zona unde userul face actiuni (ex: scanare barcode...)
		textField = new Text(parent, SWT.BORDER);
		textField.setMessage("some text");
		GridData textFieldData = new GridData(SWT.FILL, SWT.FILL, true, false);
		textFieldData.horizontalSpan = 9;
		textFieldData.heightHint = 30;
		textField.setLayoutData(textFieldData);
	}

	public void SomeActions(Composite parent) { // Zona de confirmare a actiunilor

		GridData buttonGridData = new GridData(SWT.FILL, SWT.FILL, false, true);
		buttonGridData.widthHint = 90;
		buttonGridData.heightHint = 30;

		Button actionButton1 = new Button(parent, SWT.PUSH);
		actionButton1.setText("scan");
		actionButton1.setLayoutData(buttonGridData);
		actionButton1.setBackground(new Color(168, 218, 220));

		Label errorLabel = new Label(parent, SWT.NONE);
		GridData errorLabelLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		errorLabel.setLayoutData(errorLabelLayoutData);

		actionButton1.addListener(SWT.Selection, e -> {
			try {
				LoadUnit containsLoadUnit = loadUnits.stream()
						.filter(loadUnit -> textField.getText().equals(loadUnit.getBarcode())).findFirst().orElse(null);

				if (containsLoadUnit != null && validateScan(containsLoadUnit)) {
					errorLabel.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
					errorLabel.setText("OK");
					shell.setVisible(false);
					transportDialog = new TransportWorkerDialog(display, containsLoadUnit);
					shell.dispose();
				} else {
					errorLabel.setForeground(display.getSystemColor(SWT.COLOR_RED));
					errorLabel.setText("inexistent");
				}
			} catch (Exception er) {
				System.out.println("ERROR: ");
			}
		});
	}

	private boolean validateScan(LoadUnit containsLoadUnit) {
		try {
			Reservation r1 = reservations.stream()
					.filter(e -> e.getStockItemID().getId().equals(containsLoadUnit.getStockItems().getId()))
					.filter(e -> e.getOrderOutID().getStatus().equals(Status.PICKED)).findAny().orElse(null);

			if (r1 != null && containsLoadUnit.getLocation().getId().equals("OUT"))
				return true;
		} catch (Exception er) {
			System.out.println("value_id : " + containsLoadUnit.getStockItems().getId());
		}
		return false;
	}

}
