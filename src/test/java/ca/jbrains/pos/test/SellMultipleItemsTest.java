package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class SellMultipleItemsTest {
    @Test
    public void noItemsScanned() throws Exception {
        SellOneItemTest.Display display = new SellOneItemTest.Display();
        Sale sale = new Sale(new Catalog(new HashMap<String, String>() {{
            put("12345", "RON 6.50");
            put("23456", "RON 12.75");
        }}), display);

        // no items scanned

        sale.onTotal();

        Assert.assertEquals("Total: RON 0.00", display.getText());
    }
}
