package org.OOP;

public class SavingAccount extends Account {
    private final double interestRate;
    public SavingAccount(String accountNumber, double amount, double interestRate) {
        super(accountNumber, amount);
        this.interestRate = interestRate;
    }
}
