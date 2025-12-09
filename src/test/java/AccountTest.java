import main.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountTest {

  Customer customer = new RegularCustomer("Ange Buhendwa", 20, "Kigali", "+250");
  Account account = new SavingsAccount(customer, 8000, "ACTIVE");


  @Test
  @DisplayName("Deposit account should work")
  void deposit(){
    double currentBalance = account.getBalance();
    double depositedAmount = 1450;
    account.deposit(depositedAmount);
    Assertions.assertEquals(currentBalance+depositedAmount, account.getBalance());
  }
  @Test
  @DisplayName("Withdrawal operation should work")
  void withdraw(){
    double balance = 8000;
    account.setBalance(balance);
    double withdrew = 23;
    account.withdraw(withdrew);
    Assertions.assertEquals(balance-withdrew, account.getBalance());
    balance -= withdrew;
    withdrew = 124;
    account.withdraw(withdrew);
    Assertions.assertEquals(balance-withdrew, account.getBalance());
  }

  @Test
  @DisplayName("Should create accounts")
  void createAccount(){
    Account account = new SavingsAccount(customer, 8000, "ACTIVE");
    Assertions.assertNotNull(account);
  }

  @Test
  @DisplayName("should waive montly fee for eligible people")
  void monthlyFee(){
    PremiumCustomer premium = new PremiumCustomer("Joe Dalton", 29, "USA", "+12345");
    CheckingAccount checkingAccount = new CheckingAccount(premium, 10000, "ACTIVE");
    Assertions.assertTrue(premium.hasWaivedFees(checkingAccount.getBalance()));

    checkingAccount.setBalance(9999);
    Assertions.assertFalse(premium.hasWaivedFees(checkingAccount.getBalance()));

  }
}
