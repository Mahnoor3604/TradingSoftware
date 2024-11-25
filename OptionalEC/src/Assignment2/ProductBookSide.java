package Assignment2;

import Assignment1.Price;
import Assignment3.UserManager;
import exceptions.DataValidationException;
import exceptions.InvalidBookSideObject;
import exceptions.InvalidPriceOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProductBookSide {
    private BookSide side;
    private final HashMap<Price, ArrayList<Tradable>> bookEntries;
    private final ArrayList<Price> prices;


    public ProductBookSide (BookSide sideIn) throws InvalidBookSideObject {
        bookEntries = new HashMap<>();
        prices = new ArrayList<>();
        setSide(sideIn);
    }

    private void setSide (BookSide sideIn) throws InvalidBookSideObject {
        if (sideIn != null) {
            side = sideIn;
        } else {
            throw new InvalidBookSideObject("Assignment2.BookSide object cannot be null.");
        }
    }

    public TradableDTO add(Tradable o) {
        Price price = o.getPrice();
        if (bookEntries.containsKey(price)) {
            bookEntries.get(price).add(o);
        } else {
            bookEntries.put(price, new ArrayList<Tradable>());
            bookEntries.get(price).add(o);

            prices.add(price);

        }
        System.out.println("ADD: " + side + ": " + o + "\n");
        return o.makeTradableDTO();
    }

    public TradableDTO cancel(String tradableId) throws DataValidationException {
        for (Price price: bookEntries.keySet()) {
            for (Tradable tradable: bookEntries.get(price)) {
                if (tradable.getId().equals(tradableId)) {
                    bookEntries.get(price).remove(tradable);
                    tradable.setCancelledVolume(tradable.getCancelledVolume() + tradable.getRemainingVolume());
                    tradable.setRemainingVolume(0);
                    if (bookEntries.get(price).isEmpty()) {
                        bookEntries.remove(price);
                        prices.remove(price);
                    }
                    System.out.println("CANCEL: " + side + ": " + tradable.getId() + " Cxl Qty: " + tradable.getCancelledVolume() + "\n");
                    UserManager.getInstance().addToUser(tradable.getUser(), tradable.makeTradableDTO());
                    return tradable.makeTradableDTO();
                }
            }
        }
        return null;
    }

    public TradableDTO removeQuotesForUser(String userName) throws DataValidationException {
        TradableDTO t = null;
        for (Price price: bookEntries.keySet()) {
            for (Tradable tradable : bookEntries.get(price)) {
                if (tradable.getUser().equals(userName)) {
                    return cancel(tradable.getId());
                }
            }
        }
        return t;
    }

    public Price topOfBookPrice() throws InvalidPriceOperation {
        if (bookEntries.isEmpty()) {
            return null;
        }
        Collections.sort(prices);
        if (side == BookSide.BUY) {
            Collections.reverse(prices);
        }
        return prices.get(0);
    }

    public int topOfBookVolume() throws InvalidPriceOperation {
        int total = 0;
        if (prices.isEmpty()) {
            return total;
        }
        Collections.sort(prices);
        if (side == BookSide.BUY) {
            Collections.reverse(prices);
        }
        for (Tradable tradable : bookEntries.get(prices.getFirst())) {
            total += tradable.getRemainingVolume();
        }
        return total;
    }

    public void tradeOut(Price price, int vol) throws DataValidationException {
        int remainingVolume = vol;
        ArrayList<Tradable> tradables = bookEntries.get(price);
        while (remainingVolume > 0) {
            if (!tradables.isEmpty()) {
                Tradable tradable = tradables.get(0);
                if (tradable.getRemainingVolume() <= remainingVolume) {
                    bookEntries.get(price).remove(tradable);
                    int remainingVol = tradable.getRemainingVolume();
                    tradable.setFilledVolume(tradable.getFilledVolume() + remainingVol);
                    tradable.setRemainingVolume(0);
                    remainingVolume -= remainingVol;
                    System.out.println("    FULL FILL: (" + side + " " + remainingVol + ") " + tradable);
                    UserManager.getInstance().addToUser(tradable.getUser(), tradable.makeTradableDTO());
                } else {
                    tradable.setFilledVolume(tradable.getFilledVolume() + remainingVolume);
                    tradable.setRemainingVolume(tradable.getRemainingVolume() - remainingVolume);
                    System.out.println("    PARTIAL FILL: (" + side + " " + remainingVolume + ") " + tradable);
                    remainingVolume = 0;
                    UserManager.getInstance().addToUser(tradable.getUser(), tradable.makeTradableDTO());
                }
            }
        }
        if (tradables.isEmpty()) {
            bookEntries.remove(price);
            prices.remove(price);
        }
    }


    public String toString() {
        Collections.sort(prices);
        if (side == BookSide.SELL) {
            String out = "";

            for (Price price: prices) {
                out += "    Price: " + price + "\n";
                for (Tradable entry: bookEntries.get(price)) {
                    out += "       " + entry + "\n";
                }
            }
            return "Side: SELL\n" + out;
        } else {
            Collections.reverse(prices);
            String out = "";

            for (Price price: prices) {
                out += "    Price: " + price + "\n";
                for (Tradable entry: bookEntries.get(price)) {
                    out += "       " + entry + "\n";
                }
            }
            return "Side: BUY\n" + out;
        }

    }
}
