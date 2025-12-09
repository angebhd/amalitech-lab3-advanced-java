package main.service;

import main.models.Transactable;
import main.models.Transaction;
import main.models.TransactionType;

public class TransactionManager implements Transactable {
    private final Transaction[] transactions = new Transaction[200];
    private int transactionCount = 0;

    public void addTransaction(Transaction transaction){
        if(transactionCount == this.transactions.length){
            System.out.println("Maximum transaction reached");
            return;
        }
        transactions[transactionCount] = transaction;
        transactionCount++;
    }

    /**
     * Prints the transaction history of an account
     * @param accountNumber Account number
     */
    public void viewTransactionsByAccount(String accountNumber){
        boolean found = false;
        for(int i =0; i < getTransactionCount(); i++){
            if(this.transactions[i].getAccountNumber().equals(accountNumber)){
                if (!found) {
                    System.out.println("\n___________________________________________");
                    System.out.println(" TXN ID |       DATE/TIME        |  TYPE    |   AMOUNT   | BALANCE ");
                    System.out.println("_____________________________________________");
                    found=true;
                }
                char transactionType = this.transactions[i].getType().equals(TransactionType.DEPOSIT) ? '+' : '-';
                System.out.println(this.transactions[i].getTransactionId() + " | " + this.transactions[i].getTimestamp() + " | " +
                        this.transactions[i].getType().toString()  + " | "+ transactionType +"$" + this.transactions[i].getAmount() + " | $" +
                        this.transactions[i].getBalanceAfter());
            }
        }
        if (found){
            viewTransactionSummary(accountNumber);;
        }else
            System.out.println("No transactions recorded for this account");
    }

    public Transaction[] getTransactionsByAccount(String accountNumber){
      int counter = 0;
      Transaction[] accountTransactions = new Transaction[getTransactionCount()];
      for(int i =0; i < getTransactionCount(); i++){
        if(this.transactions[i].getAccountNumber().equals(accountNumber)){
          accountTransactions[counter] = this.transactions[i];
          counter++;
        }
      }
      return accountTransactions;
    }

    /**
     * Calculate the total amount deposited in a given account
     * @param accountNumber Account number
     * @return Total deposit amount in an account
     */
    public double calculateTotalDeposits(String accountNumber){
        double sum = 0;
        for(Transaction transaction: this.transactions){
            if(transaction == null)
                return sum;
            if(transaction.getAccountNumber().equals(accountNumber) && transaction.getType().equals(TransactionType.DEPOSIT)){
                sum+= transaction.getAmount();
            }
        }
        return sum;
    }
    /**
     * Calculate the total amount withdrawn in a given account
     * @param accountNumber Account number
     * @return Total withdrawn amount in an account
     */
    public double calculateTotalWithdrawals(String accountNumber){
        double sum = 0;
        for(Transaction transaction: this.transactions){
            if(transaction == null)
                return sum;
            if(transaction.getAccountNumber().equals(accountNumber) && transaction.getType().equals(TransactionType.WITHDRAW)){
                sum+= transaction.getAmount();
            }
        }
        return sum;
    }

    public int getTransactionCount(){
        return this.transactionCount;
    }

    private void viewTransactionSummary(String accountNumber){
        final double totalDeposit =  calculateTotalDeposits(accountNumber);
        final double totalWithdrawal =  calculateTotalWithdrawals(accountNumber);
        System.out.println();
        System.out.println("Total transactions: " + transactions.length);
        System.out.println("___________________________________________");
        System.out.println("Total deposits: $" + totalDeposit);
        System.out.println("Total withdrawals: $" + totalWithdrawal);
        System.out.println("Net change: $" + (totalDeposit-totalWithdrawal));
    }

    @Override
    public boolean processTransaction(double amount, TransactionType type) {
        return false;
    }
}
