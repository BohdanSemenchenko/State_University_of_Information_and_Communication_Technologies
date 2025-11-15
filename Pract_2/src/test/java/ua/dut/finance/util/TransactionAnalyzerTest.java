package ua.dut.finance.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dut.finance.Transaction;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TransactionAnalyzerTest {

    @Test
    public void testCalculateTotalBalance() {
        [cite_start]// [cite: 209-210]
        Transaction t1 = new Transaction("2023-01-01", 100.0, "Дохід");
        Transaction t2 = new Transaction("2023-01-02", -50.0, "Витрата");
        Transaction t3 = new Transaction("2023-01-03", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(t1, t2, t3);

        double result = TransactionAnalyzer.calculateTotalBalance(transactions); //


        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    @Test
    public void testCountTransactionsByMonth() {
        [cite_start]// [cite: 255-257]
        Transaction t1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction t2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction t3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(t1, t2, t3);


        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");
        int countJan = TransactionAnalyzer.countTransactionsByMonth(transactions, "01-2023");

        [cite_start]// [cite: 261-264]
        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
        Assertions.assertEquals(0, countJan, "Кількість транзакцій за січень має бути 0");
    }


    @Test
    public void testFindTopExpenses() {
        Transaction t1 = new Transaction("01-01-2023", 1000.0, "ЗП");
        Transaction t2 = new Transaction("02-01-2023", -500.0, "Кафе");
        Transaction t3 = new Transaction("03-01-2023", -2000.0, "Оренда");
        Transaction t4 = new Transaction("04-01-2023", -10.0, "Дрібниці");
        Transaction t5 = new Transaction("05-01-2023", -1000.0, "Комуналка");
        List<Transaction> transactions = Arrays.asList(t1, t2, t3, t4, t5);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(4, topExpenses.size());

        Assertions.assertEquals(-2000.0, topExpenses.get(0).getAmount());
        Assertions.assertEquals(-1000.0, topExpenses.get(1).getAmount());
        Assertions.assertEquals(-500.0, topExpenses.get(2).getAmount());
        Assertions.assertEquals(-10.0, topExpenses.get(3).getAmount());
    }

    @Test
    public void testFindHighestAndLowestExpenseInPeriod() {
        Transaction t1 = new Transaction("01-01-2023", -100.0, "Кава");
        Transaction t2 = new Transaction("15-01-2023", -5000.0, "Техніка");
        Transaction t3 = new Transaction("31-01-2023", -50.0, "Жуйка");
        Transaction t4 = new Transaction("01-02-2023", -1000.0, "Одяг");
        List<Transaction> transactions = Arrays.asList(t1, t2, t3, t4);

        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 31);

        Optional<Transaction> highest = TransactionAnalyzer.findHighestExpenseInPeriod(transactions, start, end);
        Optional<Transaction> lowest = TransactionAnalyzer.findLowestExpenseInPeriod(transactions, start, end);

        Assertions.assertTrue(highest.isPresent());
        Assertions.assertTrue(lowest.isPresent());
        Assertions.assertEquals(-5000.0, highest.get().getAmount());
        Assertions.assertEquals(-50.0, lowest.get().getAmount());
    }
}