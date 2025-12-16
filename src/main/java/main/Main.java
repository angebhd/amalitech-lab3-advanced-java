package main;

import main.models.Account;
import main.models.exceptions.AccountNotFoundException;
import main.models.exceptions.InsufficientAmountException;
import main.models.exceptions.OverdraftExceededException;
import main.service.AccountManager;
import main.models.CheckingAccount;
import main.models.SavingsAccount;
import main.models.Customer;
import main.models.PremiumCustomer;
import main.models.RegularCustomer;
import main.models.Menu;
import main.models.Transaction;
import main.service.FilePersistenceService;
import main.service.StatementGenerator;
import main.service.TransactionManager;
import main.models.TransactionType;
import main.utils.ConcurrencyUtils;
import main.utils.ValidationUtils;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Main{
  static Scanner scanner = new Scanner(System.in);
  static Menu menu = new Menu();
  static TransactionManager transactionManager = new TransactionManager();
  static AccountManager accountManager = new AccountManager();
  static ValidationUtils validationUtils = new ValidationUtils();
  static FilePersistenceService filePersistenceService = new FilePersistenceService();

  public static void main(String[] args) {
    loadData();
    try{
      Scanner scanner = new Scanner(System.in);
      boolean exitApp = false ;
      int menuChoice;
      do{
        menu.showMainMenu();
        menuChoice = validationUtils.intInput(scanner, 6);
        switch (menuChoice){
          case 1:
            menu.showManageAccount();
            menuChoice = validationUtils.intInput(scanner, 4);
            manageAccountMenu(menuChoice);
            cleanScannerBuffer(scanner);
            break;
          case 2:
            makeTransactionProcess();
            cleanScannerBuffer(scanner);
            break;
          case 3:
            statementGeneratorProcess();
            cleanScannerBuffer(scanner);
            break;
          case 4:
            filePersistenceService.save(accountManager, transactionManager);
            System.out.println("Data saved !");
            cleanScannerBuffer(scanner);
            break;
          case 5:
            System.out.println("Running Concurrent Operations");
            runConcurrentAction();
            cleanScannerBuffer(scanner);
            break;
          case 6:
            exitApp = true;
        }
      }while (!exitApp);
      scanner.close();
      System.out.println("Thank you for using Bank Account Management System !");
      System.out.println("GoodBye!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      main(args);
    }
  }

  static void createAccountProcess(){
    menu.printTitle("ACCOUNT CREATION");
    System.out.println();
    /// Create new Customer
    Customer newCustomer = createCustomerPrompt();

    System.out.println();
    Account newAccount = createAccountPrompt(newCustomer);

    /// save accounts & Customer somewhere, then display success message
    accountManager.addAccount(newAccount);
    viewAccountCreatedDetails(newAccount);
  }

  static void makeTransactionProcess(){
    menu.printTitle("PROCESS TRANSACTION");
    Account account = getAccountPrompt();
    displayAccount(account);
    /// Get Transaction type prompt
    TransactionType transactionType = getTransactionTypePrompt();

    System.out.print("Enter amount: $");
    final double amount = validationUtils.doubleInput(scanner);;
    final int operation = transactionType.equals(TransactionType.DEPOSIT)? 1 : -1;
    Transaction newTransaction = new Transaction(account.getAccountNumber(), transactionType, amount, account.getBalance() + (amount * operation));

    /// transaction confirmation
    boolean transactionConfirmation = confirmTransactionPrompt(newTransaction, account);;
    if (transactionConfirmation){
      transactionManager.addTransaction(newTransaction);
      System.out.println("Transaction completed successfully!");
    }
  }

  static void viewTransactionHistory(){
    menu.printTitle("VIEW TRANSACTION HISTORY");
    System.out.println();
    System.out.print("Enter count number: ");
    String accountNo = validationUtils.validateAccountNumber(scanner);
    try{
      Account account = accountManager.findAccount(accountNo);
      displayAccount(account);
      transactionManager.viewTransactionsByAccount(accountNo);
    }catch (AccountNotFoundException e){
      System.out.println(e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static Customer createCustomerPrompt(){
    System.out.print("Enter customer name: ");
    String name = scanner.nextLine().trim();
    System.out.print("Enter customer age: ");
    int age = validationUtils.intInput(scanner, 120);
    System.out.print("Enter customer contact (Phone number): ");
    String contact = validationUtils.validatePhoneNumber(scanner);
    System.out.print("Enter customer address: ");
    String address = scanner.nextLine().trim();

    System.out.println();
    System.out.println("Customer Type: ");
    System.out.println("1. Regular Customer");
    System.out.println("2. Premium Customer");
    System.out.print("Select type(1-2): ");
    int customerType = validationUtils.intInput(scanner, 2);
    ///  returning customer depending on user choice
    return switch (customerType) {
      case 1 -> new RegularCustomer(name, age, address, contact);
      case 2 -> new PremiumCustomer(name, age, address, contact);
      default -> throw new IllegalArgumentException();
    };
  }
  static Account createAccountPrompt(Customer customer){
    System.out.println("Account Type: ");
    System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
    System.out.println("2. Checking Account (Overdraft: $1,000, Montly fee: $10)");
    System.out.print("Select type(1-2): ");
    int accountType = validationUtils.intInput(scanner, 2);;
    System.out.println();
    System.out.print("Enter initial deposit amount: $");
    double deposit;
    if(accountType == 1){
      deposit = validationUtils.doubleInput(scanner, 500, "Invalid amount, minimum balance is $" + 500 + " for saving account, try again: ");;
    }else{
      deposit = validationUtils.doubleInput(scanner);;
    }

    return switch (accountType) {
      case 1 -> new SavingsAccount(customer, deposit, "ACTIVE");
      case 2 -> new CheckingAccount(customer, deposit, "ACTIVE");
      default -> throw new IllegalArgumentException("Invalid account type choice");
    };
  }
  static Account getAccountPrompt(){
    System.out.print("Enter Account number: ");
    String accountNo = validationUtils.validateAccountNumber(scanner);
    try {
      return accountManager.findAccount(accountNo);
    } catch (AccountNotFoundException e){
      System.out.println(e.getMessage());
      System.out.println();
      return getAccountPrompt();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  static void displayAccount(Account account){
    System.out.println("Account Details: ");
    System.out.println("\tCustomer: " + account.getCustomer().getName());
    System.out.println("\tAccount Type: " + account.getAccountType());
    System.out.printf("\tCurrent Balance: $%.2f%n", account.getBalance());
    System.out.println();
  }

  static TransactionType getTransactionTypePrompt(){
    System.out.println("Transaction Type: ");
    System.out.println("1. Deposit");
    System.out.println("2. Withdrawal");
    System.out.println();
    System.out.print("Select type(1-2): ");
    int chosenTransaction = validationUtils.intInput(scanner, 2);;
    System.out.println();
    return   switch (chosenTransaction){
      case 1 -> TransactionType.DEPOSIT;
      case 2 -> TransactionType.WITHDRAW;
      default -> throw new IllegalArgumentException("Invalid transaction type");
    };
  }
  static boolean confirmTransactionPrompt(Transaction transaction, Account account){
    menu.printTitle("TRANSACTION CONFIRMATION");
    System.out.println("Transaction ID: " + transaction.getTransactionId());
    System.out.println("Account: " + transaction.getAccountNumber());
    System.out.printf("Amount: $%.2f%n", transaction.getAmount());
    System.out.printf("Previous Balance: %.2f%n", account.getBalance());
    System.out.printf("New Balance: $%.2f%n", transaction.getBalanceAfter());
    System.out.println("Date/Time: " + transaction.getTimestamp());
    System.out.println("_____________________________________________");
    System.out.println();
    System.out.print("Confirm Transaction? (Y/N): ");
    char confirm = scanner.nextLine().trim().toUpperCase().charAt(0);
    scanner.reset();
    if(confirm != 'Y'){
      System.out.println("Transaction canceled .....");
      System.out.println();
      return false;
    }
    if (transaction.getType().equals(TransactionType.WITHDRAW)) {
      try{
        account.withdraw(transaction.getAmount());
        System.out.println("Amount " + transaction.getAmount() + "$ sucessfully withdrawn");

      }catch (OverdraftExceededException | InsufficientAmountException e){
        System.out.println("_____________________________________________");
        System.out.println("Transaction failed!!!");
        System.out.println("Reason: \n" + e.getMessage());
        System.out.println();
        return false;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }else
      account.deposit(transaction.getAmount());
    return true;
  }
  static void viewAccountCreatedDetails(Account account){
    System.out.println("\nAccount Created Successfully!");
    System.out.println("Account Number: "+ account.getAccountNumber());
    System.out.println("Customer "+ account.getCustomer().getName() + " ( " + account.getCustomer().getCustomerType() + " )") ;
    System.out.println("Account Type: "+ account.getAccountType());
    ///  display saving account details
    if(account instanceof SavingsAccount){
      System.out.println("Interest Rate: "+ ((SavingsAccount) account).getInterestRate() + "%");
      System.out.printf("Minimum balance: $%.2f%n", ((SavingsAccount) account).getMinimumBalace());
    }
    /// for Checking account display checking account information details
    if(account instanceof  CheckingAccount){
      System.out.println("Overfraft Limit: "+ ((CheckingAccount) account).getOverdraftLimit());
      System.out.printf("Monthly fee: $%.2f", ((CheckingAccount) account).getMonthlyFee());
      ///  if premium customer, check if monthly fees are waived
      if(((CheckingAccount) account).getMonthlyFee() == 0){
        System.out.print(" ( WAIVED - Premium Customer) ");
      }
    }
    System.out.printf("\nInitial Balance: $%.2f%n", account.getBalance());
    System.out.println("Status: "+ account.getStatus());
  }
  static void cleanScannerBuffer(Scanner scanner){
    System.out.print("Press enter to continue...");
    scanner.nextLine();
    System.out.println();
  }

  static void manageAccountMenu(int option){
    switch (option){
      case 1:
        createAccountProcess();
        break;
      case 2:
        accountManager.viewAllAccount();
        break;
      case 3:
        viewTransactionHistory();
    }
  }

  static void statementGeneratorProcess(){
    menu.printTitle("GENERATE ACCOUNT STATEMENT");
    Account  account = getAccountPrompt();
    List<Transaction> transactions = transactionManager.getTransactionsByAccount(account.getAccountNumber());
    StatementGenerator.generate(account, transactions);
  }

  static void loadData(){
    try{
      filePersistenceService.load(accountManager, transactionManager);
      System.out.println("Data loaded sucessfully!");
      System.out.println("||||||||||||||||||||||||");
    } catch (Exception e) {
      System.out.println("Failed to load data...");
    }
  }

  static void runConcurrentAction(){
    Account account = accountManager.findAccount("ACC001");
    Runnable thread1 = new ConcurrencyUtils(transactionManager, account, 500, TransactionType.DEPOSIT, account.getBalance()+500);
    Runnable thread2 = new ConcurrencyUtils(transactionManager, account, 100, TransactionType.WITHDRAW, account.getBalance()-100);
    Runnable thread3 = new ConcurrencyUtils(transactionManager, account, 200, TransactionType.WITHDRAW, account.getBalance()-100);

    System.out.println("Balance before: "+ account.getBalance());

    try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
      executorService.submit(thread1);
      executorService.submit(thread2);
      executorService.submit(thread3);
    }catch (Exception e){
      System.out.println("Failed to run concurrent operations");
    }
    System.out.println("Balance after: "+ account.getBalance());

  }
}
