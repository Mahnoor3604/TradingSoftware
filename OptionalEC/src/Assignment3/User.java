package Assignment3;

import Assignment2.TradableDTO;
import Assignment4.CurrentMarketObserver;
import Assignment4.CurrentMarketSide;
import exceptions.UsernameFormatIssueException;

import java.util.HashMap;

public class User implements CurrentMarketObserver {
    private String userId;
    private HashMap<String, TradableDTO> tradables;

    private HashMap<String, CurrentMarketSide[]> currentMarkets = new HashMap<>();

    public User(String userIdIn) throws UsernameFormatIssueException {
        setUserId(userIdIn);
        tradables = new HashMap<>();
    }

    private void setUserId(String userIdIn)
            throws UsernameFormatIssueException
    {
        if (userIdIn.length() == 3 && userIdIn.matches("[a-zA-Z]+")) {
            userId = userIdIn;
        } else {
            throw new UsernameFormatIssueException("Assignment3.User string does not match format conditions.");
        }
    }

    public String getUserId() {
        return userId;
    }

    public void addTradable (TradableDTO o) {
        if (o != null) {
            tradables.put(o.id, o);
        }
    }

    public boolean hasTradableWithRemainingQty() {
        for (String user: tradables.keySet()) {
            if (tradables.get(user).remainingVolume > 0) {
                return true;
            }
        }
        return false;
    }

    public TradableDTO getTradableWithRemainingQty() {
        for (String user: tradables.keySet()) {
            TradableDTO tradable = tradables.get(user);
            if (tradable.remainingVolume > 0) {
                return tradable;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String string = "User Id: " + userId +"\n";
        for (String id : tradables.keySet()) {
            TradableDTO tradable = tradables.get(id);
            string += "   " + "Product: " + tradable.product + ", Price: " + tradable.price + ", OriginalVolume: " + tradable.originalVolume + ", RemainingVolume: " + tradable.remainingVolume + ", CancelledVolume: " + tradable.cancelledVolume + ", FilledVolume: " + tradable.filledVolume + ", User: " + tradable.user + ", Side: " + tradable.side + ", Id: " + tradable.id + "\n";
        }
        return string;
    }

    @Override
    public void updateCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) {
        CurrentMarketSide[] buyAndSell = new CurrentMarketSide[2];
        buyAndSell[0] = buySide;
        buyAndSell[1] = sellSide;
        currentMarkets.put(symbol, buyAndSell);
    }
    public String getCurrentMarkets() {
        String string = "";
        for (String symbol : currentMarkets.keySet()) {
            string += symbol + "    ";
            CurrentMarketSide[] buyAndSell = currentMarkets.get(symbol);
            string += buyAndSell[0] + " - " + buyAndSell[1] + "\n";
        }
        return string;
    }

}
