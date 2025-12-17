# Java Collections Architecture

This document outlines the usage of the Java Collections Framework in the banking application, specifically focusing on `Lists`, `Maps`, and the `Stream API`.

## 1. Lists
The application primarily uses the `List` interface with the `ArrayList` implementation.

### Implementation: `ArrayList`
**Rationale**: `ArrayList` was chosen as the preferred `List` implementation because:
- **Fast Random Access**: It provides constant-time $O(1)$ access to elements by index.
- **Efficient Iteration**: It offers excellent performance for iterating over elements, which is a common operation in this application (e.g., viewing transaction history).
- **Simplicity**: It is the standard, resizable array implementation in Java, suitable for most general-purpose list needs where insertion at the beginning or middle is rare.

### Usage Examples
- **Transaction History**: Storing a list of transactions for the system or per session.
- **Data Persistence**: Loading lists of `Customer`, `Account`, and `Transaction` objects from files.

**Code Reference (`TransactionManager.java`):**
```java
// Main storage for transactions
private final List<Transaction> transactions = new ArrayList<>();

public synchronized void addTransaction(Transaction transaction){
    transactions.add(transaction);
}
```

**Code Reference (`FilePersistenceService.java`):**
```java
// Loading customers into an ArrayList
List<Customer> customers = new ArrayList<>();
```

---

## 2. Maps
The application uses the `Map` interface with the `HashMap` implementation for efficient data retrieval.

### Implementation: `HashMap`
**Rationale**: `HashMap` was chosen because:
- **Key-Value Association**: It allows associating a unique key (e.g., Account Number) with a value (Account object), which intuitively models the relationship.
- **Performance**: It provides average constant-time $O(1)$ performance for `get` and `put` operations. This is critical for `AccountManager` to quickly find an account by its ID without iterating through the entire collection.

### Usage Examples
- **Account Management**: Storing active accounts mapped by their unique account number.
- **Data Parsing**: Temporarily storing key-value pairs when parsing data strings from text files (e.g., `id=123`, `type=Checking`).

**Code Reference (`AccountManager.java`):**
```java
// Efficient lookup of accounts by Account Number
private final Map<String, Account> accounts = new HashMap<>();

public void addAccount(Account acc) {
    this.accounts.put(acc.getAccountNumber(), acc);
}

public Account findAccount(String accountNumber) {
    return this.accounts.get(accountNumber);
}
```

**Code Reference (`FilePersistenceService.java`):**
```java
// Parsing line data into key-value pairs
Map<String, String> dataMap = new HashMap<>();
data.forEach(d -> {
    String[] entries = d.split("=");
    dataMap.put(entries[0], entries[1]);
});
```

---

## 3. Stream API
The application utilizes the Java Stream API to process collections declaratively, making code more readable and concise.

### Key Operations Used
- **`filter`**: Selecting elements that match specific criteria (e.g., finding transactions for a specific account).
- **`mapToDouble`**: Transforming objects into double values for calculation (e.g., extracting amounts).
- **`sum`**: Aggregating values.
- **`forEach`**: Iterating over elements to perform an action (e.g., printing).
- **`toList`**: Collecting stream results back into a `List`.

### Usage Examples

#### Filtering and Collecting
Fetching all transactions for a specific account:
**`TransactionManager.java`:**
```java
public List<Transaction> getTransactionsByAccount(String accountNumber){
    return this.transactions.stream()
            .filter(t -> t.getAccountNumber().equalsIgnoreCase(accountNumber))
            .toList();
}
```

#### Map-Reduce (Summation)
Calculating the total balance across all accounts:
**`AccountManager.java`:**
```java
public double getTotalBalance() {
    return this.accounts.values().stream()
            .mapToDouble(Account::getBalance)
            .sum();
}
```

#### Filtering and Aggregation
Calculating total deposits for a specific account:
**`TransactionManager.java`:**
```java
public double calculateTotalDeposits(String accountNumber){
    return this.transactions.stream()
          .filter(tr -> tr.getAccountNumber().equalsIgnoreCase(accountNumber) && tr.getType().equals(TransactionType.DEPOSIT))
          .mapToDouble(Transaction::getAmount)
          .sum();
}
```

#### Finding First Element
Locating a customer by ID:
**`FilePersistenceService.java`:**
```java
Customer customer = customers.stream()
        .filter(c -> c.getCustomerId().equals(customerId))
        .findFirst()
        .orElse(null);
```
