package ua.dut.finance;

import ua.dut.finance.util.TransactionAnalyzer;
import ua.dut.finance.util.TransactionCSVReader;
import ua.dut.finance.util.TransactionReportGenerator;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        String month = "01-2024";
        int count = TransactionAnalyzer.countTransactionsByMonth(transactions, month);
        TransactionReportGenerator.printTransactionsCountByMonth(month, count);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        LocalDate start = LocalDate.of(2023, 12, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);

        Transaction maxExp = TransactionAnalyzer.findHighestExpense(transactions, start, end);
        Transaction minExp = TransactionAnalyzer.findLowestExpense(transactions, start, end);

        System.out.println("\n--- Екстремуми витрат (12-2023 до 01-2024) ---");
        System.out.println("Найбільша: " + (maxExp != null ? maxExp : "N/A"));
        System.out.println("Найменша: " + (minExp != null ? minExp : "N/A"));

        Map<String, Double> expensesByCategory = TransactionAnalyzer.getExpensesByCategory(transactions);
        TransactionReportGenerator.printExpenseReportVisualized(expensesByCategory);
    }
}