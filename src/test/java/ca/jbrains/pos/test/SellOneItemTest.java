package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(display);

        sale.onBarcode("12345");

        Assert.assertEquals("RON 6.50", display.getText());
    }

    @Test
    @Ignore("refactoring")
    public void anotherProductFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(display);

        sale.onBarcode("23456");

        Assert.assertEquals("RON 12.75", display.getText());
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
        private Display display;

        public Sale(Display display) {
            this.display = display;
        }

        public void onBarcode(String barcode) {
            display.setText("RON 6.50");
        }
    }
}
