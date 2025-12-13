package org.shop.model.util;

import com.github.javafaker.Faker;
import org.shop.model.entity.Clothing;
import org.shop.model.entity.Electronics;
import org.shop.model.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static List<Product> generateProducts(int count) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                products.add(Electronics.builder()
                        .id(UUID.randomUUID().toString())
                        .name(faker.commerce().productName())
                        .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                        .brand(faker.company().name())
                        .warrantyMonths(faker.number().numberBetween(12, 24))
                        .build());
            } else {
                products.add(Clothing.builder()
                        .id(UUID.randomUUID().toString())
                        .name(faker.commerce().productName())
                        .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                        .size(faker.options().option("S", "M", "L", "XL"))
                        .material(faker.commerce().material())
                        .build());
            }
        }
        return products;
    }
}