package org.shop.app;

import org.shop.model.entity.Clothing;
import org.shop.model.entity.Electronics;
import org.shop.model.entity.Product;
import org.shop.model.util.DataGenerator;
import org.shop.processing.service.OrderProcessor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Старт системи обробки замовлень ===\n");

        List<Product> products = DataGenerator.generateProducts(10);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("--- Фільтрація та запуск обробки електроніки ---");
        products.stream()
                .filter(p -> p instanceof Electronics)
                .map(p -> (Electronics) p)
                .forEach(electronics -> {
                    OrderProcessor<Electronics> processor = new OrderProcessor<>(electronics);
                    executor.submit(processor::processOrder);
                });

        System.out.println("--- Фільтрація та запуск обробки одягу ---");
        products.stream()
                .filter(p -> p instanceof Clothing)
                .map(p -> (Clothing) p)
                .forEach(clothing -> {
                    OrderProcessor<Clothing> processor = new OrderProcessor<>(clothing);
                    executor.submit(processor::processOrder);
                });

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("\n=== Всі завдання додано в чергу ===");
    }
}