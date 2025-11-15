package ua.dut.finance.util;

import ua.dut.finance.Transaction;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    private TransactionCSVReader() {}

    public static List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            URL url = new URL(filePath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        Transaction transaction = parseTransaction(line);
                        transactions.add(transaction);
                    } catch (Exception e) {
                        System.err.println("Помилка парсингу рядка: " + line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }


    public static Transaction parseTransaction(String line) {
        String[] values = line.split(",");
        if (values.length != 3) {
            throw new IllegalArgumentException("Неправильний формат рядка: " + line);
        }
        String date = values[0].trim();
        double amount = Double.parseDouble(values[1].trim());
        String description = values[2].trim();
        return new Transaction(date, amount, description);
    }
}