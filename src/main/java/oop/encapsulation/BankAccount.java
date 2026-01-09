package oop._02_encapsulation;

/**
 * Classic Encapsulation Example
 * Demonstrates:
 * - Private fields (data hiding)
 * - Public getters and setters
 * - Data validation in setters
 * - Controlled access to data
 */
public class BankAccount {
    // Private fields - cannot be accessed directly from outside
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private String accountType;

    // Constructor
    public BankAccount(String accountNumber, String accountHolder, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.balance = 0.0;  // Initial balance
    }

    // Getter for balance (read-only, no setter)
    public double getBalance() {
        return balance;
    }

    // Getter for account number (read-only)
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for account holder
    public String getAccountHolder() {
        return accountHolder;
    }

    // Setter for account holder (with validation)
    public void setAccountHolder(String accountHolder) {
        if (accountHolder != null && !accountHolder.trim().isEmpty()) {
            this.accountHolder = accountHolder;
        } else {
            System.out.println("Invalid account holder name");
        }
    }

    // Controlled method to modify balance - deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
            System.out.println("New Balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    // Controlled method to modify balance - withdraw
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount");
            return false;
        }

        if (amount > balance) {
            System.out.println("Insufficient balance");
            return false;
        }

        balance -= amount;
        System.out.println("Withdrawn: $" + amount);
        System.out.println("Remaining Balance: $" + balance);
        return true;
    }

    // Display account info
    public void displayInfo() {
        System.out.println("\n=== Account Information ===");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: $" + balance);
    }

    // Main method to test
    public static void main(String[] args) {
        // Create bank account
        BankAccount account = new BankAccount("123456789", "John Doe", "Savings");

        // Cannot access private fields directly
        // account.balance = 1000000;  // ERROR: balance is private

        // Must use public methods (controlled access)
        account.deposit(1000);
        account.deposit(500);
        account.withdraw(200);
        account.withdraw(2000);  // Insufficient balance

        account.displayInfo();

        // Read-only access to balance
        System.out.println("\nCurrent balance: $" + account.getBalance());

        // Try invalid deposit
        account.deposit(-100);  // Invalid amount
    }
}
