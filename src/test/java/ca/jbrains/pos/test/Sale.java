package ca.jbrains.pos.test;

import io.vavr.Function2;

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
        Price total = sum(new PriceFoldableValue(), io.vavr.collection.List.ofAll(reservedItems));
        display.displayTotal(total.formatPrice());
    }

    public static <T> T sum(FoldableValue<T> foldableValue, io.vavr.collection.List<T> items) {
        return items.fold(foldableValue.zero(), foldableValue.accumulate());
    }

    interface FoldableValue<T> {
        T zero();

        Function2<T, T, T> accumulate();
    }

    public static class PriceFoldableValue implements FoldableValue<Price> {
        @Override
        public Price zero() {
            return Price.zero();
        }

        @Override
        public Function2<Price, Price, Price> accumulate() {
            return Function2.of(Price::add);
        }
    }
}