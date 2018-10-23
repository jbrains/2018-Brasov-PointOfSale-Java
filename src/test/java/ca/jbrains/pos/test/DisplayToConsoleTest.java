package ca.jbrains.pos.test;

import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class DisplayToConsoleTest {
    @Test
    public void emptyBarcodeMessage() throws Exception {
        StringWriter canvas = new StringWriter();
        displayEmptyBarcodeMessage(canvas);
        Assert.assertEquals(List.of("Scanning error: empty barcode"), lines(canvas.toString()));
    }

    @Test
    public void productNotFoundMessage() throws Exception {
        StringWriter canvas = new StringWriter();
        displayProductNotFoundMessage(canvas, "::missing barcode::");
        Assert.assertEquals(List.of("Product not found: ::missing barcode::"), lines(canvas.toString()));
    }

    @Test
    public void price() throws Exception {
        StringWriter canvas = new StringWriter();
        displayPrice(canvas, Price.euroCents(795));
        Assert.assertEquals(List.of("EUR 7.95"), lines(canvas.toString()));
    }

    private void displayPrice(StringWriter canvas, Price price) {
        new PrintWriter(canvas, true).printf("EUR %.2f", price.inEuro()).println();
    }

    private void displayProductNotFoundMessage(Writer canvas, String missingBarcode) {
        new PrintWriter(canvas, true).printf("Product not found: %s", missingBarcode).println();
    }

    private static List<String> lines(String multilineText) {
        return List.ofAll(new BufferedReader(new StringReader(multilineText)).lines());
    }

    private void displayEmptyBarcodeMessage(Writer canvas) {
        new PrintWriter(canvas, true).println("Scanning error: empty barcode");
    }
}