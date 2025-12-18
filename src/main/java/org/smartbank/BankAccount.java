package org.smartbank;

import org.smartbank.exceptions.InsufficientFundsException;

public class BankAccount {
    private final String accountNumber;
    private final String accountHolder;
    protected double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public BankAccount(String accountNumber, String accountHolder) {
        this(accountNumber, accountHolder, 0.0);
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds in account: " + accountNumber);
        }
        balance -= amount;
    }

    public void transferTo(BankAccount destiny, double amount) throws InsufficientFundsException {
        this.withdraw(amount);
        destiny.deposit(amount);
    }

    public String getAccountInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("ACCOUNT INFO:\n")
                .append("==============================\n")
                .append(this.toString()).append("\n")
                .append("==============================");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Account number: ").append(accountNumber).append("\n")
                .append("Account holder: ").append(accountHolder).append("\n")
                .append("Total balance: ").append(String.format("%.2f", balance)).append("€\n");
        return sb.toString();
    }
}