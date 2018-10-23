package ca.jbrains.pos.test;

import org.junit.Test;
import org.mockito.Mockito;

public class SellOneItemControllerTest {
    @Test
    public void productFound() throws Exception {
        Catalog catalog = Mockito.mock(Catalog.class);
        Display display = Mockito.mock(Display.class);

        Price matchingPrice = Price.euroCents(650);
        Mockito.when(catalog.findPrice("12345")).thenReturn(matchingPrice);

        new SellOneItemController(catalog, display).onBarcode("12345");

        Mockito.verify(display).displayPrice(matchingPrice);
    }

    public interface Catalog {
        Price findPrice(String barcode);
    }
    public interface Display {
        void displayPrice(Price price);
    }
    public static class SellOneItemController {
        public SellOneItemController(Catalog catalog, Display display) {
        }

        public void onBarcode(String barcode) {
        }
    }
    public static class Price {
        public static Price euroCents(int euroCents) {
            return new Price();
        }
    }
}
