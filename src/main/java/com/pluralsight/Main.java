package com.pluralsight;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean runWhileLoop=true;

        while (runWhileLoop) {
            System.out.println(); //Leaves a blank line in between
            System.out.println("Welcome to the Accounting Ledger App!");
            System.out.println("D. Add Deposit");
            System.out.println("P. Make Payment");
            System.out.println("L. Ledger");
            System.out.println("B. View Current Balance"); //allow user to see their current balance
            System.out.println("X. Exit");
            System.out.print("Choose an option: ");  /*asks user for input on the same line*/

            String choice = scanner.nextLine().trim().toUpperCase();  /*stores the user input as the value for variable choice*/

            switch (choice) {
                case "D" -> {
                    Ledger.addDeposit();
                }
                case "P" -> {
                    Ledger.makePayment();
                }
                case "L" -> {
                    Ledger.viewLedger();
                }
                case "X" -> {
                    System.out.println("Exiting application. Goodbye!");
                    runWhileLoop = false; //Exits while loop
                }
                case "B"->{
                    Ledger.viewBalance();
                }
                default ->
                        System.out.println("Invalid input. Please enter D, P, L, or X."); /*if anything other than D,P,L, or X is typed it returns this message to the user*/
            }

        }
        scanner.close(); //Close Scanner
    }
}