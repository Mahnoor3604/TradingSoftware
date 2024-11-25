package Assignment4;


import exceptions.InvalidPriceOperation;

import java.util.ArrayList;
import java.util.HashMap;

public final class CurrentMarketPublisher {

    private HashMap<String, ArrayList<CurrentMarketObserver>> filters = new HashMap<>();

    private static CurrentMarketPublisher instance;

    public static CurrentMarketPublisher getInstance() {
        if (instance == null) {
            instance = new CurrentMarketPublisher();
        }
        return instance;
    }

    private CurrentMarketPublisher() {
    }

    public void subscribeCurrentMarket(String symbol, CurrentMarketObserver cmo) {
        if (!filters.containsKey(symbol)) {
            filters.put(symbol, new ArrayList<CurrentMarketObserver>());
        }
        filters.get(symbol).add(cmo);
    }

    public void unSubscribeCurrentMarket(String symbol, CurrentMarketObserver cmo) {
        if (filters.containsKey(symbol)) {
            filters.get(symbol).remove(cmo);
        }
    }

    public void acceptCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) throws InvalidPriceOperation {
        if (filters.containsKey(symbol)) {
            ArrayList<CurrentMarketObserver> temp = filters.get(symbol);
            for (CurrentMarketObserver cmo : temp) {
                cmo.updateCurrentMarket(symbol, buySide, sellSide);
            }
        }
    }
}
