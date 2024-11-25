package Assignment2;

import Assignment1.Price;
import Assignment3.UserManager;
import Assignment4.CurrentMarketTracker;
import exceptions.DataValidationException;
import exceptions.InvalidBookSideObject;
import exceptions.InvalidPriceOperation;
import exceptions.ProductNameFormatIssueException;

public class ProductBook {

    private String product;
    private final ProductBookSide buySide;
    private final ProductBookSide sellSide;

    public ProductBook (String productIn)
            throws ProductNameFormatIssueException, InvalidBookSideObject
    {
        setProduct(productIn);
        buySide = new ProductBookSide(BookSide.BUY);
        sellSide = new ProductBookSide(BookSide.SELL);
    }

    private void setProduct (String productIn)
            throws ProductNameFormatIssueException
    {
        if (!productIn.isEmpty() && productIn.length() <= 5 && productIn.matches("[a-zA-Z.]+")) {
            product = productIn;
        } else {
            throw new ProductNameFormatIssueException("Product string does not match format conditions.");
        }
    }

    public TradableDTO add (Tradable t)
            throws InvalidPriceOperation, DataValidationException
    {
        TradableDTO tradable = null;
        if (t.getSide() == BookSide.BUY) {
            tradable = buySide.add(t);
        } else {
            tradable = sellSide.add(t);
        }
        tryTrade();
        updateMarket();
        return tradable;
    }

    public TradableDTO[] add(Quote qte) throws InvalidPriceOperation, DataValidationException {
        TradableDTO buy = buySide.add(qte.getQuoteSide(BookSide.BUY));
        TradableDTO sell = sellSide.add(qte.getQuoteSide(BookSide.SELL));

        tryTrade();

        TradableDTO[] tradables = new TradableDTO[2];
        tradables[0] = buy;
        tradables[1] = sell;
        return tradables;

    }

    public TradableDTO cancel(BookSide side, String orderId) throws DataValidationException, InvalidPriceOperation {
        TradableDTO tradable;
        if (side == BookSide.BUY) {
            tradable = buySide.cancel(orderId);
        } else {
            tradable = sellSide.cancel(orderId);
        }
        updateMarket();
        return tradable;
    }

    public void tryTrade() throws InvalidPriceOperation, DataValidationException {
        Price buyPrice = buySide.topOfBookPrice();
        Price sellPrice = sellSide.topOfBookPrice();
         while (buyPrice != null && sellPrice != null && buyPrice.greaterOrEqual(sellPrice)) {
            int buyVolume = buySide.topOfBookVolume();
            int sellVolume = sellSide.topOfBookVolume();
            int volumeToTrade = Math.min(buyVolume, sellVolume);

            sellSide.tradeOut(sellPrice, volumeToTrade);
            buySide.tradeOut(buyPrice, volumeToTrade);

            buyPrice = buySide.topOfBookPrice();
            sellPrice = sellSide.topOfBookPrice();
        }
    }

    public TradableDTO[] removeQuotesForUser(String userName) throws DataValidationException, InvalidPriceOperation {
        TradableDTO buy = buySide.removeQuotesForUser(userName);
        TradableDTO sell = sellSide.removeQuotesForUser(userName);

        TradableDTO[] tradables = new TradableDTO[2];
        tradables[0] = buy;
        tradables[1] = sell;
        UserManager.getInstance().getUser(userName).addTradable(buy);
        UserManager.getInstance().getUser(userName).addTradable(sell);
        updateMarket();
        return tradables;
    }

    private void updateMarket () throws InvalidPriceOperation {
        Price topPriceBS = buySide.topOfBookPrice();
        Price topPriceSS = sellSide.topOfBookPrice();
        int topVolBS = buySide.topOfBookVolume();
        int topVolSS = sellSide.topOfBookVolume();
        CurrentMarketTracker.getInstance().updateMarket(product, topPriceBS, topVolBS, topPriceSS, topVolSS);
    }

    public String toString() {
        return "Product Book: " + product +"\n" + buySide  + sellSide;
    }

}
