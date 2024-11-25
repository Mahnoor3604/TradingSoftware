package Assignment3;

import Assignment2.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public final class ProductManager {
    private static HashMap<String, ProductBook> productCollection = new HashMap<>();
    private static ProductManager instance;

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }
    private ProductManager(){
    }

    public void addProduct(String symbol) throws ProductNameFormatIssueException, InvalidBookSideObject, UsernameFormatIssueException, DataValidationException {
        if (symbol == null || symbol.length() < 1 || symbol.length() > 6 || !symbol.matches("[a-zA-Z.]+")) {
            throw new DataValidationException("Product cannot be added.");
        } else if (!productCollection.containsKey(symbol)) {
            ProductBook p = new ProductBook(symbol);
            productCollection.put(symbol, p);
        }
    }

    public ProductBook getProductBook(String symbol) throws DataValidationException {
        if (productCollection.containsKey(symbol)) {
            return productCollection.get(symbol);
        } else {
            throw new DataValidationException("Product does not exist.");
        }
    }

    public String getRandomProduct() {
        if (productCollection.isEmpty()) {
            return null;
        } else {
            ArrayList<String> products = new ArrayList<>(productCollection.keySet());
            Collections.shuffle(products);
            return products.getFirst();
        }
    }

    public TradableDTO addTradable(Tradable o) throws InvalidPriceOperation, DataValidationException {
        if (o == null) {
            throw new DataValidationException("Null tradable.");
        }
        ProductBook p = productCollection.get(o.getProduct());
        p.add(o);
        TradableDTO t = o.makeTradableDTO();
        UserManager.getInstance().addToUser(o.getUser(), t);
        return t;
    }

    public TradableDTO[] addQuote(Quote q) throws InvalidPriceOperation, DataValidationException{
        if (q == null) {
            throw new DataValidationException("Null quote.");
        }
        ProductBook p = productCollection.get(q.getSymbol());
        p.removeQuotesForUser(q.getUser());
        TradableDTO buy = addTradable(q.getQuoteSide(BookSide.BUY));
        TradableDTO sell = addTradable(q.getQuoteSide(BookSide.SELL));
        TradableDTO[] t = new TradableDTO[2];
        t[0] = buy;
        t[1] = sell;
        return t;
    }

    public TradableDTO cancel(TradableDTO o) throws DataValidationException, InvalidPriceOperation {
        if (o == null) {
            throw new DataValidationException("Null quote.");
        }
        ProductBook p = productCollection.get(o.product);
        TradableDTO t = p.cancel(o.side, o.id);
        if (t == null) {
            System.out.println("Failed attempt to cancel.");
        }

        return t;
    }

    public TradableDTO[] cancelQuote(String symbol, String user) throws DataValidationException, InvalidPriceOperation {
        if (symbol == null || user == null || !productCollection.containsKey(symbol)) {
            throw new DataValidationException("Invalid Parameters.");
        }
        ProductBook p = productCollection.get(symbol);
        TradableDTO[] tradables = p.removeQuotesForUser(user);
        return tradables;
    }

    public ArrayList<String> getProductList() {
        return new ArrayList<>(productCollection.keySet());
    }

    @Override
    public String toString() {
        String string = "";
        for (String product : productCollection.keySet()) {
            string += productCollection.get(product);
        }
        return string;
    }
}
