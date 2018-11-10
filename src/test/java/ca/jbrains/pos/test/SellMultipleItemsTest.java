package ca.jbrains.pos.test;

import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class SellMultipleItemsTest {
    @Test
    public void noItemsScanned() throws Exception {
        Display display = new Display();
        Purchase purchase = new Purchase(List.empty());
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", MonetaryAmount.bani(650));
            put("23456", MonetaryAmount.bani(1275));
        }}), purchase, display);

        // no itemsAsJavaList scanned

        Assert.assertTrue(purchase.isEmpty());
    }

    @Test
    public void oneItemScannedButNoProductsFound() throws Exception {
        Display display = new Display();
        Purchase purchase = new Purchase(List.empty());
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", MonetaryAmount.bani(650));
            put("23456", MonetaryAmount.bani(1275));
        }}), purchase, display);

        sale.onBarcode("::barcode not found::");

        Assert.assertTrue(purchase.isEmpty());
    }

    @Test
    public void oneItemScannedAndProductFound() throws Exception {
        Display display = new Display();
        Purchase purchase = new Purchase(List.empty());
        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", MonetaryAmount.bani(650));
            put("23456", MonetaryAmount.bani(1275));
        }}), purchase, display);

        sale.onBarcode("23456");

        Assert.assertEquals(MonetaryAmount.bani(1275), purchase.getTotal());
    }

    @Test
    public void severalItemsScannedAndAllProductsFound() throws Exception {
        Purchase purchase = new Purchase(List.empty());

        Sale sale = new Sale(new Catalog(new HashMap<>() {{
            put("12345", MonetaryAmount.bani(1275));
            put("23456", MonetaryAmount.bani(1766));
            put("34567", MonetaryAmount.bani(650));
        }}), purchase, new Display());

        List.of("12345", "23456", "34567").forEach(sale::onBarcode);

        Assert.assertEquals(MonetaryAmount.bani(3691), purchase.getTotal());
    }

    @Test
    public void displayTotal() throws Exception {
        List<MonetaryAmount> itemsQueuedForPurchase = List.of(3691).map(MonetaryAmount::bani);

        Display display = new Display();

        Sale sale = new Sale(null, new Purchase(itemsQueuedForPurchase), display);

        sale.onTotal();

        Assert.assertEquals("Total: RON 36.91", display.getText());
    }
}
