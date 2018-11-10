package ca.jbrains.pos.test;

import java.util.ArrayList;
import java.util.List;

public class Purchase {
    public final List<MonetaryAmount> items;

    public Purchase() {
        items = new ArrayList<>();
    }

    public void addItemCosting(MonetaryAmount monetaryAmount) {
        items.add(monetaryAmount);
    }

    public MonetaryAmount getTotal() {
        return EvenMoreFoldable.sum(io.vavr.collection.List.ofAll(items), MonetaryAmount.monoid());
    }
}