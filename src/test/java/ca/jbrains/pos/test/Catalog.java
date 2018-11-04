package ca.jbrains.pos.test;

import io.vavr.control.Option;

import java.util.Map;

public class Catalog {
    private final Map<String, Price> pricesByBarcode;

    public Catalog(Map<String, Price> pricesByBarcode) {
        this.pricesByBarcode = pricesByBarcode;
    }

    public Price findPrice_old(String barcode) {
        return pricesByBarcode.get(barcode);
    }
    public Option<Price> findPrice(String barcode) {
        return Option.of(findPrice_old(barcode));
    }
}
