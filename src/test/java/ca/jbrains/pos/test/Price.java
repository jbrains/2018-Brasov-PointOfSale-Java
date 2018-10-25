package ca.jbrains.pos.test;

public class Price {
    private final int bani;

    public Price(int bani) {
        this.bani = bani;
    }

    public static Price bani(int bani) {
        return new Price(bani);
    }

    public static Price zero() {
        return bani(0);
    }

    private double inLei() {
        return bani / 100.0d;
    }

    public Price add(Price that) {
        return Price.bani(this.bani + that.bani);
    }

    @Override
    public String toString() {
        return String.format("RON %.2f", inLei());
    }

    public String formatPrice() {
        return toString();
    }

    public int inBani() {
        return bani;
    }
}
