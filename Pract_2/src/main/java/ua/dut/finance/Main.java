package ua.dut.finance;

import ua.dut.finance.util.TransactionAnalyzer;
import ua.dut.finance.util.TransactionCSVReader;
import ua.dut.finance.util.TransactionReportGenerator;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        if (transactions.isEmpty()) {
            System.out.println("Не вдалося завантажити транзакції.");
            return;
        }


        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        String monthYear = "12-2023";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);


        LocalDate start = LocalDate.of(2023, 12, 1);
        LocalDate end = LocalDate.of(2023, 12, 31);

        Optional<Transaction> highest = TransactionAnalyzer.findHighestExpenseInPeriod(transactions, start, end);
        Optional<Transaction> lowest = TransactionAnalyzer.findLowestExpenseInPeriod(transactions, start, end);
        TransactionReportGenerator.printPeriodicExpenseReport(start, end, highest, lowest);

        Map<String, Double> expensesByCategory = TransactionAnalyzer.calculateExpensesByCategory(transactions);
        TransactionReportGenerator.printExpensesByReport("Витрати по категоріях (всі)", expensesByCategory);

        Map<YearMonth, Double> expensesByMonth = TransactionAnalyzer.calculateExpensesByMonth(transactions);
        TransactionReportGenerator.printExpensesByReport("Витрати по місяцях", expensesByMonth);
    }
}