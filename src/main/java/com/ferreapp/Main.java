package com.ferreapp;

import com.ferreapp.infrastructure.adapter.ui.EpsUI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EpsUI epsUI = new EpsUI();
        Scanner sc = new Scanner(System.in); 

        while (true) { 
            System.out.println("\n--- Menú de EPS ---");
            System.out.println("1. Buscar EPS por nombre exacto");
            System.out.println("2. Mostrar todas las EPS");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    epsUI.FindEpsByNameExact(); 
                    break;
                case 2:
                    epsUI.FindAllEps(); 
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    sc.close(); 
                    return; 
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}