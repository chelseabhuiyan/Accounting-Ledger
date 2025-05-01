# Accounting-Ledger
Accounting-Ledger is a Java Command Line application designed to manage and track financial transactions. The application allows users to add deposits, record payments, view detailed ledgers, and generate custom financial reports. Built with file handling and object-oriented principles, it provides a simple yet powerful way to manage finances.


## Features 

### Home Screen
- `D) Add Deposit`: Prompts for deposit info and saves to the CSV file.
- `P) Make Payment`: Prompts for debit info and saves to the CSV file.
- `L) Ledger`: Opens the ledger screen to view transaction history.
- `X) Exit`: Closes the application.


### Ledger Screen
- `A) All`: Displays all transactions, newest first.
- `D) Deposits`: Shows only deposit entries (positive amounts).
- `P) Payments`: Shows only payment entries (negative amounts).
- `R) Reports`: Opens the reports submenu.
- `H) Home`: Returns to the home screen.


#### Reports Menu
- `1) Month To Date`: Shows transactions from the current month.
- `2) Previous Month`: Shows transactions from last month.
- `3) Year To Date`: Shows transactions from the current year.
- `4) Previous Year`: Shows transactions from the last calendar year.
- `5) Search by Vendor`: Filters transactions by vendor name.
- `0) Back`: Returns to the Ledger menu.


### All transactions in the application should be read from and saved to a transaction file named `transactions.csv`.  

Each transaction should be saved as a single line with the following format:date|time|description|vendor|amount
