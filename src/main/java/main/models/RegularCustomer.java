package main.models;

public class RegularCustomer extends Customer {
  public RegularCustomer(String name, int age, String address, String contact) {
    super(name, age, address, contact);
  }
  public RegularCustomer(String id, String name, int age, String address, String contact) {
    super(id, name, age, address, contact);
  }

    @Override
    public  void displayCustomerDetail(){
        System.out.println("Details: \nName: " + this.getName());
    }

    @Override
    public  String getCustomerType(){
    return "Regular";
    }
}