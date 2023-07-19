package com.ssn.practica.personal.swt;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Snippet71 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Snippet 71");
	shell.pack ();
	shell.open ();
	Shell dialog = new Shell (shell, SWT.DIALOG_TRIM);
	Label label = new Label (dialog, SWT.NONE);
	label.setText ("Exit the application?");
	Button okButton = new Button (dialog, SWT.PUSH);
	okButton.setText ("&OK");
	Button cancelButton = new Button (dialog, SWT.PUSH);
	cancelButton.setText ("&Cancel");

	FormLayout form = new FormLayout ();
	form.marginWidth = form.marginHeight = 8;
	dialog.setLayout (form);
	FormData okData = new FormData ();
	okData.top = new FormAttachment (label, 8);
	okButton.setLayoutData (okData);
	FormData cancelData = new FormData ();
	cancelData.left = new FormAttachment (okButton, 8);
	cancelData.top = new FormAttachment (okButton, 0, SWT.TOP);
	cancelButton.setLayoutData (cancelData);

	dialog.setDefaultButton (okButton);
	dialog.pack ();
	dialog.open ();

	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}