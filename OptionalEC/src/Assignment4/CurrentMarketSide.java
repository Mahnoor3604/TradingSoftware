package Assignment4;

import Assignment1.*;

public class CurrentMarketSide {
    private Price price;
    private int volume;

    public CurrentMarketSide (Price priceIn, int volumeIn) {
        setPrice(priceIn);
        setVolume(volumeIn);
    }

    private void setPrice (Price priceIn) {
        price = priceIn;
    }

    private void setVolume (int volumeIn) {
        volume = volumeIn;
    }

    public Price getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        if (price == null) {
            return "$0.00x0";
        } else {
            return price + "x" + volume;
        }
    }
}
