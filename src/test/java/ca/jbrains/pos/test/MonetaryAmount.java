package ca.jbrains.pos.test;

import ca.jbrains.pos.test.Sale.Monoid;
import io.vavr.Function2;

public class MonetaryAmount {
    public static final PriceMonoid monoid = new PriceMonoid();
    private final int bani;

    public MonetaryAmount(int bani) {
        this.bani = bani;
    }

    public static MonetaryAmount bani(int bani) {
        return new MonetaryAmount(bani);
    }

    public static MonetaryAmount zero() {
        return bani(0);
    }

    private double inLei() {
        return bani / 100.0d;
    }

    public MonetaryAmount add(MonetaryAmount that) {
        return MonetaryAmount.bani(this.bani + that.bani);
    }

    @Override
    public String toString() {
        return String.format("RON %.2f", inLei());
    }

    public String format() {
        return toString();
    }

    public int inBani() {
        return bani;
    }

    public static PriceMonoid monoid() {
        return monoid;
    }

    public static class PriceMonoid implements Monoid<MonetaryAmount> {
        @Override
        public MonetaryAmount zero() {
            return MonetaryAmount.zero();
        }

        @Override
        public Function2<MonetaryAmount, MonetaryAmount, MonetaryAmount> add() {
            return Function2.of(MonetaryAmount::add);
        }
    }
}
