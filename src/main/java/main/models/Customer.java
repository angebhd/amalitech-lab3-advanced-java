package main.models;

public abstract class Customer {
  public Customer( String id, String name, int age, String address, String contact) {
    this.customerId = id;
    this.address = address;
    this.contact = contact;
    this.age = age;
    this.name = name;
    customerCounter++;
  }
    public Customer(String name, int age, String address, String contact) {
        this.customerId = generateId();
        this.address = address;
        this.contact = contact;
        this.age = age;
        this.name = name;
    }
    private final String customerId;
    private String name;
    private int age;
    private String contact;
    private String address;

    public static int customerCounter;

    public abstract void displayCustomerDetail();
    public abstract String getCustomerType();


    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }


    public String getContact() {
        return contact;
    }


    public String getAddress() {
        return address;
    }


    public String getCustomerId() {
        return customerId;
    }


private String generateId (){
    Customer.customerCounter++;
    String count = String.valueOf(Customer.customerCounter);
    if (count.length() > 2)
        return "CUS"+count;
    return "CUS"+ "0".repeat(3 - count.length()) + count ;
}
}