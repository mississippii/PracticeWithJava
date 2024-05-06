package org.OOP;

public class SavingAccount extends Account {
    private final double interestRate;
    private final int MAX_WITHDRAWAL=5;
    private int withdrawals;
    public SavingAccount(String accountNumber, double amount, double interestRate) {
        super(accountNumber, amount);
        this.interestRate = interestRate;
    }
    public double getInterestRate() {return (getBalance()*interestRate)/100.0;}

    @Override
    public void  withdraw(double amount) {
        if(withdrawals==MAX_WITHDRAWAL){
            System.out.println("Withdrawal limit reached");
        }else{
            super.withdraw(amount);
            withdrawals++;
        }
    }
}
