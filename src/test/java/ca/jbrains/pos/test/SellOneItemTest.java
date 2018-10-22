package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale();

        sale.onBarcode("12345");

        Assert.assertEquals("RON 6.50", display.getText());
    }

    public static class Display {
        public String getText() {
            return "RON 6.50";
        }
    }

    public static class Sale {
        public void onBarcode(String barcode) {

        }
    }
}
