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
        Price total = tallyReservedItemsPrices(reservedItems);
        display.displayTotal(total.formatPrice());
    }

    private Price tallyReservedItemsPrices(List<Price> reservedItems) {
        if (reservedItems.isEmpty()) return Price.bani(0);
        else if (reservedItems.size() == 1) return this.reservedItems.get(0);
        else return Price.bani(io.vavr.collection.List.ofAll(reservedItems).map(each -> each.inBani()).fold(0, (sum, each) -> sum + each));

    }
}
