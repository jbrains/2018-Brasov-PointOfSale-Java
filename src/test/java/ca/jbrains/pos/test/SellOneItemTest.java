package ca.jbrains.pos.test;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.Assert;
import org.junit.Test;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(HashMap.of(
                "12345", "RON 6.50",
                "23456", "RON 12.75"
        ), display);

        sale.onBarcode("12345");

        Assert.assertEquals("RON 6.50", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(HashMap.of(
                "12345", "RON 6.50",
                "23456", "RON 12.75"
        ), display);

        sale.onBarcode("23456");

        Assert.assertEquals("RON 12.75", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(HashMap.of(
                "12345", "RON 6.50",
                "23456", "RON 12.75"
        ), display);

        sale.onBarcode("99999");

        Assert.assertEquals("Product not found: 99999", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(HashMap.empty(), display);

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
    }

    public static class Sale {
        private final Map<String, String> pricesByBarcode;
        private final Display display;

        public Sale(Map<String, String> pricesByBarcode, Display display) {
            this.pricesByBarcode = pricesByBarcode;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.setText("Scanning error: empty barcode");
                return;
            }

            String priceAsText = pricesByBarcode.getOrElse(barcode, null);
            if (priceAsText == null)
                display.setText(String.format("Product not found: %s", barcode));
            else
                display.setText(priceAsText);
        }
    }
}
