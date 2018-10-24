package ca.jbrains.pos.test;

public class Sale {
    private final Catalog catalog;
    private final SellOneItemTest.Display display;

    public Sale(Catalog catalog, SellOneItemTest.Display display) {
        this.catalog = catalog;
        this.display = display;
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
        else
            display.displayPrice(priceAsText);
    }

    public void onTotal() {
        display.displayTotal("RON 0.00");
    }
}
