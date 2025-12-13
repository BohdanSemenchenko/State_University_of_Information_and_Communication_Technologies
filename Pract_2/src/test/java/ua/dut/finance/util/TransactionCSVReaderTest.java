package ua.dut.finance.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dut.finance.Transaction;
import java.util.List;

class TransactionCSVReaderTest {

    @Test
    void testReadTransactions() {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        Assertions.assertNotNull(transactions);
        Assertions.assertFalse(transactions.isEmpty());

        Transaction first = transactions.get(0);
        Assertions.assertNotNull(first.getDate());
        Assertions.assertNotNull(first.getDescription());
    }
}