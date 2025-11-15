package ua.dut.finance.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dut.finance.Transaction;

class TransactionCSVReaderTest {


    @Test
    void testParseTransaction_Success() {

        String line = "05-12-2023, -450, Сільпо";
        Transaction t = TransactionCSVReader.parseTransaction(line);

        Assertions.assertEquals("05-12-2023", t.getDate());
        Assertions.assertEquals(-450.0, t.getAmount());
        Assertions.assertEquals("Сільпо", t.getDescription());
    }

    @Test
    void testParseTransaction_WithSpaces() {
        String line = " 10-12-2023 , 8000.0 , Зарплата ";
        Transaction t = TransactionCSVReader.parseTransaction(line);

        Assertions.assertEquals("10-12-2023", t.getDate());
        Assertions.assertEquals(8000.0, t.getAmount());
        Assertions.assertEquals("Зарплата", t.getDescription());
    }

    @Test
    void testParseTransaction_Fail_BadAmount() {
        String line = "05-12-2023, -450гривень, Сільпо";

        Assertions.assertThrows(NumberFormatException.class, () -> {
            TransactionCSVReader.parseTransaction(line);
        });
    }

    @Test
    void testParseTransaction_Fail_WrongColumnCount() {
        String line = "05-12-2023, -450";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TransactionCSVReader.parseTransaction(line);
        });
    }
}