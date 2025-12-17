package main.models;

public class PremiumCustomer extends Customer {
  public PremiumCustomer(String name, int age, String address, String contact) {
    super(name, age, address, contact);
  }

  public PremiumCustomer(String id, String name, int age, String address, String contact) {
    super(id, name, age, address, contact);
  }

  @Override
  public void displayCustomerDetail() {
    System.out.println("Premium Account");

  }

  @Override
  public String getCustomerType() {
    return "Premium";
  }

  public boolean hasWaivedFees(double balance) {
    double minimumBalance = 10000;
    return balance >= minimumBalance;
  }


}
