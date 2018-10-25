package ca.jbrains.pos.test;

import io.vavr.Function2;
import io.vavr.collection.Foldable;

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
        Price total = EvenMoreFoldable.sum(new PriceFoldableValue(), io.vavr.collection.List.ofAll(reservedItems));
        display.displayTotal(total.formatPrice());
    }

    interface FoldableValue<T> {
        T zero();

        Function2<T, T, T> add();
    }

    public static class PriceFoldableValue implements FoldableValue<Price> {
        @Override
        public Price zero() {
            return Price.zero();
        }

        @Override
        public Function2<Price, Price, Price> add() {
            return Function2.of(Price::add);
        }
    }

    public static class EvenMoreFoldable {
        /**
         * A generalization of sum(), which allows the client to compute the sum of a Foldable
         * (usually a collection) by describing zero and how to add items.
         *
         * You can use this with your Value Objects by writing a FoldableValue adapter for your type.
         *
         * @param foldableValue A definition of zero and add
         * @param items
         */
        public static <T> T sum(FoldableValue<T> foldableValue, Foldable<T> items) {
            return items.fold(foldableValue.zero(), foldableValue.add());
        }
    }
}