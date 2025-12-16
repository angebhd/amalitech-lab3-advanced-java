package main.service;

import main.models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FilePersistenceService {
  private final Path CUSTOMER_FILE_PATH = Paths.get("data/customers.txt");
  private final Path ACCOUNTS_FILE_PATH = Paths.get("data/accounts.txt");
  private final Path TRANSACTIONS_FILE_PATH = Paths.get("data/transactions.txt");


  public FilePersistenceService() {
    try {
      Path DATA_DIRECTORY_PATH = Paths.get("data");
      if (!DATA_DIRECTORY_PATH.toFile().exists()) {
        Files.createDirectory(DATA_DIRECTORY_PATH);
      }
      if (!Files.exists(CUSTOMER_FILE_PATH)) {
        Files.createFile(CUSTOMER_FILE_PATH);
      }
      if (!Files.exists(ACCOUNTS_FILE_PATH)) {
        Files.createFile(ACCOUNTS_FILE_PATH);
      }
      if (!Files.exists(TRANSACTIONS_FILE_PATH)) {
        Files.createFile(TRANSACTIONS_FILE_PATH);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private void saveCustomers(List<Customer> customers) {
    customers.forEach(customer -> {
      String line = "id=%s~type=%s~age=%d~address=%s~contact=%s~name=%s\n"
              .formatted(
                      customer.getCustomerId(),
                      customer.getCustomerType(),
                      customer.getAge(),
                      customer.getAddress(),
                      customer.getContact(),
                      customer.getName()
              );
      try {
        Files.write(CUSTOMER_FILE_PATH, line.getBytes(), StandardOpenOption.APPEND);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void saveAccounts(List<Account> accounts) {
    try {
        Files.write(ACCOUNTS_FILE_PATH, "".getBytes());
        Files.write(CUSTOMER_FILE_PATH, "".getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    accounts.forEach(account -> {
      String line = "accountNumber=%s~accountType=%s~balance=%f~customerId=%s~status=%s\n"
              .formatted(
                      account.getAccountNumber(),
                      account.getAccountType(),
                      account.getBalance(),
                      account.getCustomer().getCustomerId(),
                      account.getStatus()
              );
      try {
        Files.write(ACCOUNTS_FILE_PATH, line.getBytes(), StandardOpenOption.APPEND);
        saveCustomers(Collections.singletonList(account.getCustomer()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void saveTransactions(List<Transaction> transactions) {
    try {
      Files.write(TRANSACTIONS_FILE_PATH, "".getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    transactions.forEach(transaction -> {
      String line = "id=%s~accountNumber=%s~amount=%f~balanceAfter=%f~type=%s~timestamp=%s\n"
              .formatted(
                      transaction.getTransactionId(),
                      transaction.getAccountNumber(),
                      transaction.getAmount(),
                      transaction.getBalanceAfter(),
                      transaction.getType().name(),
                      transaction.getTimestamp()
              );
      try {
        Files.write(TRANSACTIONS_FILE_PATH, line.getBytes(), StandardOpenOption.APPEND);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

  }

  private List<Customer> loadCustomers() {
    List<Customer> customers = new ArrayList<>();
    try {
      List<String> lines = Files.readAllLines(CUSTOMER_FILE_PATH);
      for (String line : lines) {
        List<String> data = Arrays.asList(line.split("~"));
        customers.add(buildCustomer(data));
      }
      System.out.println(customers.size() + " Customers(s) loaded from data/customers.txt file");

      return customers;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Account> loadAccounts() {
    List<Account> accounts = new ArrayList<>();
    List<Customer> customers = loadCustomers();
    try {
      List<String> lines = Files.readAllLines(ACCOUNTS_FILE_PATH);
      for (String line : lines) {
        List<String> data = Arrays.asList(line.split("~"));
        accounts.add(buildAccount(data, customers));
      }
      System.out.println(accounts.size() + " Account(s) loaded from data/accounts.txt file");
      return accounts;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Transaction> loadTransactions() {
    List<Transaction> transactions = new ArrayList<>();
    try {
      List<String> lines = Files.readAllLines(TRANSACTIONS_FILE_PATH);
      for (String line : lines) {
        List<String> data = Arrays.asList(line.split("~"));
        transactions.add(buildTransaction(data));
      }
      System.out.println(transactions.size() + " Transactions(s) loaded from data/transactions.txt file");

      return transactions;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Customer buildCustomer(List<String> data) {
    Map<String, String> dataMap = new HashMap<>();
    data.forEach(d -> {
      String[] entries = d.split("=");
      dataMap.put(entries[0], entries[1]);
    });
    String id = dataMap.get("id");
    String type = dataMap.get("type");
    int age = Integer.parseInt(dataMap.get("age"));
    String address = dataMap.get("address");
    String contact = dataMap.get("contact");
    String name = dataMap.get("name");

    return switch (type) {
      case "Premium" -> new PremiumCustomer(id, name, age, address, contact);
      case "Regular" -> new RegularCustomer(id, name, age, address, contact);
      default -> throw new RuntimeException("Invalid account type");
    };
  }

  private Account buildAccount(List<String> data, List<Customer> customers) {
    Map<String, String> dataMap = new HashMap<>();
    data.forEach(d -> {
      String[] entries = d.split("=");
      dataMap.put(entries[0], entries[1]);
    });
    String accountNumber = dataMap.get("accountNumber");
    String accountType = dataMap.get("accountType");
    double balance = Double.parseDouble(dataMap.get("balance"));
    String customerId = dataMap.get("customerId");
    String status = dataMap.get("status");

    Customer customer = customers.stream().filter(c -> c.getCustomerId().equals(customerId)).findFirst().orElse(null);
    return switch (accountType) {
      case "Checking" -> new CheckingAccount(accountNumber, customer, balance, status);
      case "Savings" -> new SavingsAccount(accountNumber,customer, balance, status);
      default -> throw new RuntimeException("Invalid account type");
    };
  }

  private Transaction buildTransaction(List<String> data) {
    Transaction transaction = null;
    Map<String, String> dataMap = new HashMap<>();
    data.forEach(d -> {
      String[] entries = d.split("=");
      dataMap.put(entries[0], entries[1]);
    });
    String transactionId = dataMap.get("id");
    String accountNumber = dataMap.get("accountNumber");
    TransactionType transactionType =  TransactionType.valueOf(dataMap.get("type"));
    double amount = Double.parseDouble(dataMap.get("amount"));
    double balanceAfter = Double.parseDouble(dataMap.get("balanceAfter"));
    String timestamp = dataMap.get("timestamp");

    return new Transaction(transactionId, accountNumber, transactionType,amount, balanceAfter, timestamp);
  }

  public void save(AccountManager accountManager, TransactionManager transactionManager) {
    saveAccounts(accountManager.getAccounts().values().stream().toList());
    saveTransactions(transactionManager.getTransactions());
    System.out.println("Data saved !");

  }
  public void load(AccountManager accountManager, TransactionManager transactionManager) {
    loadAccounts().forEach(accountManager::addAccount);
    loadTransactions().forEach(transactionManager::addTransaction);

  }
}
