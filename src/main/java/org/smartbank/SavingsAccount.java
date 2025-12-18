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

    @Override
    public String getAccountInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getAccountInfo()).append("\n");
        sb.append("Interest rate: ").append(interestRate).append("%\n")
                .append("==============================");

        return sb.toString();
    }
}
