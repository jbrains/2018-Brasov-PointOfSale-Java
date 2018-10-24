package ca.jbrains.pos.test;

public class Price {
    private final int bani;

    public Price(int bani) {
        this.bani = bani;
    }

    public static Price bani(int bani) {
        return new Price(bani);
    }

    private double inLei() {
        return bani / 100.0d;
    }

    @Override
    public String toString() {
        return String.format("RON %.2f", inLei());
    }
}
