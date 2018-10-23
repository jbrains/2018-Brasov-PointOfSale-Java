package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        Price matchingPrice = createAnyValidPrice();
        Catalog catalog = createCatalogWith("::known barcode::", matchingPrice);
        Assert.assertEquals(matchingPrice, catalog.findPrice("::known barcode::"));
    }

    private Price createAnyValidPrice() {
        return Price.euroCents(795);
    }

    private Catalog createCatalogWith(String barcode, Price matchingPrice) {
        return new InMemoryCatalog(Collections.singletonMap(barcode, matchingPrice));
    }

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = new InMemoryCatalog(Collections.emptyMap());
        Assert.assertEquals(null, catalog.findPrice("12345"));
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
