package ca.jbrains.pos.test;

public class Price {
    private final int euroCents;

    public Price(int euroCents) {
        this.euroCents = euroCents;
    }

    public static Price euroCents(int euroCents) {
        return new Price(euroCents);
    }

    @Override
    public String toString() {
        return "a Price";
    }

    public double inEuro() {
        return (double) (euroCents / 100.0d);
    }
}
