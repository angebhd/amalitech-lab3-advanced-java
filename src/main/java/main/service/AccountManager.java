package main.service;

import main.models.Account;
import main.models.Menu;
import main.models.exceptions.AccountNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountManager {
  private final Map<String, Account> accounts = new HashMap();
  private final Menu menu = new Menu();

  public synchronized void addAccount(Account acc) {
    this.accounts.put(acc.getAccountNumber(), acc);
  }

  /**
   * Find an account corresponding to the given account number
   *
   * @param accountNumber Account number
   * @return The account found
   */
  public Account findAccount(String accountNumber) {
    return this.accounts.get(accountNumber);

  }

  public void viewAllAccount() {
    menu.printTitle("ACCOUNT LISTING");
    if (this.accounts.isEmpty()) {
      System.out.println("Nothing to show");
      return;
    }
    System.out.println("ACC NO   |   CUSTOMER NAME     |  TYPE   |   BALANCE    |  STATUS   | OTHERS   ");
    System.out.println("_______________________________________________________________________________");

    /// Prints every accounts details row by row
    this.accounts.values().forEach(menu::printViewAccountRow);

    System.out.println();
    System.out.println("Total Accounts: " + Account.accountCounter);
    System.out.printf("Total Bank Balance: $ %.2f%n", getTotalBalance());
  }

  public double getTotalBalance() {
    return this.accounts.values().stream().mapToDouble(Account::getBalance).sum();
  }

  Map<String, Account> getAccounts() {
    return this.accounts;
  }

}

