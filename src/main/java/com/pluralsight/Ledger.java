package com.pluralsight;

import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat; //Formats the date
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.util.Collections;


public class Ledger {
    static Scanner scanner=new Scanner(System.in);

    public static void addDeposit() {
        System.out.println("You are Depositing!");

        // Get current date and time for the deposit entry
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        //Get description
        System.out.println("Enter description:");
        String description = scanner.nextLine(); //description is saved as a string

        //Get vendor
        System.out.println("Enter vendor:");
        String vendor = scanner.nextLine();   //Vendor is saved as a string

        //Get amount
        System.out.println("Enter amount:");
        double amount = scanner.nextDouble(); //amount is saved as a double
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
        System.out.println("Now making an a payment");

        // Get current date and time for the deposit entry
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        //Get description
        System.out.println("Enter description:");
        String description = scanner.nextLine(); //description is saved as a string

        //Get vendor
        System.out.println("Enter vendor:");
        String vendor = scanner.nextLine();   //Vendor is saved as a string

        //Get amount
        System.out.println("Enter amount:");
        double amount = scanner.nextDouble(); //amount is saved as a double
        scanner.nextLine(); //a hidden new line was left so use this to clear up for the next input

        amount=-Math.abs(amount);//amount is negative so a - is placed in front of the value entered by the user

        //Format the transaction to match the required csv format
        String transaction = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;

        // Write to the CSV file
        try (FileWriter writer = new FileWriter("transactions.csv", true)) {
            writer.append(transaction); //add transaction string to the csv
            writer.append("\n");   //go to the next line to get ready for the next transaction
            System.out.println("Payment recorded successfully!");
        } catch (IOException e) {   //errors
            System.out.println("An error occurred while saving the payment.");
            e.printStackTrace();   //print where the error is
        }
    }


    public static void viewLedger() {
        System.out.println("View Ledger  ");

        File file = new File("transactions.csv"); //file object is created that points to transactions.csv(where all the data is stored)

        //If the file doesn't exit the if statement will return true and it will print out that no transactions were found
        if (!file.exists()) {
            System.out.println("There are NO transactions. Ledger is empty.");
            return;
        }

        List<String> transactions = new ArrayList<>(); //A list is created to store each line of transaction from the csv file

        //Open file for reading using BufferedReader. It is try block since it automatically closes after
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(line); // Add each line to the list as long as it isn't null
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the ledger.");
            e.printStackTrace();  //prints where the error happened in terminal
            return;
        }
        //If the transaction list is empty
        if (transactions.isEmpty()) {
            System.out.println("There are no transactions found.");
        } else {
            // Show most recent transactions first (reverse order)
            Collections.reverse(transactions);
            //Format to print out the ledger
            System.out.printf("%-12s %-10s %-25s %-15s %-10s\n",
                    "Date", "Time", "Description", "Vendor", "Amount");
            System.out.println("/n"); //space to separate it

        }
        for (String transaction : transactions) {
            String[] section = transaction.split("\\|");  //breaks up each line with the | symbol called a pipe delimiter
            if (section.length == 5) {     //There should be 5 parts since we have 5 different categories
                System.out.printf("%-12s %-10s %-25s %-15s $%-10s\n",
                        section[0], section[1], section[2], section[3], section[4]); //$ added in front of amount and also it is formated correctly to display
            }
        }



    }
}
