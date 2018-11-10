package ca.jbrains.pos.test;

import java.util.List;

public class Purchase {
    public final List<MonetaryAmount> items;

    public Purchase(io.vavr.collection.List<MonetaryAmount> items) {
        this.items = items.toJavaList();
    }

    public void addItemCosting(MonetaryAmount monetaryAmount) {
        items.add(monetaryAmount);
    }

    public MonetaryAmount getTotal() {
        return EvenMoreFoldable.sum(io.vavr.collection.List.ofAll(items), MonetaryAmount.monoid());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}