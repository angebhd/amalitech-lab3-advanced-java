# Advanced Java Lab - Bank Account Management

A clean, well-tested bank account management CLI application built with Java and Maven.

**By Ange Buhendwa**

## Overview
A console-based bank account management system that allows users to create accounts, manage transactions, and view account details. The application supports different customer types (Regular and Premium) and account types (Savings and Checking) with specific features and benefits. It also demonstrates advanced concepts like concurrency handling and file-based persistence.

## Features
- **Account Management**: Create, view, and list bank accounts.
- **Customer Types**:
  - **Regular Customer**: Standard banking features.
  - **Premium Customer**: Waived monthly fees on checking accounts.
- **Account Types**:
  - **Savings Account**: Earns 3.5% interest, requires $500 minimum balance.
  - **Checking Account**: $1,000 overdraft limit, $10 monthly fee (waived for Premium customers).
- **Transaction Processing**: Robust deposit and withdrawal operations.
- **Transaction History**: View complete transaction records by account.
- **Data Persistence**: Save and load application state (Customers, Accounts, Transactions) to text files.
- **Concurrency**: Simulation of concurrent transactions to demonstrate thread safety.
- **Code Quality**: SOLID principles, custom exceptions, and comprehensive unit testing.

## Project Structure

```
├── data/                   # Data storage for persistence (generated on run)
├── docs/
│   └── git-workflow.md     # Git workflow documentation
├── src/
│   ├── main/java/main/
│   │   ├── models/         # Core domain entities (Account, Customer, Transaction)
│   │   │   └── exceptions/ # Custom exceptions
│   │   ├── service/        # Business logic & services
│   │   │   ├── AccountManager.java         # Manages account collection
│   │   │   ├── TransactionManager.java     # Manages transaction history
│   │   │   ├── FilePersistenceService.java # File I/O for saving/loading data
│   │   │   └── StatementGenerator.java     # Generates account statements
│   │   ├── utils/          # Utility helper classes
│   │   │   ├── ValidationUtils.java        # Input validation
│   │   │   └── ConcurrencyUtils.java       # Threading support
│   │   └── Main.java       # Application entry point & Menu loop
│   └── test/java/          # JUnit 5 test cases
├── pom.xml                 # Maven project configuration
└── README.md
```

## Prerequisites
Before running this project, ensure you have the following installed:
- **Java JDK 21** or higher
- **Maven 3.6+**

## Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/angebhd/amalitech-lab3-advanced-java.git
   cd amalitech-lab3-advanced-java
   ```

2. **Clean and Compile**
   ```bash
   mvn clean compile
   ```

## Running the Application

You can run the application directly using Maven:

```bash
mvn exec:java -Dexec.mainClass="main.Main"
```

Or manually compile and run:

```bash
mvn compile
java -cp target/classes main.Main
```

## Usage Guide

Upon starting the application, you will be presented with the **Main Menu**:

1. **Manage Accounts**:
    - **Create Account**: Register a new customer and open a Savings or Checking account.
    - **View Account**: List all accounts with their current status and balance.
    - **View Transactions History**: See history for a specific account.
2. **Perform Transactions**:
    - Deposit or Withdraw funds from an existing account.
3. **Generate Account Statements**:
    - Generate a simple statement of transactions for an account.
4. **Save Data**:
    - Persist all current Customers, Accounts, and Transactions to the `data/` directory.
    - **Note**: Data is automatically loaded from this directory when the application starts.
5. **Run Concurrent Simulation**:
    - Runs a multi-threaded simulation (1 deposit, 2 withdrawals) on account `ACC001` to demonstrate thread safety and race condition handling.
6. **Exit**: Closes the application.

## Troubleshooting

- **"AccountNotFoundException"**: Ensure you have created an account before trying to access it. Note that account numbers are usually auto-generated or specified during creation.
- **Concurrency Simulation Fails**: The simulation expects an account with ID `ACC001` to exist. Please create this account if it doesn't exist, or ensure data is loaded correctly.
- **Persistence Issues**: Check that the `data/` directory allows write permissions.

## Testing

Run the automated JUnit tests to ensure system integrity:

```bash
mvn test
```
