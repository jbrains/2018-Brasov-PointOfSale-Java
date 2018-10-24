package ca.jbrains.pos.test;

import java.util.HashMap;
import java.util.Map;

public class Catalog {
    private final Map<String, String> formattedPricesByBarcode;
    private final Map<String, Price> pricesByBarcode;

    private Catalog(Map<String, String> formattedPricesByBarcode, Map<String, Price> pricesByBarcode) {
        this.formattedPricesByBarcode = formattedPricesByBarcode;
        this.pricesByBarcode = pricesByBarcode;
    }

    private static Map<String, String> convertBaniToString(Map<String, Price> pricesByBarcode) {
        Map<String, String> formattedPricesByBarcode = new HashMap<>();
        pricesByBarcode.forEach((barcode, price) -> formattedPricesByBarcode.put(barcode, price.toString()));
        return formattedPricesByBarcode;
    }

    public static Catalog createCatalog(Map<String, Price> pricesByBarcode) {
        return new Catalog(convertBaniToString(pricesByBarcode), pricesByBarcode);
    }

    public String findPrice(String barcode) {
        Price price = pricesByBarcode.get(barcode);
        return price == null ? null : price.toString();
    }
}
