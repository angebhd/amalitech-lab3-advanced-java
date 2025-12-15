package main.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ange Buhendwa
 */
public class Transaction{

    public Transaction(String accountNumber, TransactionType type, double amount, double balanceAfter) {
        this.transactionId = generateId();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a"));
    }
  public Transaction(String id, String accountNumber, TransactionType type, double amount, double balanceAfter, String timestamp) {
    this.transactionId = id; transactionCounter++;
    this.accountNumber = accountNumber;
    this.type = type;
    this.amount = amount;
    this.balanceAfter = balanceAfter;
    this.timestamp = timestamp;
  }

    static  int transactionCounter = 0;
    private final String transactionId;
    private final String accountNumber;
    private final TransactionType type;
    private final double amount;
    private final double balanceAfter;
    private final String timestamp;

    public void displayTransactionDetails(){
        System.out.println("Transaction Details \nAccount Number: " + this.accountNumber);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public static int getTransactionCounter() {
        return transactionCounter;
    }

    private String generateId (){
        Transaction.transactionCounter++;
        String count = String.valueOf(Transaction.transactionCounter);
        if (count.length() > 2)
            return "TXN"+count;
        return "TXN"+ "0".repeat(3 - count.length()) + count ;
    }

    public LocalDateTime getTimestampAsLocalDateTime(){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
      return LocalDateTime.parse(timestamp, formatter);
    }
}
