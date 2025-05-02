# Accounting-Ledger
Accounting-Ledger is a Java Command Line Interface (CLI) application that helps users track their financial activity through deposits and payments. It supports viewing all transactions in a ledger and generating detailed financial reports. Transactions are stored persistently in a transactions.csv file, using a pipe-delimited format.
This application is ideal for personal and businesses looking to manage finances simply and effectively using object-oriented programming and file I/O techniques.

## Features 

### Home Screen
- `D) Add Deposit`: Prompts for deposit info and saves to the transaction.csv file.
- `P) Make Payment`: Prompts for debit info and saves to the transaction.csv file.
- `L) Ledger`: Opens the ledger screen to view transaction history.
- `X) Exit`: Closes the application.


### Ledger Screen
- `A) All`: Displays all transactions, sorted with the most recent at the top.
- `D) Deposits`: Shows only deposit entries (transactions with positive amounts).
- `P) Payments`: Shows only payment entries (transactions with negative amounts).
- `R) Reports`: Opens the reports submenu to view summary data or filtered results.
- `H) Home`: Returns to the home screen.

After viewing any ledger option, the user is returned to the Ledger Menu to choose another option or go back.

#### Reports Menu
- `1) Month To Date`: Lists all transactions from the current calendar month.
- `2) Previous Month`: Lists all transactions from the previous calendar month.
- `3) Year To Date`: Lists all transactions from the current year.
- `4) Previous Year`: Lists all transactions from the last calendar year.
- `5) Search by Vendor`: Filters transactions by vendor name.
- `6) Custom Search`:Opens a submenu to search by custom criteria.
- `0) Back`: Returns to the Ledger menu.

After each report is viewed, the user returns to the Reports Menu for additional options.

###  Custom Search Menu
- `1) Search by Vendor`: Prompts for a vendor name and displays all matching transactions.
- `2) Search by Description`:Finds transactions with a matching or partial description.
- `3) Search by Date` : Prompts for a specific date (`YYYY-MM-DD`) and shows matching entries.
- `4) Search by Amount Range `:Prompts for minimum and maximum amounts and shows matching entries.
- `0) Back`: Returns to the Reports Menu.

After each search result is shown, the user returns to the Custom Search Menu to perform more searches or go back.

## All transactions in the application should be read from and saved to a transaction file named `transactions.csv`.  Each transaction should be saved as a single line with the following format:date|time|description|vendor|amount



## Technology Stack and Tools

### Languages and Frameworks
- **Java**: Core programming language used to build the application.
- **Maven**: Project management and build tool used to manage dependencies and organize the project structure.

### IDE and Version Control
- **IntelliJ IDEA Community Edition**: Primary development environment used for writing and testing code.
- **Git & GitHub**: Version control system used to track changes and collaborate.



