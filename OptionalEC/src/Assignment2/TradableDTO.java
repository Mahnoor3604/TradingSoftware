package Assignment2;

import Assignment1.Price;

public class TradableDTO {

    public String user;
    public String product;
    public Price price;
    public BookSide side;
    public String id;
    public int originalVolume;
    public int remainingVolume;
    public int cancelledVolume;
    public int filledVolume;

    public TradableDTO (String userIn, String productIn, Price priceIn, BookSide sideIn, String idIn, int originalVolumeIn, int remainingVolumeIn, int cancelledVolumeIn, int filledVolumeIn ) {
        user = userIn;
        product = productIn;
        price = priceIn;
        side = sideIn;
        originalVolume = originalVolumeIn;
        remainingVolume = remainingVolumeIn;
        filledVolume = filledVolumeIn;
        cancelledVolume = cancelledVolumeIn;
        id = idIn;
    }

    @Override
    public String toString() {
        return product + " quote from " + user + ": " + price + ", Orig Vol: " + originalVolume + ", Rem Vol: " + remainingVolume + ", Fill Vol: " + filledVolume + ", CXL Vol: " + cancelledVolume + ", ID: " + id;
    }


}
