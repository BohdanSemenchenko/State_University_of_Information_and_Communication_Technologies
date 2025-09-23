package org.example.shop;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        Product product1 = new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics);
        Product product2 = new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном...", smartphones);
        Product product3 = new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories);

        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Зробити замовлення");
            System.out.println("0 - Вийти");

            System.out.print("Ваш вибір: ");
            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Будь ласка, введіть число!");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println(product1);
                    System.out.println(product2);
                    System.out.println(product3);
                    break;
                case 2:
                    System.out.print("Введіть ID товару: ");
                    int id;
                    if (scanner.hasNextInt()) {
                        id = scanner.nextInt();
                    } else {
                        System.out.println("Будь ласка, введіть число!");
                        scanner.next();
                        break;
                    }

                    if (id == 1) cart.addProduct(product1);
                    else if (id == 2) cart.addProduct(product2);
                    else if (id == 3) cart.addProduct(product3);
                    else System.out.println("Невірний ID");
                    break;
                case 3:
                    System.out.println(cart);
                    break;
                case 4:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній!");
                    } else {
                        Order order = new Order(cart);
                        System.out.println("Замовлення оформлено:\n" + order);
                        cart.clear();
                    }
                    break;
                case 0:
                    System.out.println("Дякуємо за використання магазину!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невідома опція!");
                    break;
            }
        }
    }
}
