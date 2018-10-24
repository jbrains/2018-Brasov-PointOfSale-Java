package ca.jbrains.pos.test;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private final Catalog catalog;
    private final SellOneItemTest.Display display;
    private final List<Price> reservedItems;

    public Sale(Catalog catalog, SellOneItemTest.Display display) {
        this.catalog = catalog;
        this.display = display;
        this.reservedItems = new ArrayList<Price>();
    }

    public void onBarcode(String barcode) {
        // REFACTOR Move Guard Clause up into the client?
        if ("".equals(barcode)) {
            display.displayEmptyBarcodeMessage();
            return;
        }

        Price price = catalog.findPrice(barcode);
        if (price == null)
            display.displayProductNotFoundMessage(barcode);
        else {
            display.displayPrice(price.formatPrice());
            reservedItems.add(price);
        }
    }

    public void onTotal() {
        Price total = (reservedItems.isEmpty()) ? Price.bani(0) : reservedItems.get(0);
        display.displayTotal(total.formatPrice());
    }
}
