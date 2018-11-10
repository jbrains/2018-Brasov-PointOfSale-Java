package ca.jbrains.pos.test;

public class FormatMessage {
    public FormatMessage() {
    }

    public String formatProductNotFoundMessage(String barcode) {
        return String.format("Product not found: %s", barcode);
    }

    public String formatEmptyBarcodeMessage() {
        return "Scanning error: empty barcode";
    }

    public String formatPrice(MonetaryAmount monetaryAmount) {
        return monetaryAmount.format();
    }

    public String formatTotal(MonetaryAmount total) {
        return String.format("Total: %s", total.format());
    }
}