package org.smartbank;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<BankAccount> accountList;

    public Bank() {
        this.accountList = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accountList.add(account);
    }

    public BankAccount findAccount(String accountNumber) {
        for (BankAccount acc : accountList) {
            if (acc.getAccountNumber().equals(accountNumber)){
                return acc;
            }
        }
        return null;
    }

    public double getTotalBalance() {
        double total = 0;
        for (BankAccount account : accountList){
            total += account.getBalance();
        }
        return total;
    }

    public List<BankAccount> getAccountsWithBalanceGreaterThan(double minBalance) {
        List<BankAccount> result = new ArrayList<>();
        for (BankAccount acc : accountList) {
            if (acc.getBalance() > minBalance){
                result.add(acc);
            }
        }
        return result;
    }
}