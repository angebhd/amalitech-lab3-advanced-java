package main.utils;

import main.models.Account;
import main.models.Transaction;
import main.models.TransactionType;
import main.service.TransactionManager;

import java.util.UUID;

public class ConcurrencyUtils implements Runnable {

  private final TransactionManager transactionManager;
  private final TransactionType type;
  private final Account account;
  private final double amount;
  private final double balanceAfter;
  public ConcurrencyUtils(TransactionManager transactionManager, Account account, double amount, TransactionType type, double balanceAfter) {
    this.transactionManager = transactionManager;
    this.type = type;
    this.account = account;
    this.amount = amount;
    this.balanceAfter = balanceAfter;
  }

  @Override
  public void run() {
      Transaction transaction = new Transaction(this.account.getAccountNumber(), this.type, this.amount, this.balanceAfter);
      this.transactionManager.addTransaction(transaction);
    if (this.type == TransactionType.WITHDRAW) {
      account.withdraw(transaction.getAmount());
      System.out.println("Withdrawal");
    }else {
      account.deposit(transaction.getAmount());
      System.out.println("Deposit done");
    }
  }
}
