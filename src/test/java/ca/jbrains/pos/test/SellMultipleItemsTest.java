package ca.jbrains.pos.test;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;

public class SellMultipleItemsTest {
    private Option<MonetaryAmount> totalDisplayed = Option.none();

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
    public void severalBarcodesScannedOnlySomeProductsFound() throws Exception {
        Purchase purchase = new Purchase(List.empty());

        Catalog catalog = new Catalog(new HashMap<>() {{
            put("12345", MonetaryAmount.bani(1275));
            put("23456", MonetaryAmount.bani(1766));
            put("34567", MonetaryAmount.bani(650));
        }});
        Sale sale = new Sale(catalog, purchase, new Display());

        List.of(
                "12345",
                "::not found::",
                "23456",
                "::also not found::",
                "34567",
                "::totally not found::",
                "::completely not found::"
        ).forEach(sale::onBarcode);

        Assert.assertThat(purchase.getTotal(), is(MonetaryAmount.bani(3691)));
    }

    @Test
    public void displayTotal() throws Exception {

        Sale sale = new Sale(
                null,
                new Purchase(List.of(3691).map(MonetaryAmount::bani)),
                new Display() {
                    @Override
                    public void displayTotal(MonetaryAmount total) {
                        SellMultipleItemsTest.this.totalDisplayed = Option.some(total);
                    }
                });

        sale.onTotal();

        Assert.assertThat(totalDisplayed, is(Option.some(MonetaryAmount.bani(3691))));
    }
}
