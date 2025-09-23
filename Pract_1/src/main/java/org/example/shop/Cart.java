package org.example.shop;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        System.out.println(product.getName() + " додано до кошика.");
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {
        if (products.isEmpty()) return "Кошик порожній.";
        StringBuilder sb = new StringBuilder("Ваш кошик:\n");
        for (Product p : products) {
            sb.append(p).append("\n");
        }
        sb.append("Загальна сума: ").append(getTotalPrice()).append(" грн");
        return sb.toString();
    }
}