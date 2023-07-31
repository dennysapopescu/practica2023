package com.ssn.practica.work.BasicWarehouseManagement.main;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import com.ssn.practica.work.BasicWarehouseManagement.RemoteValidateBarcodeService;

public class RMIClientValidateBarcode {
	public static void main(String[] args) {
		try {
	        Registry registry = LocateRegistry.getRegistry();
			RemoteValidateBarcodeService barcodeValidationService = (RemoteValidateBarcodeService) registry.lookup("RemoteValidateBarcodeService");
			Scanner scanner = new Scanner(System.in);
			System.out.println("Provide the barcode: ");
			String barcode = scanner.nextLine();
			String result = barcodeValidationService.validateBarcodePalet(barcode);
			System.out.println("Validation result: " + result);
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
