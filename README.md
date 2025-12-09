# Java Clean Code Lab - Bank Account Management
A clean, well-tested bank account management CLI application built with Java and Maven

**By Ange Buhendwa**


## Overview
A console-based bank account management system that allows users to create accounts, manage transactions, and view account details. The application supports different customer types (Regular and Premium) and account types (Savings and Checking) with specific features and benefits.

## Features
- **Account Management**: Create and view bank accounts
- **Customer Types**: 
  - Regular Customer: Standard banking features
  - Premium Customer: Waived monthly fees on checking accounts
- **Account Types**:
  - Savings Account: 3.5% interest rate, $500 minimum balance
  - Checking Account: $1,000 overdraft limit, $10 monthly fee (waived for premium customers)
- **Transaction Processing**: Deposit and withdrawal operations
- **Transaction History**: View complete transaction records by account
- **Auto-generated IDs**: Automatic generation of account, customer, and transaction IDs
- **Custom Exception Handling**: Robust input validation and error management
- **Unit Testing**: Comprehensive test coverage with JUnit 5

## Project Structure
```
├── docs/
│   └── git-workflow.md
├── src/
│   ├── main/java/main/
│   │   ├── models/
│   │   │   ├── exceptions/
│   │   │   │   ├── AccountNotFoundException.java
│   │   │   │   ├── InsufficientAmountException.java
│   │   │   │   ├── InvalidAmountException.java
│   │   │   │   └── OverdraftExceededException.java
│   │   │   ├── Account.java
│   │   │   ├── CheckingAccount.java
│   │   │   ├── Customer.java
│   │   │   ├── Helper.java
│   │   │   ├── Menu.java
│   │   │   ├── PremiumCustomer.java
│   │   │   ├── RegularCustomer.java
│   │   │   ├── SavingsAccount.java
│   │   │   ├── Transactable.java
│   │   │   ├── Transaction.java
│   │   │   └── TransactionType.java
│   │   ├── service/
│   │   │   ├── AccountManager.java
│   │   │   ├── StatementGenerator.java
│   │   │   └── TransactionManager.java
│   │   ├── utils/
│   │   │   └── ValidationUtils.java
│   │   └── Main.java
│   └── test/java/
│       ├── AccountTest.java
│       ├── ExceptionTest.java
│       └── TransactionManagerTest.java
├── pom.xml
└── README.md
```

## Project Setup
**Requirements:**
- JDK version 21 or higher
- Maven 3.6+ (for dependency management)

**Running the project**: 

```bash
# Clone and access the repository
git clone https://github.com/angebhd/amalitech-lab-java-basics-bank-account-management.git
cd amalitech-lab-java-basics-bank-account-management

# Compile and run with Maven
mvn compile exec:java -Dexec.mainClass="main.Main"

# Or compile and run manually
mvn compile
java -cp target/classes main.Main
```

## Testing
**Run unit tests:**
```bash
# Run all tests
mvn test
```

## Git Workflow
For navigating between project branches and using cherry-pick commands, see [Git Workflow Guide](docs/git-workflow.md).

## Usage
The application provides a menu-driven interface with the following options:

1. **Create Account**: Set up new customer and account with initial deposit
2. **View Accounts**: Display all accounts with balances and details
3. **Process Transaction**: Perform deposits or withdrawals
4. **View Transaction History**: Review transaction records for specific accounts
5. **Exit**: Close the application

## Code Quality & Architecture
- **SOLID Principles**: Clean architecture with proper separation of concerns
- **DRY Principle**: Eliminated code duplication through proper abstraction
- **Clean Code**: Meaningful naming conventions and readable code structure
- **Method Best Practices**: Methods under 25 lines, maximum 3 parameters
- **Custom Exceptions**: Robust error handling and input validation
- **Unit Testing**: Comprehensive test coverage with JUnit 5

## Account Features
- **Savings Account**: Earns 3.5% interest, requires $500 minimum balance
- **Checking Account**: Allows overdraft up to $1,000, $10 monthly fee (waived for premium customers with sufficient balance)
- **Premium Customer Benefits**: Monthly fee waiver on checking accounts