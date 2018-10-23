package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new HashMap<String, String>() {{
            put("12345", "RON 6.50");
            put("23456", "RON 12.75");
        }}, display);

        sale.onBarcode("12345");

        Assert.assertEquals("RON 6.50", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new HashMap<String, String>() {{
            put("12345", "RON 6.50");
            put("23456", "RON 12.75");
        }}, display);

        sale.onBarcode("23456");

        Assert.assertEquals("RON 12.75", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(new HashMap<String, String>() {{
            put("12345", "RON 6.50");
            put("23456", "RON 12.75");
        }}, display);

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

        public void setText(String text) {
            this.text = text;
        }

        public void displayPrice(String priceAsText) {
            setText(priceAsText);
        }

        public void displayProductNotFoundMessage(String barcode) {
            setText(String.format("Product not found: %s", barcode));
        }

        public void displayEmptyBarcodeMessage() {
            setText("Scanning error: empty barcode");
        }
    }

    public static class Sale {
        private final Map<String, String> pricesByBarcode;
        private final Display display;

        public Sale(Map<String, String> pricesByBarcode, Display display) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(String barcode) {
            // REFACTOR Move Guard Clause up into the client?
            if ("".equals(barcode)) {
                display.displayEmptyBarcodeMessage();
                return;
            }

            String priceAsText = findPrice(barcode);
            if (priceAsText == null)
                display.displayProductNotFoundMessage(barcode);
            else
                display.displayPrice(priceAsText);
        }

        private String findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
