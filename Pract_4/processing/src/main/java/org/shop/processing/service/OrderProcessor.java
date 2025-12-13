package org.shop.processing.service;

import org.shop.model.entity.Product;

public class OrderProcessor<T extends Product> {
    private final T product;

    public OrderProcessor(T product) {
        this.product = product;
    }

    public void processOrder() {
        String threadName = Thread.currentThread().getName();
        try {
            System.out.printf("[%s] Обробка замовлення: %s | Ціна: %.2f грн%n",
                    threadName, product.getName(), product.getPrice());
            Thread.sleep(500);
            System.out.printf("[%s] Замовлення успішно оброблено: %s%n", threadName, product.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Потік було перервано під час обробки: " + product.getName());
        }
    }
}