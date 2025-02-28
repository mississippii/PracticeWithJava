package oop.inheritance;

public class Account {
    private final String accountNumber;
    private  double balance;
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    public double getBalance() {return balance;}
    public void deposit(double amount) {
        balance+=amount;
        System.out.printf("{0} take deposit to your account.\n",amount);
        System.out.printf("Your current balance is: {0}\n" ,balance);
    }
    public void withdraw(int amount) {
        if(balance<amount) {
            System.out.println("insufficient balance");
        }
        else {
            balance-=amount;
            System.out.printf("{0} take withdraw from your account.\n",amount);
            System.out.printf("your current balance is: {0}\n",balance);
        }
    }
}
