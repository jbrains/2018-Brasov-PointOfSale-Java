package ca.jbrains.pos.test;

import java.util.HashMap;
import java.util.Map;

public class Catalog {
    private final Map<String, String> formattedPricesByBarcode;

    private Catalog(Map<String, String> formattedPricesByBarcode) {
        this.formattedPricesByBarcode = formattedPricesByBarcode;
    }

    private static Map<String, String> convertBaniToString(Map<String, Price> pricesByBarcode) {
        Map<String, String> formattedPricesByBarcode = new HashMap<>();
        pricesByBarcode.forEach((barcode, price) -> formattedPricesByBarcode.put(barcode, price.toString()));
        return formattedPricesByBarcode;
    }

    public static Catalog createCatalog(Map<String, Price> pricesByBarcode) {
        return new Catalog(convertBaniToString(pricesByBarcode));
    }

    public String findPrice(String barcode) {
        return formattedPricesByBarcode.get(barcode);
    }
}
