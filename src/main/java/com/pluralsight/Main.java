package com.pluralsight;

import java.util.Scanner;
import static com.pluralsight.ColorText.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean runWhileLoop=true;

        while (runWhileLoop) {
            System.out.println(); //Leaves a blank line in between
            System.out.println(BLUE + "Welcome to the Accounting Ledger App!" + RESET);
            System.out.println(CYAN + "D. Add Deposit" + RESET);
            System.out.println(CYAN + "P. Make Payment" + RESET);
            System.out.println(CYAN + "L. Ledger" + RESET);
            System.out.println(CYAN + "B. View Current Balance" + RESET); //see current balance
            System.out.println(CYAN + "X. Exit" + RESET);  /*asks user for input on the same line*/
            System.out.println(GREEN+ "Choose an option: ");

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