package com.ssn.practica.work.BasicWarehouseManagement.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import com.ssn.practica.work.BasicWarehouseManagement.RemoteCreatePaletService;

public class RMIClientCreatePalet {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            RemoteCreatePaletService remoteCreatePaletService = (RemoteCreatePaletService) registry.lookup("RemoteCreatePaletService");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the barcode: ");
            String barcode = scanner.nextLine();
            System.out.print("Enter the article: ");
            String article = scanner.nextLine();
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            
            String result = remoteCreatePaletService.createPalet(barcode, article, quantity);
            System.out.println("Result: " + result);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
