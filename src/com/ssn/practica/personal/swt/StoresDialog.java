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

import com.ssn.practica.personal.miniapp.DatabaseOperations;
import com.ssn.practica.personal.miniapp.Store;

public class StoresDialog {

    private Display display;
    private Shell shell;
    private DatabaseOperations db = new DatabaseOperations();
    private Table table;

    public StoresDialog(Display display) {
        this.display = display;
        shell = new Shell(display);
        table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    }

    public void open() {
        shell.setText("Stores dialog");
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

        List<Store> stores = db.getAllStores();
        for (Store s : stores) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, s.getName());
        }

        for (int i = 0; i < titles.length; i++) {
            table.getColumn(i).pack();
        }

        Button addStoreButton = new Button(shell, SWT.PUSH);
        addStoreButton.setText("Add Store");
        addStoreButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        addStoreButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openAddStoreDialog();
            }
        });

        Button deleteStoreButton = new Button(shell, SWT.PUSH);
        deleteStoreButton.setText("Delete Store");
        deleteStoreButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        deleteStoreButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openDeleteStoreDialog();
            }
        });

        shell.pack();
        shell.open();
    }

    private void openAddStoreDialog() {
        Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        dialogShell.setText("Add Store");
        dialogShell.setLayout(new GridLayout(2, false));

        Label nameLabel = new Label(dialogShell, SWT.NONE);
        nameLabel.setText("Store Name: ");
        Text nameText = new Text(dialogShell, SWT.BORDER);

        Button addButton = new Button(dialogShell, SWT.PUSH);
        addButton.setText("Add");
        addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String storeName = nameText.getText();
                addStoreToDB(storeName);
                dialogShell.close();
                refreshStoresTable();
            }
        });
        dialogShell.pack();
        dialogShell.open();
    }

    private void addStoreToDB(String storeName) {
        db.addStore(storeName);
    }

    private void openDeleteStoreDialog() {
        Shell dialogShell = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        dialogShell.setText("Delete Store");
        dialogShell.setLayout(new GridLayout(2, false));

        Label nameLabel = new Label(dialogShell, SWT.NONE);
        nameLabel.setText("Store Name: ");
        Text nameText = new Text(dialogShell, SWT.BORDER);

        Button deleteButton = new Button(dialogShell, SWT.PUSH);
        deleteButton.setText("Delete");
        deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        deleteButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String storeName = nameText.getText();
                deleteStoreFromDB(storeName);
                dialogShell.close();
                refreshStoresTable();
            }
        });
        dialogShell.pack();
        dialogShell.open();
    }

    private void deleteStoreFromDB(String storeName) {
        db.deleteStore(storeName);
    }

    private void refreshStoresTable() {
        if (table != null && !table.isDisposed()) {
            table.removeAll();
            List<Store> stores = db.getAllStores();
            for (Store s : stores) {
                TableItem item = new TableItem(table, SWT.NONE);
                item.setText(0, s.getName());
            }
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumn(i).pack();
            }
        }
    }
}
