package com.ssn.practica.personal.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

//lista o fac intr-o clasa "Constante" 

public class AdminApp {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Snippet 65");
		Label label = new Label(shell, SWT.WRAP);
		label.setText("Administrative table with all tables.");
		List list = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		list.setItems("Article", "LoadUnit", "Location", "Lot", "OrderIn", "OrderOut", "Reservation", "StockItem",
				"TransportOrders");
		Button button1 = new Button(shell, SWT.PUSH);
		button1.setText("OK");
		Button button2 = new Button(shell, SWT.PUSH);
		button2.setText("Cancel");

		final int insetX = 4, insetY = 4;
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = insetX;
		formLayout.marginHeight = insetY;
		shell.setLayout(formLayout);

		Point size = label.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		final FormData labelData = new FormData(size.x, SWT.DEFAULT);
		labelData.left = new FormAttachment(0, 0);
		labelData.right = new FormAttachment(100, 0);
		label.setLayoutData(labelData);
		shell.addListener(SWT.Resize, e -> {
			Rectangle rect = shell.getClientArea();
			labelData.width = rect.width - insetX * 2;
			shell.layout();
		});

		FormData button2Data = new FormData();
		button2Data.right = new FormAttachment(100, -insetX);
		button2Data.bottom = new FormAttachment(100, 0);
		button2.setLayoutData(button2Data);

		FormData button1Data = new FormData();
		button1Data.right = new FormAttachment(button2, -insetX);
		button1Data.bottom = new FormAttachment(100, 0);
		button1.setLayoutData(button1Data);

		FormData listData = new FormData();
		listData.left = new FormAttachment(0, 0);
		listData.right = new FormAttachment(100, 0);
		listData.top = new FormAttachment(label, insetY);
		listData.bottom = new FormAttachment(button2, -insetY);
		list.setLayoutData(listData);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}