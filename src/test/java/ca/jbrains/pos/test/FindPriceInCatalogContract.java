package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        Price matchingPrice = createAnyValidPrice();
        Catalog catalog = createCatalogWith("::known barcode::", matchingPrice);
        Assert.assertEquals(matchingPrice, catalog.findPrice("::known barcode::"));
    }

    private Price createAnyValidPrice() {
        return Price.euroCents(795);
    }

    protected abstract Catalog createCatalogWith(String barcode, Price matchingPrice);

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = createCatalogWithout("::unknown barcode::");
        Assert.assertEquals(null, catalog.findPrice("::unknown barcode::"));
    }

    protected abstract Catalog createCatalogWithout(String barcodeToAvoid);
}
