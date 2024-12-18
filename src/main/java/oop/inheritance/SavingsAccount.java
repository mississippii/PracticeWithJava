package oop.inheritance;

public class SavingsAccount extends Account{
    public static final int MAX_WITHDRAWL=5;
    private final double interestRate;
    private int withdrawalCnt;

    public SavingsAccount(String accountNumber, int balance,double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(int amount) {
        if(withdrawalCnt==MAX_WITHDRAWL){
            System.out.println("Withdrawal limit reached");
        }else{
            super.withdraw(amount);
            withdrawalCnt++;
        }
    }
}


