package ca.jbrains.pos.test;

public class Sale {
    private final Catalog catalog;
    private final Purchase purchase;
    private final Display display;

    public Sale(Catalog catalog, Purchase purchase, Display display) {
        this.catalog = catalog;
        this.purchase = purchase;
        this.display = display;
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

    private void acceptPurchaseRequest(MonetaryAmount itemPrice) {
        display.displayPrice(itemPrice);
        purchase.addItemCosting(itemPrice);
    }

    private void rejectPurchaseRequest(String barcode) {
        display.displayProductNotFoundMessage(barcode);
    }

    public void onTotal() {
        display.displayTotal(purchase.getTotal());
    }
}