package oop.Interface;

// Second interface — used to demonstrate multiple interface implementation.
// A class can implement both Payable and Taxable at the same time.

public interface Taxable {

    double TAX_RATE = 0.15;  // 15% tax — public static final by default

    double calculateTax(double amount);  // abstract — must be implemented

    // Default method using the abstract method above
    default double totalWithTax(double amount) {
        return amount + calculateTax(amount);
    }
}
