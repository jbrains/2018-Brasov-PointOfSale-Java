package ca.jbrains.pos.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SellOneItemControllerTest {
    private Catalog catalog;
    private Display display;

    @Before
    public void setUp() throws Exception {
        catalog = Mockito.mock(Catalog.class);
        display = Mockito.mock(Display.class);
    }

    @Test
    public void productFound() throws Exception {
        Price matchingPrice = Price.euroCents(650);
        Mockito.when(catalog.findPrice("::barcode with matching price::")).thenReturn(matchingPrice);

        new SellOneItemController(this.catalog, display).onBarcode("::barcode with matching price::");

        Mockito.verify(display).displayPrice(matchingPrice);
    }

    @Test
    public void productNotFound() throws Exception {
        Mockito.when(catalog.findPrice("::barcode without matching price::")).thenReturn(null);

        new SellOneItemController(catalog, display).onBarcode("::barcode without matching price::");

        Mockito.verify(display).displayProductNotFoundMessage("::barcode without matching price::");
    }

    @Test
    public void emptyBarcode() throws Exception {
        new SellOneItemController(catalog, display).onBarcode("");

        Mockito.verify(display).displayScannedEmptyBarcodeMessage();
    }

    public interface Display {
        void displayPrice(Price price);

        void displayProductNotFoundMessage(String barcodeNotFound);

        void displayScannedEmptyBarcodeMessage();
    }

    public static class SellOneItemController {
        private final Catalog catalog;
        private final Display display;

        public SellOneItemController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            // REFACTOR Guard Clause. Move up the call stack.
            if ("".equals(barcode)) {
                display.displayScannedEmptyBarcodeMessage();
                return;
            }

            Price price = catalog.findPrice(barcode);
            if (price == null)
                display.displayProductNotFoundMessage(barcode);
            else
                display.displayPrice(price);
        }
    }

}
