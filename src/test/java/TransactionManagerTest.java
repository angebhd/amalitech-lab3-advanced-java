import main.models.Transaction;
import main.models.TransactionType;
import main.service.TransactionManager;
import org.junit.jupiter.api.*;

public class TransactionManagerTest {
  static TransactionManager transactionManager;

  @BeforeEach
  void setup(){
    transactionManager = new TransactionManager();
  }


  @Test
  @DisplayName("Should Add Transactions")
  void addTransaction(){
    int transactions = transactionManager.getTransactionCount();
    Transaction transaction1 = new Transaction("ACC001", TransactionType.DEPOSIT, 500, 1500);
    transactionManager.addTransaction(transaction1);
    transactions++;
    Assertions.assertEquals(transactions, transactionManager.getTransactionCount());
  }

  @Test
  @DisplayName("Should get total deposits by account")
  void calculateDeposits(){
    final double[] amounts = new double[]{500, 600, 700, 100, 12};
    final String accountNo = "ACC001";
    Transaction transaction0 = new Transaction(accountNo, TransactionType.DEPOSIT, amounts[0], 1500);
    Transaction transaction1 = new Transaction(accountNo, TransactionType.DEPOSIT, amounts[1], 1500);
    Transaction transaction2 = new Transaction(accountNo, TransactionType.DEPOSIT, amounts[2], 1500);
    Transaction transaction3 = new Transaction(accountNo, TransactionType.DEPOSIT, amounts[3], 1500);
    Transaction transaction4 = new Transaction(accountNo, TransactionType.DEPOSIT, amounts[4], 1500);
    transactionManager.addTransaction(transaction0);
    transactionManager.addTransaction(transaction1);
    transactionManager.addTransaction(transaction2);
    transactionManager.addTransaction(transaction3);
    transactionManager.addTransaction(transaction4);
    Assertions.assertEquals(getArraySum(amounts), transactionManager.calculateTotalDeposits(accountNo));
  }

  @Test
  @DisplayName("Should get total withdraw by account")
  void calculateWithdrawals(){
    final double[] amounts = new double[]{12, 35626, 700, 100, 12};
    final String accountNo = "ACC001";
    Transaction transaction0 = new Transaction(accountNo, TransactionType.WITHDRAW, amounts[0], 1500);
    Transaction transaction1 = new Transaction(accountNo, TransactionType.WITHDRAW, amounts[1], 1500);
    Transaction transaction2 = new Transaction(accountNo, TransactionType.WITHDRAW, amounts[2], 1500);
    Transaction transaction3 = new Transaction(accountNo, TransactionType.WITHDRAW, amounts[3], 1500);
    Transaction transaction4 = new Transaction(accountNo, TransactionType.WITHDRAW, amounts[4], 1500);
    transactionManager.addTransaction(transaction0);
    transactionManager.addTransaction(transaction1);
    transactionManager.addTransaction(transaction2);
    transactionManager.addTransaction(transaction3);
    transactionManager.addTransaction(transaction4);
    Assertions.assertEquals(getArraySum(amounts), transactionManager.calculateTotalWithdrawals(accountNo));
  }

  public static double getArraySum(double[] array){
    double sum = 0;
    for(double a: array)
      sum+=a;
    return sum;
  }
}
