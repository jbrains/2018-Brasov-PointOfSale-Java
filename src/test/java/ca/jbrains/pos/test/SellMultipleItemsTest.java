package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

public class SellMultipleItemsTest {
    @Test
    public void noItemsScanned() throws Exception {
        SellOneItemTest.Display display = new SellOneItemTest.Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", Price.bani(650));
            put("23456", Price.bani(1275));
        }}), display);

        // no items scanned

        sale.onTotal();

        Assert.assertEquals("Total: RON 0.00", display.getText());
    }

    @Test
    public void oneItemScannedButNoProductsFound() throws Exception {
        SellOneItemTest.Display display = new SellOneItemTest.Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", Price.bani(650));
            put("23456", Price.bani(1275));
        }}), display);

        sale.onBarcode("::barcode not found::");
        sale.onTotal();

        Assert.assertEquals("Total: RON 0.00", display.getText());
    }

    @Test
    public void oneItemScannedAndProductFound() throws Exception {
        SellOneItemTest.Display display = new SellOneItemTest.Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", Price.bani(650));
            put("23456", Price.bani(1275));
        }}), display);

        sale.onBarcode("23456");

        sale.onTotal();

        Assert.assertEquals("Total: RON 12.75", display.getText());
    }

    @Test
    @Ignore("refactoring")
    public void severalItemsScannedAndAllProductsFound() throws Exception {
        SellOneItemTest.Display display = new SellOneItemTest.Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", Price.bani(650));
            put("23456", Price.bani(1275));
            put("34567", Price.bani(3190));
            put("45678", Price.bani(1766));
        }}), display);
        sale.onBarcode("23456");
        sale.onBarcode("34567");
        sale.onBarcode("12345");

        sale.onTotal();

        Assert.assertEquals("Total: RON 36.91", display.getText());
    }
}
