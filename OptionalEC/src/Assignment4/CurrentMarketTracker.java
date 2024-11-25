package Assignment4;

import Assignment1.Price;
import Assignment1.PriceFactory;
import exceptions.InvalidPriceOperation;


public final class CurrentMarketTracker {

    private static CurrentMarketTracker instance;

    public static CurrentMarketTracker getInstance() {
        if (instance == null) {
            instance = new CurrentMarketTracker();
        }
        return instance;
    }

    private CurrentMarketTracker() {
    }

    public void updateMarket(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) throws InvalidPriceOperation {
        Price width;
        CurrentMarketSide buySide = new CurrentMarketSide(buyPrice, buyVolume);
        CurrentMarketSide sellSide = new CurrentMarketSide(sellPrice, sellVolume);
        if (buyPrice != null && sellPrice != null) {
            if (buyPrice.greaterOrEqual(sellPrice)) {
                width = buyPrice.subtract(sellPrice);
            } else {
                width = sellPrice.subtract(buyPrice);
            }
        } else {
            width = PriceFactory.makePrice(0);
        }
        System.out.println("*********** Current Market ***********\n* " + symbol + "    " + buySide + " - " + sellSide + " " + "[" + width + "]\n" + "**************************************");
        CurrentMarketPublisher.getInstance().acceptCurrentMarket(symbol, buySide, sellSide);
    }
}
