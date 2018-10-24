package ca.jbrains.pos.test;

import java.util.Map;

public class Catalog {
    private final Map<String, Price> pricesByBarcode;

    public Catalog(Map<String, Price> pricesByBarcode) {
        this.pricesByBarcode = pricesByBarcode;
    }

    public Price findPrice(String barcode) {
        return pricesByBarcode.get(barcode);
    }
}
