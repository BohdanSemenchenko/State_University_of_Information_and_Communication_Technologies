package ua.dut.finance.util;

import ua.dut.finance.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TransactionReportGenerator {

    private static final int VISUALIZATION_UNIT = 1000;

    private TransactionReportGenerator() {}


    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }


    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }


    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("\n--- 10 найбільших витрат ---");
        if (topExpenses.isEmpty()) {
            System.out.println("Витрат не знайдено.");
            return;
        }
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }


    public static void printPeriodicExpenseReport(LocalDate start, LocalDate end, Optional<Transaction> highest, Optional<Transaction> lowest) {
        System.out.println("\n--- Аналіз витрат за період " + start + " до " + end + " ---");
        highest.ifPresentOrElse(
                t -> System.out.println("Найбільша витрата: " + t.getDescription() + " (" + t.getAmount() + ")"),
                () -> System.out.println("Найбільшу витрату не знайдено.")
        );
        lowest.ifPresentOrElse(
                t -> System.out.println("Найменша витрата: " + t.getDescription() + " (" + t.getAmount() + ")"),
                () -> System.out.println("Найменшу витрату не знайдено.")
        );
    }


    public static void printExpensesByReport(String title, Map<?, Double> expenseMap) {
        System.out.println("\n--- " + title + " ---");
        if (expenseMap.isEmpty()) {
            System.out.println("Дані відсутні.");
            return;
        }

        expenseMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    double amount = entry.getValue();
                    String visualization = generateVisualization(amount);
                    System.out.printf("%-25s | %.2f грн %s\n", entry.getKey(), amount, visualization);
                });
    }


    private static String generateVisualization(double amount) {
        // [cite: 340]
        int symbols = (int) (Math.abs(amount) / VISUALIZATION_UNIT);
        if (symbols == 0 && amount < 0) {
            return ".";
        }
        return "*".repeat(symbols);
    }
}