package ca.jbrains.pos.test;

import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.Map;

public class Catalog {
    // REFACTOR Replace with Vavr Map
    private final Map<String, MonetaryAmount> pricesByBarcode;

    public Catalog(Map<String, MonetaryAmount> pricesByBarcode) {
        this.pricesByBarcode = pricesByBarcode;
    }

    public Either<String, MonetaryAmount> lookupBarcode(String barcode) {
        return Option.of(pricesByBarcode.get(barcode)).toEither(barcode);
    }
}
