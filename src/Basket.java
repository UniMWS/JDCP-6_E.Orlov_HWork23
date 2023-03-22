import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String[] products;
    private int[] prices;
    private static int[] amounts;
    private static int sumPrices = 0;

    public Basket(String[] products, int[] prices) {
//конструктор, принимающий массив цен и названий продуктов;
        this.products = products;
        this.prices = prices;
        this.amounts = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
//метод добавления amount штук продукта номер productNum в корзину
        int currentPrice = prices[productNum];  //цена этого продукта
        amounts[productNum] += amount;
        sumPrices += currentPrice * amount;
    }

    public void printCart() {
//метод вывода на экран покупательской корзины.
        for (int i = 0; i < amounts.length; i++) {
            if (!(amounts[i] == 0)) {
                System.out.printf("%s %d шт. по %d руб./шт. - %d руб в сумме; \n",
                        products[i], amounts[i], prices[i], (amounts[i] * prices[i]));
            }
        }
        System.out.printf("Итого: %d руб. \n", sumPrices);
    }

    public void saveTxt(File textFile) throws IOException {
//метод сохранения корзины в текстовый файл; использовать встроенные сериализаторы нельзя; //А как это?
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int amount : amounts)
                out.print(amount + " ");
            out.print("\n" + sumPrices);
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
//статический(!) метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
//чтение построчно
            String[] interim = (br.readLine()).split(" ");     // первая строка файла
            for (int i = 0; i < amounts.length; i++) {
                amounts[i] = Integer.parseInt(interim[i]);
            }
            sumPrices = Integer.parseInt(br.readLine());            // вторая строка файла
//            System.out.println(sumPrices);                        // контроль
//            System.out.println(Arrays.toString(amounts));         // контроль
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (textFile.exists())                                      // если файл существует
            System.out.println("Корзина уже существует и будет использована:");
        else
            System.out.print("Корзина пуста. ");
        return null;
    }

    public void saveBin(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
// запишем экземпляр класса в файл
            oos.writeObject(this);
            oos.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //я не понимаю как это вообще возможно без теневых копий самого себя зашить,
    // а потом безболезненно обратно "вышить крестиком"?
    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
// откроем входной поток для чтения файла
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
// десериализуем объект и скастим его в класс
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("для контроля \"loadFromBinFile\" " + basket + "\n");// контроль amounts
        return basket;
    }
    // мне нужна помощь: как это понять и где что читать? куда копать?
    // отсылать к "материалам" урока бессмысленно - вот он здесь результат.

    @Override
    public String toString() {
        return "Basket:" +
                "\nproducts=" + Arrays.toString(products) +
                "\nprices=" + Arrays.toString(prices) +
                "\namounts=" + Arrays.toString(amounts) +
                "\nsumPrices=" + sumPrices;
    }
}
