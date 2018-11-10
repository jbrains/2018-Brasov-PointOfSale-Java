package ca.jbrains.pos.test;

import io.vavr.collection.List;

public class Purchase {
    private List<MonetaryAmount> items;

    public Purchase(List<MonetaryAmount> items) {
        this.items = items;
    }

    public void addItemCosting(MonetaryAmount monetaryAmount) {
        items = items.push(monetaryAmount);
    }

    public MonetaryAmount getTotal() {
        return EvenMoreFoldable.sum(items, MonetaryAmount.monoid());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}