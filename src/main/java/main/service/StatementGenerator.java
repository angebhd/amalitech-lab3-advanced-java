package main.service;

import main.models.Account;
import main.models.Transaction;
import main.models.TransactionType;


public class StatementGenerator {

  public static void generate(Account account, Transaction[] transactions){
    System.out.println();
    printAccountDetail(account);
    System.out.println();
    printTransactions(transactions);
    System.out.println();
    System.out.println("Statement Generated Successfully");
  }

  private static void printAccountDetail(Account account){
    System.out.println("Account: " + account.getCustomer().getName() + "( " + account.getAccountType() + " )");
    System.out.println("Current balance: $" + account.getBalance());

  }

  private static void printTransactions(Transaction[] transactions){
    double change = 0;
    boolean found = false;
      for(int i =0 ; i< transactions.length; i++){
        if (transactions[i] == null)
          break;
        if (!found){
          System.out.println("Transactions: ");
          System.out.println("_".repeat(30));

          found = true;
        }
        boolean isDeposit = transactions[i].getType().equals(TransactionType.DEPOSIT);
        String sign = isDeposit ? "+$" : "-$";
        System.out.print(transactions[i].getTransactionId() + " | ");
        System.out.print(transactions[i].getType() + " | ");
        System.out.print(sign + String.format("%.2f", transactions[i].getAmount()) + " | ");
        System.out.printf("$%.2f%n",transactions[i].getBalanceAfter());
        if (transactions[i].getType().equals(TransactionType.DEPOSIT)){
          change+=transactions[i].getAmount();
        }else{
          change-=transactions[i].getAmount();
        }
      }
      if(!found){
        System.out.println("_".repeat(30));
        System.out.println("No transactions found");
      }
    System.out.println("_".repeat(30));
    System.out.printf("Net Change: $%.2f\n" , change);

  }

}
