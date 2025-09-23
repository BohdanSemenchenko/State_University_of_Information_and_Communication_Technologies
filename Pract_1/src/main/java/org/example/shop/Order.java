package org.example.shop;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private List<Product> products;
    private double totalPrice;
    private String status;

    public Order(Cart cart) {
        this.products = new ArrayList<>(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Замовлення:\n");
        for (Product p : products) {
            sb.append(p).append("\n");
        }
        sb.append("Загальна сума: ").append(totalPrice).append(" грн\n");
        sb.append("Статус: ").append(status);
        return sb.toString();
    }
}