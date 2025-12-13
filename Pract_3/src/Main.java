import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        double num1;
        double num2 = 0;
        double result;
        String operation;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введіть перше число: ");
            num1 = scanner.nextDouble();

            System.out.print("Введіть операцію (+, -, *, /, sqrt): ");
            operation = scanner.next();

            if (!operation.equals("sqrt")) {
                System.out.print("Введіть друге число: ");
                num2 = scanner.nextDouble();
            }

            switch (operation) {
                case "+":
                    result = calculator.add(num1, num2);
                    System.out.println("Результат: " + result);
                    break;
                case "-":
                    result = calculator.subtract(num1, num2);
                    System.out.println("Результат: " + result);
                    break;
                case "*":
                    result = calculator.multiply(num1, num2);
                    System.out.println("Результат: " + result);
                    break;
                case "/":
                    result = calculator.divide(num1, num2);
                    System.out.println("Результат: " + result);
                    break;
                case "sqrt":
                    result = calculator.sqrt(num1);
                    System.out.println("Результат: " + result);
                    break;
                default:
                    System.out.println("Помилка: Невідома операція");
            }

        } catch (ArithmeticException | InvalidInputException e) {
            System.out.println("Помилка: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Помилка: Невірний формат вводу. Будь ласка, введіть число.");
        } catch (Exception e) {
            System.out.println("Помилка: Виникла непередбачена помилка -> " + e.getMessage());
        } finally {
            System.out.println("Роботу завершено");
        }
    }
}