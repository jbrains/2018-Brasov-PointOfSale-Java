package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", Price.bani(650));
            put("23456", Price.bani(1275));
        }}), display);

        sale.onBarcode("12345");

        Assert.assertEquals("RON 6.50", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", Price.bani(650));
            put("23456", Price.bani(1275));
        }}), display);

        sale.onBarcode("23456");

        Assert.assertEquals("RON 12.75", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", Price.bani(650));
            put("23456", Price.bani(1275));
        }}), display);

        sale.onBarcode("99999");

        Assert.assertEquals("Product not found: 99999", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(null, display);

        sale.onBarcode("");

        Assert.assertEquals("Scanning error: empty barcode", display.getText());
    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void displayPrice(String priceAsText) {
            this.text = priceAsText;
        }

        public void displayProductNotFoundMessage(String barcode) {
            this.text = String.format("Product not found: %s", barcode);
        }

        public void displayEmptyBarcodeMessage() {
            this.text = "Scanning error: empty barcode";
        }

        public void displayTotal(String totalAsText) {
            this.text = String.format("Total: %s", totalAsText);
        }
    }
}
