package ca.jbrains.pos.test;

import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.Map;

public class Catalog {
    private final Map<String, Price> pricesByBarcode;

    public Catalog(Map<String, Price> pricesByBarcode) {
        this.pricesByBarcode = pricesByBarcode;
    }

    public Either<String, Price> lookupBarcode(String barcode) {
        return Option.of(pricesByBarcode.get(barcode)).toEither(barcode);
    }
}
