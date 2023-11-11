package com.example.jfk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionHandler {
    private static final String TRANSACTIONS_FILE_PATH = "../data/.txt";

    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(TRANSACTIONS_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String orderID = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    boolean paid = Boolean.parseBoolean(parts[2].trim());
                    Transaction transaction = new Transaction(orderID, amount, paid);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading transactions: " + e.getMessage());
        }

        return transactions;
    }

    public static void saveTransactions(List<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE_PATH))) {
            for (Transaction transaction : transactions) {
                String line = transaction.getOrderID() + "," + transaction.getAmount() + "," + transaction.isPaid();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving transactions: " + e.getMessage());
        }
    }

    public static void addTransaction(Transaction transaction) {
        List<Transaction> transactions = loadTransactions();
        transactions.add(transaction);
        saveTransactions(transactions);
    }

    public static void removeTransaction(String orderID) {
        List<Transaction> transactions = loadTransactions();
        transactions.removeIf(transaction -> transaction.getOrderID().equals(orderID));
        saveTransactions(transactions);
    }

    public static void updateTransaction(String orderID, Double newAmount, Boolean newPaid) {
        List<Transaction> transactions = loadTransactions();
        for (Transaction transaction : transactions) {
            if(newAmount==0){
                if (transaction.getOrderID().equals(orderID)) {
                    transaction.setPaid(newPaid);
                    break;
                }
            }else if (transaction.getOrderID().equals(orderID)) {
                transaction.setAmount(newAmount);
                transaction.setPaid(newPaid);
                break;
            }
        }
        saveTransactions(transactions);
    }
}
