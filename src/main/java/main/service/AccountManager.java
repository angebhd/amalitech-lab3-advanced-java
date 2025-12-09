package main.service;

import main.models.Account;
import main.models.Menu;
import main.models.exceptions.AccountNotFoundException;

public class AccountManager {
    private final Account[] accounts = new Account[50]  ;
    private int accountCount = 0;
    private final Menu menu = new Menu();
    public void addAccount(Account acc) {
        this.accounts[accountCount] = acc;
        this.accountCount++;
    }

    /**
     * Find an account corresponding to the given account number
     * @param accountNumber Account number
     * @return The account found
     */
    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        for (int i = 0; i < accountCount ; i++) {
            if (this.accounts[i].getAccountNumber().equalsIgnoreCase(accountNumber))
                return this.accounts[i];
        }
        throw new AccountNotFoundException(accountNumber);
    }

    public void viewAllAccount(){
        menu.printTitle("ACCOUNT LISTING");
        if(accounts.length == 0){
            System.out.println("Nothing to show");
            return;
        }
        System.out.println("ACC NO   |   CUSTOMER NAME     |  TYPE   |   BALANCE    |  STATUS   | OTHERS   ");
        System.out.println("_______________________________________________________________________________");
        for (int i =0; i< Account.accountCounter; i++){
            menu.printViewAccountRow(accounts[i]);
        }
        System.out.println();
        System.out.println("Total Accounts: " + Account.accountCounter);
        System.out.printf("Total Bank Balance: $ %.2f%n" , getTotalBalance());
    }

    public double getTotalBalance(){
        double totalBalance = 0;
        for(Account ac: this.accounts){
            if(ac == null)
                break;
            totalBalance += ac.getBalance();
        }
        return totalBalance;
    }

}

