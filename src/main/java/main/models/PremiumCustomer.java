package main.models;

public class PremiumCustomer extends Customer {
  public PremiumCustomer(String name, int age, String address, String contact) {
    super(name, age, address, contact);
  }

  public PremiumCustomer(String id, String name, int age, String address, String contact) {
    super(id, name, age, address, contact);
  }

  private double minimumBalance = 10000;

  @Override
  public void displayCustomerDetail() {
    System.out.println("Premium Account");

  }

  @Override
  public String getCustomerType() {
    return "Premium";
  }

  public boolean hasWaivedFees(double balance) {
    return balance >= minimumBalance;
  }

  public double getMinimumBalance() {
    return minimumBalance;
  }

  public void setMinimumBalance(double minimumBalance) {
    this.minimumBalance = minimumBalance;
  }
}
