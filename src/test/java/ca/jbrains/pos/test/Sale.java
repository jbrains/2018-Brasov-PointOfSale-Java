package ca.jbrains.pos.test;

import io.vavr.Function2;
import io.vavr.collection.Foldable;

public class Sale {
    private final Catalog catalog;
    private final Display display;
    private final Purchase purchase;

    public Sale(Catalog catalog, Display display) {
        this.catalog = catalog;
        this.display = display;
        this.purchase = new Purchase();
    }

    public void onBarcode(String barcode) {
        // REFACTOR Move Guard Clause up into the client?
        if ("".equals(barcode)) {
            display.displayEmptyBarcodeMessage();
            return;
        }

        catalog.lookupBarcode(barcode)
                .peek(this::acceptPurchaseRequest)
                .orElseRun(this::rejectPurchaseRequest);
    }

    private void rejectPurchaseRequest(String barcode) {
        display.displayProductNotFoundMessage(barcode);
    }

    private void acceptPurchaseRequest(MonetaryAmount itemPrice) {
        display.displayPrice(itemPrice);
        purchase.addItemCosting(itemPrice);
    }

    public void onTotal() {
        display.displayTotal(purchase.getTotal());
    }
}