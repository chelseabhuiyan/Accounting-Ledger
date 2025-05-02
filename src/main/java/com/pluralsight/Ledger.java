package com.pluralsight;

import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat; // Formats the date
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Calendar;

/**
  The Ledger class handles financial transactions by reading from and writing to a CSV file.
  It displays by deposits, payments, and filters by different transaction reports.
 */
public class Ledger {
    static Scanner scanner = new Scanner(System.in);

    public static void addDeposit() {
        System.out.println("Add Deposit!");

        // Get current date and time for the deposit entry
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        // Get description
        System.out.println("Enter description:");
        String description = scanner.nextLine(); // description is saved as a string

        // Get vendor
        System.out.println("Enter vendor:");
        String vendor = scanner.nextLine();   // Vendor is saved as a string

        // Get amount
        double amount = 0.0;
        while (true) {
            System.out.println("Enter amount:");
            try {
                amount = Double.parseDouble(scanner.nextLine());
                break; // exit loop if successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        scanner.nextLine(); // a hidden new line was left so use this to clear up for the next input

        // Format the transaction string to match the required CSV format
        String transaction = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;

        // Write to the CSV file using try catch
        try (FileWriter writer = new FileWriter("transactions.csv", true))  // opens the transactions.cvs file to write, and true means it is in append mode, not overwrite meaning new entries add to the csv file. Try closes the FilterWriter when we are done.
        {
            writer.append(transaction);   // adds transaction string created into the csv file
            writer.append("\n");          // new line is placed after the transaction so that the next entry is on a new page
            System.out.println("Deposit recorded successfully!"); // Success message printed if transaction is added to the csv file correctly
        } catch (IOException e) {   // errors
            System.out.println("An error occurred while saving the deposit.");  // error message
            e.printStackTrace();  // prints where the error occurred (used to debug)
        }
    }

    public static void makePayment() {
        System.out.println("Now making a payment");

        // Get current date and time for the deposit entry
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        // Get description
        System.out.println("Enter description:");
        String description = scanner.nextLine(); // description is saved as a string

        // Get vendor
        System.out.println("Enter vendor:");
        String vendor = scanner.nextLine();   // Vendor is saved as a string

        // Get amount
        double amount = 0.0;
        while (true) {
            System.out.println("Enter amount:");
            try {
                amount = -Math.abs(Double.parseDouble(scanner.nextLine())); // ensures amount is negative
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Format the transaction to match the required csv format
        String transaction = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;

        // Write to the CSV file
        try (FileWriter writer = new FileWriter("transactions.csv", true)) {
            writer.append(transaction); // add transaction string to the csv
            writer.append("\n");   // go to the next line to get ready for the next transaction
            System.out.println("Payment recorded successfully!");
        } catch (IOException e) {   // errors
            System.out.println("An error occurred while saving the payment.");
            e.printStackTrace();   // print where the error is
        }
    }

    public static void viewLedger() {
       while (true) {//Stays in the ledger menu instead of going to the home screen each time
           System.out.println("\n"); // space in between
           System.out.println("Ledger Menu Screen ");


           System.out.println("A) All Entries");
           System.out.println("D) Deposits Only");
           System.out.println("P) Payments Only");
           System.out.println("R) Reports");
           System.out.println("H) Home");

           System.out.println("\n"); // space in between
           System.out.print("Select an option: ");

           String filter = scanner.nextLine().toUpperCase();

           // Switch options
           switch (filter) {
               case "A":
                   displayTransactions("A");
                   break;
               case "D":
                   displayTransactions("D");
                   break;
               case "P":
                   displayTransactions("P");
                   break;
               case "R":
                   reportsView();
                   break;
               case "H":
                   return; // Exit to Home
               default:
                   System.out.println("Invalid option. Try again.");
           }
           System.out.println(); // extra space between iterations
       }
    }

    public static void displayTransactions(String filter) {
        File file = new File("transactions.csv"); // file object is created that points to transactions.csv (where all the data is stored)

        // If the file doesn't exist, the if statement will return true and it will print out that no transactions were found
        if (!file.exists()) {
            System.out.println("There are NO transactions. Ledger is empty.");
            return;
        }

        // New List is created to store the transactions
        List<String> transactions = new ArrayList<>(); // A list is created to store each line of transaction from the csv file

        // Open file for reading using BufferedReader. It is try block since it automatically closes after
        // BufferedReader reads line by line from the transaction file and adds it to the transaction list
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(line); // Add each line to the list as long as it isn't null
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the ledger.");
            e.printStackTrace();  // prints where the error happened in terminal
            return;
        }

        // If the transaction list is empty
        if (transactions.isEmpty()) {
            System.out.println("There are no transactions found.");
        }
        else {
            // Show most recent transactions first (reverse order)
            Collections.reverse(transactions);
            // Prints the header (column titles for the ledger)
            System.out.printf("%-12s %-10s %-25s %-15s %-10s\n",
                    "Date", "Time", "Description", "Vendor", "Amount");

        }

        // Apply the filter logic based on the user-selected filter
        for (String transaction : transactions) {
            String[] section = transaction.split("\\|"); // break the transaction string into an array by the pipe delimiter

            // Ensure there are 5 sections (Date, Time, Description, Vendor, Amount)
            if (section.length == 5) {
                double amount = Double.parseDouble(section[4]);

                boolean show = switch (filter) {
                    case "A" -> true;           // Show all transactions
                    case "D" -> amount >= 0;    // Show is true if positive or zero amounts
                    case "P" -> amount < 0;     // Show is true if amount is negative
                    default -> false;           // Default to false if the filter is invalid
                };

                // If the 'show' variable is true, print the transaction
                if (show) {
                    System.out.printf("%-12s %-10s %-25s %-15s $%-10.2f\n",
                            section[0], section[1], section[2], section[3], amount);
                }
            }
        }
    }
    public static void reportsView() {
        while (true) {
            System.out.println("\n"); // space in between
            System.out.println("Reports Screen ");

            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            System.out.println("\n"); // space in between
            System.out.print("Select a report option: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "1":
                    displayMonthToDate();
                    break;
                case "2":
                    displayPreviousMonth();
                    break;
                case "3":
                    displayYearToDate();
                    break;
                case "4":
                    displayPreviousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "6":
                    customSearch();
                    break;
                case "0":
                    return; // Go back to the previous report page
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    private static void displayMonthToDate() {
        Date currentDate = new Date(); // Create a Date object for today's date
        Calendar calendar = Calendar.getInstance(); // Initialize calendar with current date/time
        calendar.setTime(currentDate); // Set the calendar time to the current date

        int currentMonth = calendar.get(Calendar.MONTH); // Get current month (0-11)
        int currentYear = calendar.get(Calendar.YEAR);   // Get current year (e.g., 2025)

        // Get transactions matching this month and year
        List<String> transactions = getTransactionsForPeriod(currentYear, currentMonth, 0);
        printTransactions(transactions); // Print them
    }
    //Show last Month
    private static void displayPreviousMonth() {
        Calendar calendar = Calendar.getInstance(); //gets the current date
        calendar.add(Calendar.MONTH, -1); // Go back one month
        int month = calendar.get(Calendar.MONTH); // fetch the month (0-based, so January is 0).
        int year = calendar.get(Calendar.YEAR);// fetches the year

        List<String> transactions = getTransactionsForPeriod(year, month, 0); //goes to helper method to filter out the transactions
        printTransactions(transactions);  //prints it
    }

    //Show transactions from Jan 1 to current date
    private static void displayYearToDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR); //gets the current year using the calendar class.
        List<String> transactions = getTransactionsForPeriod(year, -1, 0); // run the helper function to filters it for transaction the specific year, -1 = all month, 0 means any day
        printTransactions(transactions); //prints it
    }

    //Show transactions for last year
    private static void displayPreviousYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR) - 1;  //gets current year and subtracts one from it
        List<String> transactions = getTransactionsForPeriod(year, -1, 0); // filters it using the helper method -1 = any month
        printTransactions(transactions);
    }

    //Helper method to filter
    private static List<String> getTransactionsForPeriod(int year, int month, int day) {
        File file = new File("transactions.csv");  //points to the file where the transaction is stored
        List<String> filtered = new ArrayList<>(); //creates a list to store the filtered transactions

        if (!file.exists())
        {return filtered; } //if file didn't exist return empty list


        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {   //read each line in the csv
                String[] parts = line.split("\\|"); //split the line into an array with the "|" delimiter
                if (parts.length == 5) { //makes sure the line was correct and had 5 parts
                    String[] dateParts = parts[0].split("-"); // splits the date into parts  yyyy-MM-dd
                    int entryYear = Integer.parseInt(dateParts[0]); //extracts the year
                    int entryMonth = Integer.parseInt(dateParts[1]) - 1; // extracts the month. Calendar class months are 0-based
                    int entryDay = Integer.parseInt(dateParts[2]); //extracts the day

                    //checks to see if the transaction matches the year, month, day
                    boolean matches = entryYear == year &&
                            (month == -1 || entryMonth == month) &&
                            (day == 0 || entryDay == day);

                    if (matches) filtered.add(line); // if match is found with that line add the whole transaction line to the list
                }
            }
        } catch (IOException e) { //error
            System.out.println("Error reading transactions.");
            e.printStackTrace(); //prints in terminal the error
        }

        return filtered;  //returns the filtered version
    }

    //Helper method to print the transaction
    private static void printTransactions(List<String> transactions) {
        if (transactions.isEmpty()) { //if no transactions are found after filtering
            System.out.println("No transactions found.");
            return; //exit
        }

        //prints the header with the column names
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n",
                "Date", "Time", "Description", "Vendor", "Amount");
        Collections.reverse(transactions); //reverse list to show recent transactions first

        //iterate over each transaction that were in the filtered list
        for (String t : transactions) {
            String[] p = t.split("\\|"); //split each by the "|"


            if (p.length == 5) { //make sure there are five parts
                double amount = Double.parseDouble(p[4]); //convert the amount to double

                // Print each transaction in a formatted version.
                System.out.printf("%-12s %-10s %-25s %-15s $%-10.2f\n",
                        p[0], p[1], p[2], p[3], amount);
            }
        }
    }

    //Search with vendor name
    private static void searchByVendor() {
        System.out.print("Enter vendor name to search: ");
        String vendorSearch = scanner.nextLine().trim().toLowerCase(); // Get user input, trim spaces, and convert to lowercase for case-insensitive search

        File file = new File("transactions.csv");
        if (!file.exists()) {
            System.out.println("No transactions found.");
            return;
        }

        List<String> matching = new ArrayList<>();  //List to store matching transactions

        //read through the csv file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line; //take in a line
            while ((line = reader.readLine()) != null) { //as long as the line not empty
                String[] parts = line.split("\\|");  //break into parts by the "|"
                if (parts.length == 5 && parts[3].toLowerCase().contains(vendorSearch)) { //the thrid part is the vendor make that lowercase and compare to the user input
                    matching.add(line); // if its matching add the line
                }
            }
        } catch (IOException e) { //error handling
            System.out.println("Error reading transactions.");
            e.printStackTrace();
        }

        printTransactions(matching); //print the transactions using the helper method
    }

    //Custom search
    private static void customSearch() {
        System.out.println("CUSTOM SEARCH");
        System.out.println("(Press Enter to skip any filter)");

        // Prompt user for optional filters
        System.out.print("Start Date (yyyy-MM-dd): ");
        String startDateInput = scanner.nextLine().trim();  // Get start date filter

        System.out.print("End Date (yyyy-MM-dd): ");
        String endDateInput = scanner.nextLine().trim();    // Get end date filter

        System.out.print("Description contains: ");
        String descriptionFilter = scanner.nextLine().trim().toLowerCase(); // Get description keyword, lowercase for case-insensitive search

        System.out.print("Vendor contains: ");
        String vendorFilter = scanner.nextLine().trim().toLowerCase(); // Get vendor keyword

        System.out.print("Amount equals (exact match): ");
        String amountInput = scanner.nextLine().trim();  // Get amount as string

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Format used for parsing dates

        // Variables to hold parsed filter values
        Date startDate = null;
        Date endDate = null;
        Double amount = null;

        try {
            // Parse dates and amount only if input was provided
            if (!startDateInput.isEmpty()) {
                startDate = formatter.parse(startDateInput);  // Convert string to Date
            }
            if (!endDateInput.isEmpty()) {
                endDate = formatter.parse(endDateInput);      // Convert string to Date
            }
            if (!amountInput.isEmpty()) {
                amount = Double.parseDouble(amountInput);     // Convert amount to double
            }
        } catch (Exception e) {
            // If any parsing fails, show error and stop search
            System.out.println("Invalid input. Please use correct formats.");
            return;
        }

        List<String> matches = new ArrayList<>(); // List to store matching transactions
        File file = new File("transactions.csv"); // File where transactions are stored

        // Check if file exists before reading
        if (!file.exists()) {
            System.out.println("No transactions found.");
            return;
        }

        // Read transactions from the file line-by-line
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");  // Split line into components
                if (parts.length == 5) {
                    // Parse date and amount from current transaction
                    Date transactionDate = formatter.parse(parts[0]);
                    String description = parts[2].toLowerCase(); // Transaction description
                    String vendor = parts[3].toLowerCase();      // Vendor name
                    double transactionAmount = Double.parseDouble(parts[4]); // Amount

                    // Assume the transaction matches unless a filter fails
                    boolean matchesFilter = true;

                    // Apply filters one-by-one if user provided them
                    if (startDate != null && transactionDate.before(startDate)) {
                        matchesFilter = false;  // Skip if before start date
                    }
                    if (endDate != null && transactionDate.after(endDate)) {
                        matchesFilter = false;  // Skip if after end date
                    }
                    if (!descriptionFilter.isEmpty() && !description.contains(descriptionFilter)) {
                        matchesFilter = false;  // Skip if description doesn’t contain keyword
                    }
                    if (!vendorFilter.isEmpty() && !vendor.contains(vendorFilter)) {
                        matchesFilter = false;  // Skip if vendor doesn’t match
                    }
                    if (amount != null && transactionAmount != amount) {
                        matchesFilter = false;  // Skip if amount doesn’t match exactly
                    }

                    // If transaction passed all filters, add to result list
                    if (matchesFilter) {
                        matches.add(line);
                    }
                }
            }
        } catch (Exception e) {
            // Show error if something goes wrong while reading or parsing
            System.out.println("Error reading or parsing transactions.");
            e.printStackTrace();
            return;
        }

        // Display all matching transactions using existing helper
        printTransactions(matches);
    }

}
