package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", MonetaryAmount.bani(650));
            put("23456", MonetaryAmount.bani(1275));
        }}), new Purchase(io.vavr.collection.List.empty()), display);

        sale.onBarcode("12345");

        Assert.assertEquals("RON 6.50", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", MonetaryAmount.bani(650));
            put("23456", MonetaryAmount.bani(1275));
        }}), new Purchase(io.vavr.collection.List.empty()), display);

        sale.onBarcode("23456");

        Assert.assertEquals("RON 12.75", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", MonetaryAmount.bani(650));
            put("23456", MonetaryAmount.bani(1275));
        }}), new Purchase(io.vavr.collection.List.empty()), display);

        sale.onBarcode("99999");

        Assert.assertEquals("Product not found: 99999", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(null, new Purchase(io.vavr.collection.List.empty()), display);

        sale.onBarcode("");

        Assert.assertEquals("Scanning error: empty barcode", display.getText());
    }

}
