package Assignment2;

import Assignment1.Price;
import exceptions.*;

public class QuoteSide implements Tradable {

    private String user;
    private String product;
    private Price price;
    private BookSide side;
    private String id;
    private int originalVolume;
    private int remainingVolume;
    private int cancelledVolume;
    private int filledVolume;


    public QuoteSide (String userIn, String productIn, Price priceIn, BookSide sideIn, int originalVolumeIn)
            throws UsernameFormatIssueException, ProductNameFormatIssueException, InvalidVolumeException, InvalidPriceOperation, InvalidBookSideObject {
        setUser(userIn);
        setProduct(productIn);
        setPrice(priceIn);
        setSide(sideIn);
        setOriginalVolume(originalVolumeIn);
        setRemainingVolume(originalVolumeIn);
        setFilledVolume(0);
        setCancelledVolume(0);
        setId();
    }

    private void setId () {
        id = user + product + price + System.nanoTime();
    }

    private void setUser (String userIn) throws UsernameFormatIssueException {
        if (userIn.length() == 3 && userIn.matches("[a-zA-Z]+")) {
            user = userIn;
        } else {
            throw new UsernameFormatIssueException("Assignment3.User string does not match format conditions.");
        }
    }

    private void setProduct (String productIn) throws ProductNameFormatIssueException {
        if (productIn.length() >= 1 && productIn.length() <= 5 && productIn.matches("[a-zA-Z.]+")) {
            product = productIn;
        } else {
            throw new ProductNameFormatIssueException("Product string does not match format conditions.");
        }
    }

    private void setPrice (Price priceIn) throws InvalidPriceOperation {
        if (priceIn != null) {
            price = priceIn;
        } else {
            throw new InvalidPriceOperation("Assignment1.Price object cannot be null.");
        }
    }

    private void setSide (BookSide sideIn) throws InvalidBookSideObject {
        if (sideIn != null) {
            side = sideIn;
        } else {
            throw new InvalidBookSideObject("Assignment2.BookSide object cannot be null.");
        }
    }

    private void setOriginalVolume (int originalVolumeIn) throws InvalidVolumeException {
        if (originalVolumeIn > 0 && originalVolumeIn < 10000) {
            originalVolume = originalVolumeIn;
        } else {
            throw new InvalidVolumeException("Volume cannot be less than 0 or more that 10,000.");        }
    }

    public void setCancelledVolume (int newVol) {
        cancelledVolume = newVol;
    }

    @Override
    public int getRemainingVolume() {
        return remainingVolume;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getCancelledVolume() {
        return cancelledVolume;
    }

    @Override
    public void setRemainingVolume(int newVol) {
        remainingVolume = newVol;
    }

    @Override
    public TradableDTO makeTradableDTO() {
        return new TradableDTO(getUser(), getProduct(), getPrice(), getSide(), getId(), getOriginalVolume(), getRemainingVolume(), getCancelledVolume(), getFilledVolume());
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public void setFilledVolume(int newVol) {
        filledVolume = newVol;
    }

    @Override
    public int getFilledVolume() {
        return filledVolume;
    }

    @Override
    public BookSide getSide() {
        return side;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getProduct() {
        return product;
    }

    @Override
    public int getOriginalVolume() {
        return originalVolume;
    }

    public String toString() {
        return product + " quote from " + user + ": " + price + ", Orig Vol: " + originalVolume + ", Rem Vol: " + remainingVolume + ", Fill Vol: " + filledVolume + ", CXL Vol: " + cancelledVolume + ", ID: " + id;
    }
}
