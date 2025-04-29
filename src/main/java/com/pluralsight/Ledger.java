package com.pluralsight;

import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat; //Formats the date
import java.io.FileWriter;
import java.io.IOException;

public class Ledger {
    static Scanner scanner=new Scanner(System.in);

    public static void addDeposit() {
        System.out.println("You are Depositing!");

        // Get current date and time for the deposit entry
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        System.out.println("Enter description:");
        String description = scanner.nextLine();

        System.out.println("Enter vendor:");
        String vendor = scanner.nextLine();

        System.out.println("Enter amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); //a hidden new line was left so use this to clear up for the next input

        // Format the transaction string to match the required CSV format
        String transaction = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;

        // Write to the CSV file using try catch
        try (FileWriter writer = new FileWriter("transactions.csv", true))  //opens the transactions.cvs file to write, and true means it is in append mode, not overwrite meaning new entries add to the csv file. Try closes the FilterWriter when we are done.
        {
            writer.append(transaction);   //adds transaction string created into the csv file
            writer.append("\n");          //new line is placed after the transaction so that the next entry is on a new page
            System.out.println("Deposit recorded successfully!"); //Success message printed if transaction is added to the csv file correctly
        } catch (IOException e) {   //errors
            System.out.println("An error occurred while saving the deposit.");  //error message
            e.printStackTrace();  //prints where the error occurred (used to debug)
        }
    }

    public static void makePayment() {
        System.out.println("Make payment");
        // Logic to make a payment will go here
    }

    public static void viewLedger() {
        System.out.println("View Ledger  ");
        // Logic to view the ledger will go here
    }
}
