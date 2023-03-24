import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static File textFileMain = new File("basket.txt");
    public static String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
    public static int[] prices = {50, 14, 80};

    public static void main(String[] args) throws IOException {
        System.out.println("JDCP-6 + Евгений Орлов + ДЗ-23 + " +
                "Потоки ввода-вывода. Работа с файлами. Сериализация");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Задача 1\n");
        Basket basket;
        if (textFileMain.exists()) {// проверка существования файла
            System.out.println("Корзина уже существует и будет использована:");
            basket = Basket.loadFromTxtFile(textFileMain);//загрузка корзины из файла
            basket.printCart();
        } else {
            System.out.print("Корзина пуста. ");
            basket = new Basket(products, prices);
        }
        groceryList(basket);// список продуктов
        while (true) {
            System.out.println("\nВыберите товар и количество через пробел " +
                    "или введите \"end\" для выхода:");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println(String.format("Надо вводить два числа \"Номер Количество\"" +
                        " через пробел, а не '%s'", input));
                continue;
            }
            try {
                if (Integer.parseInt(parts[0]) < 0 || Integer.parseInt(parts[0]) > products.length) {
                    System.out.println(String.format("Надо вводить номер товара от '1' до '%s'",
                            (products.length)));
                } else if (Integer.parseInt(parts[1]) >= 0) {
                    int productNumber = Integer.parseInt(parts[0]) - 1;//номер продукта
                    int productCount = Integer.parseInt(parts[1]);//штук продукта
                    basket.addToCart(productNumber, productCount);// ушло в корзину
                    basket.saveTxt(textFileMain);// корзина ушла в файл
                } else
                    System.out.println(String.format("Количество товара не может быть отрицательным" +
                            " '%s'", Integer.parseInt(parts[1])));
            } catch (NumberFormatException e) {
                System.out.println(String.format("Ошибка ввода, вы ввели не два числа через пробел:" +
                        " '%s'", input));
            }
        }
        System.out.println("Ваша корзина:");
        basket.printCart();// печать корзины
    }

    private static void groceryList(Basket basket) {
        System.out.println("Список доступных для покупки продуктов:");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%d. %s %d руб/шт \n", i + 1, products[i], prices[i]);
        }
    }
}