package Assignment1;

import exceptions.InvalidPriceOperation;

import java.util.Objects;

public class Price implements Comparable<Price> {

    private final int cents;

    Price(int cents) {
        this.cents = cents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return cents == price.cents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cents);
    }

    @Override
    public int compareTo(Price p) {
        return this.cents - p.cents;
    }

    @Override
    public String toString() {
        double value = cents/100.0;
        return String.format("$%,.2f", value);
    }

    public boolean isNegative() {
        return cents < 0;
    }

    public Price add(Price p)
            throws InvalidPriceOperation {
        if (p == null) {
            throw new InvalidPriceOperation("Input price is null.");
        }
        return new Price(this.cents + p.cents);
    }

    public Price subtract(Price p)
            throws InvalidPriceOperation {
        if (p == null) {
            throw new InvalidPriceOperation("Input price is null.");
        }
        return new Price(this.cents - p.cents);
    }

    public Price multiply(int n) {
        return new Price(cents*n);
    }

    public boolean greaterOrEqual(Price p)
            throws InvalidPriceOperation {
        if (p == null) {
            throw new InvalidPriceOperation("Input price is null.");
        }
        return this.cents >= p.cents;
    }

    public boolean lessOrEqual(Price p)
            throws InvalidPriceOperation {
        if (p == null) {
            throw new InvalidPriceOperation("Input price is null.");
        }
        return this.cents <= p.cents;
    }

    public boolean greaterThan(Price p)
            throws InvalidPriceOperation {
        if (p == null) {
            throw new InvalidPriceOperation("Input price is null.");
        }
        return this.cents > p.cents;
    }

    public boolean lessThan(Price p)
            throws InvalidPriceOperation {
        if (p == null) {
            throw new InvalidPriceOperation("Input price is null.");
        }
        return this.cents < p.cents;
    }
}
