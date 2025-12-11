package main.service;

import main.models.Transaction;
import main.models.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    /**
     * Prints the transaction history of an account
     * @param accountNumber Account number
     */
    public void viewTransactionsByAccount(String accountNumber){
        boolean found = false;
        for(Transaction transaction: this.transactions){
            if(transaction.getAccountNumber().equals(accountNumber)){
                if (!found) {
                    System.out.println("\n___________________________________________");
                    System.out.println(" TXN ID |       DATE/TIME        |  TYPE    |   AMOUNT   | BALANCE ");
                    System.out.println("_____________________________________________");
                    found=true;
                }
                char transactionType = transaction.getType().equals(TransactionType.DEPOSIT) ? '+' : '-';
                System.out.println(transaction.getTransactionId() + " | " + transaction.getTimestamp() + " | " +
                        transaction.getType().toString()  + " | "+ transactionType +"$" + transaction.getAmount() + " | $" +
                        transaction.getBalanceAfter());
            }
        }
        if (found){
            viewTransactionSummary(accountNumber);;
        }else
            System.out.println("No transactions recorded for this account");
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber){
      return this.transactions.stream().filter(t -> t.getAccountNumber().equalsIgnoreCase(accountNumber)).toList();
    }

    /**
     * Calculate the total amount deposited in a given account
     * @param accountNumber Account number
     * @return Total deposit amount in an account
     */
    public double calculateTotalDeposits(String accountNumber){

      return this.transactions.stream()
              .filter(tr -> tr.getAccountNumber().equalsIgnoreCase(accountNumber) && tr.getType().equals(TransactionType.DEPOSIT))
              .mapToDouble(Transaction::getAmount)
              .sum();

    }
    /**
     * Calculate the total amount withdrawn in a given account
     * @param accountNumber Account number
     * @return Total withdrawn amount in an account
     */
    public double calculateTotalWithdrawals(String accountNumber){
      return this.transactions.stream()
              .filter(tr -> tr.getAccountNumber().equalsIgnoreCase(accountNumber) && tr.getType().equals(TransactionType.WITHDRAW))
              .mapToDouble(Transaction::getAmount)
              .sum();
    }

    public int getTransactionCount(){ return this.transactions.size(); }

    private void viewTransactionSummary(String accountNumber){
        final double totalDeposit =  calculateTotalDeposits(accountNumber);
        final double totalWithdrawal =  calculateTotalWithdrawals(accountNumber);
        System.out.println();
        System.out.println("Total transactions: " + transactions.size());
        System.out.println("___________________________________________");
        System.out.println("Total deposits: $" + totalDeposit);
        System.out.println("Total withdrawals: $" + totalWithdrawal);
        System.out.println("Net change: $" + (totalDeposit-totalWithdrawal));
    }

}
