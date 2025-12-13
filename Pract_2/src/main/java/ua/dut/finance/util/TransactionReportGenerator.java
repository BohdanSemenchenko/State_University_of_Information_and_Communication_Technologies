package ua.dut.finance.util;

import ua.dut.finance.Transaction;
import java.util.List;
import java.util.Map;

public abstract class TransactionReportGenerator {

    private TransactionReportGenerator() {}

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance + " грн");
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("\n--- ТОП-10 Найбільших витрат ---");
        for (Transaction t : topExpenses) {
            System.out.println(t.getDescription() + ": " + t.getAmount() + " грн");
        }
    }

    public static void printExpenseReportVisualized(Map<String, Double> expenses) {
        System.out.println("\n--- Звіт витрат по категоріях ---");
        for (Map.Entry<String, Double> entry : expenses.entrySet()) {
            String category = entry.getKey();
            double amount = entry.getValue();
            int starsCount = (int) (Math.abs(amount) / 1000);
            String stars = "*".repeat(starsCount);
            System.out.printf("%-25s %-10.2f %s%n", category, amount, stars);
        }
    }
}