package main.models;

public interface Transactable {
    boolean processTransaction(double amount, TransactionType type);
}
