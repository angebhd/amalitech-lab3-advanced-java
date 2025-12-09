import main.models.Account;
import main.models.CheckingAccount;
import main.models.PremiumCustomer;
import main.models.SavingsAccount;
import main.models.exceptions.AccountNotFoundException;
import main.models.exceptions.InsufficientAmountException;
import main.models.exceptions.OverdraftExceededException;
import main.service.AccountManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExceptionTest {

  @Test
  @DisplayName("Should throw overdraft limit exception")
  void insufficientAmount(){
    PremiumCustomer premium = new PremiumCustomer("Elvis Presley", 19, "Los Angeles", "+1 2334 2245");
    Account savingAccount = new SavingsAccount(premium, 500, "Active");
    Assertions.assertThrows(InsufficientAmountException.class, () -> savingAccount.withdraw(1001));
  }

  @Test
  @DisplayName("Should throw overdraft limit exception")
  void overfdaftLimit(){
    PremiumCustomer premium = new PremiumCustomer("Elvis Presley", 19, "Los Angeles", "+1 2334 2245");
    CheckingAccount checkingAccount = new CheckingAccount(premium, 0, "Active");
    Assertions.assertThrows(OverdraftExceededException.class, ()->checkingAccount.withdraw(1001));
  }

  @Test
  @DisplayName("Should throw account not found exception")
  void accountNotFound(){
    AccountManager accountManager = new AccountManager();
    Assertions.assertThrows(AccountNotFoundException.class, ()->accountManager.findAccount("is actually empty"));
  }

}
