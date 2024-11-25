package Assignment4;

import exceptions.InvalidPriceOperation;

public interface CurrentMarketObserver {
    void updateCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) throws InvalidPriceOperation;
}
