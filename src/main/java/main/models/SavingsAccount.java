package main.models;

import main.models.exceptions.InsufficientAmountException;

public class SavingsAccount extends Account {
    /**
     * Constructor for Saving Account
     * Automatically set the minimum balance and interest rate
     * @param customer The owner of the account
     * @param balance The initial balance of the account
     * @param status ACTIVE/INACTIVE account status
     */
    public SavingsAccount(Customer customer, double balance, String status){
        super(customer, balance, status);
        this.interestRate = 3.5;
        this.minimumBalance = 500;
    }

    private final double interestRate;
    private final double minimumBalance;

    /**
     * Print account detail in the console
     */
    @Override
    public void displayAccountDetail(){
        System.out.println("Account Details: ");
        System.out.println("\tCustomer: " + this.getCustomer().getName());
        System.out.println("\tAccount Type: " + getAccountType());
        System.out.println("\tCurrent Balance: " + this.getBalance());
    }

    @Override
    public String getAccountType(){
        return "Savings";
    }

    @Override
    public void withdraw(double amount){
        if(this.getBalance() >= amount+ this.minimumBalance){
            this.setBalance(this.getBalance() - amount);
            System.out.println("Amount " + amount + "$ sucessfully withdrawn");
            return;
        }
        throw new InsufficientAmountException(amount, this.getBalance());
    }

    public double calculateInterest(){
        return this.getBalance() + (this.getBalance() * this.interestRate);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumBalace() {
        return this.minimumBalance;
    }
}