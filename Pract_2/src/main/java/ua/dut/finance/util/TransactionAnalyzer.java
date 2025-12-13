package ua.dut.finance.util;

import ua.dut.finance.Transaction;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private TransactionAnalyzer() {}

    public static double calculateTotalBalance(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction t : transactions) {
            try {
                LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                String tMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                if (tMonthYear.equals(monthYear)) {
                    count++;
                }
            } catch (Exception ignored) {
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

    public static Transaction findHighestExpense(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    try {
                        LocalDate d = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                        return !d.isBefore(start) && !d.isAfter(end);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    public static Transaction findLowestExpense(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    try {
                        LocalDate d = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                        return !d.isBefore(start) && !d.isAfter(end);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    public static Map<String, Double> getExpensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}