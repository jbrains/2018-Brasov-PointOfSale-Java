package ca.jbrains.pos.test;

import io.vavr.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private final Catalog catalog;
    private final SellOneItemTest.Display display;
    private final List<String> reservedItems;

    public Sale(Catalog catalog, SellOneItemTest.Display display) {
        this.catalog = catalog;
        this.display = display;
        this.reservedItems = new ArrayList<String>();
    }

    public void onBarcode(String barcode) {
        // REFACTOR Move Guard Clause up into the client?
        if ("".equals(barcode)) {
            display.displayEmptyBarcodeMessage();
            return;
        }

        String priceAsText = catalog.findPrice(barcode);
        if (priceAsText == null)
            display.displayProductNotFoundMessage(barcode);
        else {
            display.displayPrice(priceAsText);
            reservedItems.add(priceAsText);
        }
    }

    public void onTotal() {
        String totalAsText = (reservedItems.isEmpty()) ? "RON 0.00" : reservedItems.get(0);
        display.displayTotal(totalAsText);
    }
}
