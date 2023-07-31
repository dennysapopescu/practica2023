package com.ssn.practica.personal.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PickingDialog {

    private Shell shell;
    private Text paletBarcodeText;
    private Text articleText;
    private Text quantityText;
    private Button confirmButton;

    // Sample data for demonstration
    private List<Palet> palets;

    public PickingDialog(Display display) {
        shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
        shell.setText("Picking Dialog");
        shell.setLayout(new GridLayout(2, false));


        // Palet Barcode
        Label paletBarcodeLabel = new Label(shell, SWT.NONE);
        paletBarcodeLabel.setText("Palet Barcode:");

        paletBarcodeText = new Text(shell, SWT.BORDER);
        paletBarcodeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Article
        Label articleLabel = new Label(shell, SWT.NONE);
        articleLabel.setText("Article:");

        articleText = new Text(shell, SWT.BORDER);
        articleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Quantity
        Label quantityLabel = new Label(shell, SWT.NONE);
        quantityLabel.setText("Quantity:");

        quantityText = new Text(shell, SWT.BORDER);
        quantityText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Confirm Button
        confirmButton = new Button(shell, SWT.PUSH);
        confirmButton.setText("Confirm");
        confirmButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
        confirmButton.setEnabled(false);

        // Event Listener for Confirm Button
        confirmButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Perform the picking process here
                String paletBarcode = paletBarcodeText.getText();
                String article = articleText.getText();
                int quantity = Integer.parseInt(quantityText.getText());

                performPicking(paletBarcode, article, quantity);

                // Close the dialog
                shell.close();
            }
        });

        // Event Listener for Palet Barcode Text
        paletBarcodeText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                // Enable the confirm button if the palet barcode is not empty
                confirmButton.setEnabled(!paletBarcodeText.getText().trim().isEmpty());
            }
        });

        shell.pack();
    }

    public void open() {
        shell.open();
        Display display = shell.getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    private void performPicking(String paletBarcode, String article, int quantity) {
        // Find the specified palet in the system using the paletBarcode
        Palet palet = findPaletByBarcode(paletBarcode);
        if (palet == null) {
            showErrorDialog("Palet Not Found", "The specified palet barcode was not found in the system.");
            return;
        }

        // Check if the specified article exists on the palet
        StockItem stockItem = palet.findStockItemByArticle(article);
        if (stockItem == null) {
            showErrorDialog("Article Not Found", "The specified article was not found on the palet.");
            return;
        }

        // Check if the quantity to be picked is available on the palet
        if (stockItem.getQuantity() < quantity) {
            showErrorDialog("Insufficient Quantity", "The requested quantity is not available on the palet.");
            return;
        }

        // Perform the picking process and update the stock
        stockItem.setQuantity(stockItem.getQuantity() - quantity);

        // Display a success message to the user
        showMessageDialog("Picking Success", "Picking process completed successfully!");
    }

    private Palet findPaletByBarcode(String paletBarcode) {
        // Your implementation to search for the palet in the system
        // Return null if the palet is not found, or return the found palet
        for (Palet palet : palets) {
            if (palet.getBarcode().equals(paletBarcode)) {
                return palet;
            }
        }
        return null;
    }

    private void showMessageDialog(String title, String message) {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
        messageBox.setText(title);
        messageBox.setMessage(message);
        messageBox.open();
    }

    private void showErrorDialog(String title, String message) {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
        messageBox.setText(title);
        messageBox.setMessage(message);
        messageBox.open();
    }

    public static void main(String[] args) {
        Display display = new Display();
        PickingDialog dialog = new PickingDialog(display);
        dialog.open();
        display.dispose();
    }
}

class Palet {
    private String barcode;
    private List<StockItem> stockItems;

    public Palet(String barcode) {
        this.barcode = barcode;
        this.stockItems = new ArrayList<>();
    }

    public String getBarcode() {
        return barcode;
    }

    public void addStockItem(StockItem stockItem) {
        stockItems.add(stockItem);
    }

    public StockItem findStockItemByArticle(String article) {
        for (StockItem stockItem : stockItems) {
            if (stockItem.getArticle().equals(article)) {
                return stockItem;
            }
        }
        return null;
    }
}


	

	

