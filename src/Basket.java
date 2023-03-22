import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String[] productsBasket;
    private static int[] pricesBasket;
    private static int[] amountsBasket;
    private static int summaryBasket;

    public Basket(String[] productsBasket, int[] pricesBasket) {
//конструктор, принимающий массив цен и названий продуктов;
        this.productsBasket = productsBasket;
        this.pricesBasket = pricesBasket;
        this.amountsBasket = new int[productsBasket.length];
        this.summaryBasket = 0;
    }

    public void addToCart(int productNum, int amount) {
//метод добавления amount штук продукта номер productNum в корзину
        int currentPrice = pricesBasket[productNum];  //цена этого продукта
        amountsBasket[productNum] += amount;
        summaryBasket += currentPrice * amount;
    }

    public void printCart() {
//метод вывода на экран покупательской корзины.
        for (int i = 0; i < amountsBasket.length; i++) {
            if (!(amountsBasket[i] == 0)) {
                System.out.printf("%s %d шт. по %d руб./шт. - %d руб в сумме; \n",
                        productsBasket[i], amountsBasket[i], pricesBasket[i],
                        (amountsBasket[i] * pricesBasket[i]));
            }
        }
        System.out.printf("Итого: %d руб. \n", summaryBasket);
    }

    public void saveTxt(File textFile) {
//метод сохранения корзины в текстовый файл; использовать встроенные сериализаторы нельзя; //А как это?
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : productsBasket)
                out.print(product + " ");
            out.println();
            for (int price : pricesBasket)
                out.print(price + " ");
            out.println();
            for (int amount : amountsBasket)
                out.print(amount + " ");
            out.print("\n" + summaryBasket);
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
//статический(!) метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
//чтение построчно
            String[] interim1 = (br.readLine()).split(" ");     // первая строка файла
            for (int i = 0; i < productsBasket.length; i++) {
                productsBasket[i] = interim1[i];
            }
            String[] interim2 = (br.readLine()).split(" ");     // вторая строка файла
            for (int i = 0; i < pricesBasket.length; i++) {
                pricesBasket[i] = Integer.parseInt(interim2[i]);
            }
            String[] interim3 = (br.readLine()).split(" ");     // третья строка файла
            for (int i = 0; i < amountsBasket.length; i++) {
                amountsBasket[i] = Integer.parseInt(interim3[i]);
            }
            summaryBasket = Integer.parseInt(br.readLine());        // четвертая строка файла
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (textFile.exists())                                      // если файл существует
            System.out.println("Корзина уже существует и будет использована:");
        return null;
    }

    @Override
    public String toString() {
        return "Basket:" +
                "\nproductsBasket=" + Arrays.toString(productsBasket) +
                "\npricesBasket=" + Arrays.toString(pricesBasket) +
                "\namounts=" + Arrays.toString(amountsBasket) +
                "\nsummary=" + summaryBasket;
    }
}
