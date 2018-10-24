package ca.jbrains.pos.test;

import io.vavr.Tuple2;

public class Sale {
    private final Catalog catalog;
    private final SellOneItemTest.Display display;
    private String reservedItem;

    public Sale(Catalog catalog, SellOneItemTest.Display display) {
        this.catalog = catalog;
        this.display = display;
        this.reservedItem  = null;
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
            reservedItem = priceAsText;
        }
    }

    public void onTotal() {
        String totalAsText = (reservedItem == null) ? "RON 0.00" : reservedItem;
        display.displayTotal(totalAsText);
    }
}
