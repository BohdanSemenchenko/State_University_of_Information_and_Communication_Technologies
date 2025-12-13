package ua.dut.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Transaction {
    private String date;
    private double amount;
    private String description;
}