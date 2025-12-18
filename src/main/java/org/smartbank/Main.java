package org.smartbank;

import org.smartbank.exceptions.InsufficientFundsException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Bank bank = new Bank();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option = -1;

        do {
            System.out.println("\nSMARTBANK MENU:");
            System.out.println("1. Create Account");
            System.out.println("2. Create Savings Account");
            System.out.println("3. Deposit money");
            System.out.println("4. Withdraw money");
            System.out.println("5. Transfer funds");
            System.out.println("6. Apply Interest (Savings accounts)");
            System.out.println("7. View Account details");
            System.out.println("8. View bank total balance");
            System.out.println("9. View account over certain balance");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid option");
                scanner.nextLine();
            }

            switch (option) {
                case 1 -> createStandardAccount();
                case 2 -> createSavingsAccount();
                case 3 -> handleDeposit();
                case 4 -> handleWithdraw();
                case 5 -> handleTransfer();
                case 6 -> handleInterest();
                case 7 -> viewAccount();
                case 8 -> viewAccountsOverX();
                case 9 -> viewGlobalBalance();
                case 0 -> {
                    System.out.println("BYE!");
                    System.exit(0);
                }
            }
            System.out.println("\nPress enter to continue...");
            scanner.nextLine();

        } while (option != 0);
    }

    private static void createStandardAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter holder name: ");
        String name = scanner.nextLine();
        System.out.print("Initial balance (leave empty for default): ");
        String balanceInput = scanner.nextLine();

        BankAccount account;
        if (balanceInput.isEmpty()) {
            account = new BankAccount(accountNumber, name);
        } else {
            double balance = Double.parseDouble(balanceInput);
            account = new BankAccount(accountNumber, name, balance);
        }

        bank.addAccount(account);
        System.out.println("Account created!");
    }

    private static void createSavingsAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter holder name: ");
        String name = scanner.nextLine();
        System.out.print("Initial balance: ");
        double balance = scanner.nextDouble();
        System.out.print("Enter interest rate (%): ");
        double rate = scanner.nextDouble();

        SavingsAccount account = new SavingsAccount(accountNumber, name, balance, rate);

        bank.addAccount(account);
        System.out.println("Savings account created successfully!");
    }

    private static void handleDeposit() {
        System.out.print("Enter account number: ");
        String id = scanner.nextLine();
        BankAccount account = bank.findAccount(id);
        if (account != null) {
            System.out.print("Amount to deposit: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found");
        }
    }

    private static void handleWithdraw() {
        System.out.print("Enter account number: ");
        String id = scanner.nextLine();
        BankAccount account = bank.findAccount(id);
        if (account != null) {
            System.out.print("Amount to withdraw: ");
            double amount = scanner.nextDouble();
            try {
                account.withdraw(amount);
            } catch (InsufficientFundsException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        } else {
            System.out.println("Account not found");
        }
    }

    private static void handleTransfer() {
        System.out.print("Origin account number: ");
        String originId = scanner.nextLine();
        BankAccount origin = bank.findAccount(originId);

        System.out.print("Destination account number: ");
        String destId = scanner.nextLine();
        BankAccount dest = bank.findAccount(destId);

        if (origin == null) {
            System.out.println("Error: Origin account not found.");
            return;
        }
        if (dest == null) {
            System.out.println("Error: Destination account not found");
            return;
        }

        System.out.print("Amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            origin.transferTo(dest, amount);
            System.out.println("Transfer successful!");
            System.out.println("New balance for " + origin.getAccountNumber() + ": " + origin.getBalance() + "€");
        } catch (InsufficientFundsException e) {
            System.out.println("TRANSFER FAILED: " + e.getMessage());
        }
    }

    private static void handleInterest() {
        System.out.print("Enter savings account number: ");
        BankAccount account = bank.findAccount(scanner.nextLine());
        try{
            ((SavingsAccount) account).applyMonthlyInterest();
            System.out.println("Interest applied");
        } catch (Exception e) {
            System.out.println("This account doesn't exist or it isn't a savings account!");
        }
    }

    private static void viewAccount() {
        System.out.print("Enter account number: ");
        BankAccount account = bank.findAccount(scanner.nextLine());
        if (account != null) {
            System.out.println(account.getAccountInfo());
        } else {
            System.out.println("Account not found");
        }
    }

    private static void viewAccountsOverX() {
        System.out.print("Filter accounts with balance greater than: ");
        double minBalance = scanner.nextDouble();
        List<BankAccount> filtered = bank.getAccountsWithBalanceGreaterThan(minBalance);
        filtered.forEach(a -> System.out.println("- " + a.getAccountNumber() + ": " + a.getBalance() + "€"));
    }

    private static void viewGlobalBalance() {
        System.out.println("\nGLOBAL BALANCE");
        System.out.println("Total balance in bank: " + String.format("%.2f", bank.getTotalBalance()) + "€");
    }
}