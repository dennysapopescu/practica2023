package com.ssn.practica.personal.project;

public class Helper {

	// barcode Validity
	private boolean isValidBarcodeFormat(LoadUnit loadUnit1) {
		return loadUnit1.getBarcode().matches("^1\\d{7}$") || loadUnit1.getBarcode().matches("^0\\d{11}$");
	}
}
