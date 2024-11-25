package Assignment2;

import Assignment1.Price;
import exceptions.*;

public class Quote {
    private String user;
    private String product;
    private QuoteSide buySide;
    private QuoteSide sellSide;

    public Quote (String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume, String userName)
            throws UsernameFormatIssueException, ProductNameFormatIssueException, InvalidVolumeException, InvalidPriceOperation, InvalidBookSideObject {
        setProduct(symbol);
        setUser(userName);
        buySide = new QuoteSide(user, product, buyPrice, BookSide.BUY, buyVolume);
        sellSide = new QuoteSide(user, product, sellPrice, BookSide.SELL, sellVolume);
    }

    private void setUser (String userIn)
            throws UsernameFormatIssueException {
        if (userIn.length() == 3 && userIn.matches("[a-zA-Z]+")) {
            user = userIn;
        } else {
            throw new UsernameFormatIssueException("Assignment3.User string does not match format conditions.");
        }
    }

    private void setProduct (String productIn)
            throws ProductNameFormatIssueException {
        if (productIn.length() >= 1 && productIn.length() <= 5 && productIn.matches("[a-zA-Z.]+")) {
            product = productIn;
        } else {
            throw new ProductNameFormatIssueException("Product string does not match format conditions.");
        }
    }


    public QuoteSide getQuoteSide(BookSide sideIn) {
        if (sideIn == BookSide.BUY) {
            return buySide;
        } else {
            return sellSide;
        }
    }

    public String getSymbol() {
        return product;
    }

    public String getUser() {
        return user;
    }
}
