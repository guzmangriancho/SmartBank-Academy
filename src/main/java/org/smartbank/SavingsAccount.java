package org.smartbank;

public class SavingsAccount extends BankAccount {
    private final double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double balance, double interestRate) {
        super(accountNumber, accountHolder, balance);
        this.interestRate = interestRate;
    }

    public void applyMonthlyInterest() {
        balance += balance * (interestRate / 100);
    }

    public String getAccountInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("SAVINGS ACCOUNT INFO:\n")
                .append("==============================\n")
                .append(this).append("\n")
                .append("==============================");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n")
                .append("Interest rate : ").append(interestRate).append("%");
        return sb.toString();
    }
}
