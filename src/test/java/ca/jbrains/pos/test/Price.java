package ca.jbrains.pos.test;

import ca.jbrains.pos.test.Sale.Monoid;
import io.vavr.Function2;

public class Price {
    public static final PriceMonoid monoid = new PriceMonoid();
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

    public static PriceMonoid monoid() {
        return monoid;
    }

    public static class PriceMonoid implements Monoid<Price> {
        @Override
        public Price zero() {
            return Price.zero();
        }

        @Override
        public Function2<Price, Price, Price> add() {
            return Function2.of(Price::add);
        }
    }
}
