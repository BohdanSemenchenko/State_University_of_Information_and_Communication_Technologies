package ua.dut.finance.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dut.finance.Transaction;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class TransactionAnalyzerTest {

    @Test
    void testCalculateTotalBalance() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2023", 100.0, "Income"),
                new Transaction("02-01-2023", -50.0, "Expense")
        );
        double result = TransactionAnalyzer.calculateTotalBalance(transactions);
        Assertions.assertEquals(50.0, result);
    }

    @Test
    void testCountTransactionsByMonth() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-02-2023", -50.0, "A"),
                new Transaction("05-02-2023", -20.0, "B"),
                new Transaction("01-03-2023", -10.0, "C")
        );
        int count = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        Assertions.assertEquals(2, count);
    }

    @Test
    void testFindTopExpenses() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2023", -100.0, "A"),
                new Transaction("01-01-2023", -1000.0, "B"),
                new Transaction("01-01-2023", -50.0, "C")
        );
        List<Transaction> top = TransactionAnalyzer.findTopExpenses(transactions);
        Assertions.assertEquals(3, top.size());
        Assertions.assertEquals(-1000.0, top.get(0).getAmount());
    }

    @Test
    void testFindHighestExpense() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2023", -100.0, "A"),
                new Transaction("05-01-2023", -5000.0, "B"),
                new Transaction("10-01-2023", -10.0, "C")
        );
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 31);
        Transaction result = TransactionAnalyzer.findHighestExpense(transactions, start, end);
        Assertions.assertEquals(-5000.0, result.getAmount());
    }
}