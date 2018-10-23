package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        Price matchingPrice = Price.euroCents(795);
        InMemoryCatalog catalog = new InMemoryCatalog(Collections.singletonMap("12345", matchingPrice));
        Assert.assertEquals(matchingPrice, catalog.findPrice("12345"));
    }

    @Test
    public void productNotFound() throws Exception {
        InMemoryCatalog catalog = new InMemoryCatalog(Collections.emptyMap());
        Assert.assertEquals(null, catalog.findPrice("12345"));
    }

    public static class InMemoryCatalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
