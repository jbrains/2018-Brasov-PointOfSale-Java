package ca.jbrains.pos.test;

import io.vavr.Function2;
import io.vavr.collection.Foldable;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private final Catalog catalog;
    private final Display display;
    private final List<MonetaryAmount> reservedItems;

    public Sale(Catalog catalog, Display display) {
        this.catalog = catalog;
        this.display = display;
        this.reservedItems = new ArrayList<>();
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

    private void acceptPurchaseRequest(MonetaryAmount monetaryAmount) {
        display.displayPrice(monetaryAmount);
        reservedItems.add(monetaryAmount);
    }

    public void onTotal() {
        MonetaryAmount total = EvenMoreFoldable.sum(io.vavr.collection.List.ofAll(reservedItems), MonetaryAmount.monoid());
        display.displayTotal(total);
    }

    interface Monoid<T> {
        T zero();

        Function2<T, T, T> add();
    }

    public static class EvenMoreFoldable {
        /**
         * A generalization of sum(), which allows the client to compute the sum of a Foldable
         * (usually a collection) by describing zero and how to add items.
         * <p>
         * You can use this with your Value Objects by writing a Monoid adapter for your type.
         *
         * @param items
         * @param monoid A definition of zero and add
         */
        public static <T> T sum(Foldable<T> items, Monoid<T> monoid) {
            return items.fold(monoid.zero(), monoid.add());
        }
    }
}