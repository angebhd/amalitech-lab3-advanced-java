package main.service;

import main.models.Account;
import main.models.Transaction;
import main.models.TransactionType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class StatementGenerator {

  public static void generate(Account account, List<Transaction> transactions){
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

  private static void printTransactions(List<Transaction> transactions){
//    transactions.sort((t1, t2) -> t1.getTimestampAsLocalDateTime().isAfter(t2.getTimestampAsLocalDateTime()) ? 1 : -1);
    List<Transaction> transactionSorted = transactions.stream()
            .sorted((t1, t2) ->
                            t1.getTimestampAsLocalDateTime().isEqual(t2.getTimestampAsLocalDateTime()) ? 0
                            : t1.getTimestampAsLocalDateTime().isAfter(t2.getTimestampAsLocalDateTime()) ? 1:-1
                    )
            .toList();
    double change = 0;
    if (transactions.isEmpty()){
      System.out.println("_".repeat(30));
      System.out.println("No transactions found");
      return;
    }
    System.out.println("Transactions: ");
    System.out.println("_".repeat(30));
      for(Transaction transaction: transactionSorted){

        boolean isDeposit = transaction.getType().equals(TransactionType.DEPOSIT);
        String sign = isDeposit ? "+$" : "-$";
        System.out.print(transaction.getTransactionId() + " | ");
        System.out.print(transaction.getType() + " | ");
        System.out.print(sign + String.format("%.2f", transaction.getAmount()) + " | ");
        System.out.printf("$%.2f%n",transaction.getBalanceAfter());
        if (transaction.getType().equals(TransactionType.DEPOSIT)){
          change+=transaction.getAmount();
        }else{
          change-=transaction.getAmount();
        }
      }
    System.out.println("_".repeat(30));
    System.out.printf("Net Change: $%.2f\n" , change);

  }

}
