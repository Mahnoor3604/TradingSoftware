package Assignment1;

import java.util.HashMap;

public abstract class PriceFactory {

    private static final HashMap<Integer, Price> prices = new HashMap<Integer, Price>();

    public static Price makePrice (int value) {
        if (prices.containsKey(value)) {
            return prices.get(value);
        } else {
            Price price = new Price(value);
            prices.put(value, price);
            return price;
        }
    }
    public static Price makePrice(String stringValueIn) {
        stringValueIn = stringValueIn.replaceAll("[,$]", "");
        double value = Double.parseDouble(stringValueIn) * 100;
        int cents = (int) value;

        if (prices.containsKey(cents)) {
            return prices.get(cents);
        } else {
            Price price = new Price(cents);
            prices.put(cents, price);
            return price;
        }
    }
}
