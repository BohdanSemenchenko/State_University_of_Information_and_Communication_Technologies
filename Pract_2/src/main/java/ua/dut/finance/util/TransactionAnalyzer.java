package ua.dut.finance.util;

import ua.dut.finance.Transaction;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private TransactionAnalyzer() {}


    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }


    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

        for (Transaction transaction : transactions) {
            try {
                LocalDate date = LocalDate.parse(transaction.getDate(), DATE_FORMATTER);
                String transactionMonthYear = date.format(monthYearFormatter);
                if (transactionMonthYear.equals(monthYear)) {
                    count++;
                }
            } catch (Exception e) {
                System.err.println("Помилка парсингу дати: " + transaction.getDate());
            }
        }
        return count;
    }


    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }


    private static Stream<Transaction> filterByPeriod(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> {
                    try {
                        LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                        return !date.isBefore(start) && !date.isAfter(end);
                    } catch (Exception e) {
                        return false;
                    }
                });
    }


    public static Optional<Transaction> findHighestExpenseInPeriod(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return filterByPeriod(transactions, start, end)
                .filter(t -> t.getAmount() < 0)
                .min(Comparator.comparing(Transaction::getAmount));
    }


    public static Optional<Transaction> findLowestExpenseInPeriod(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return filterByPeriod(transactions, start, end)
                .filter(t -> t.getAmount() < 0)
                .max(Comparator.comparing(Transaction::getAmount));
    }


    public static Map<String, Double> calculateExpensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    public static Map<YearMonth, Double> calculateExpensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> YearMonth.parse(t.getDate(), DATE_FORMATTER),
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}