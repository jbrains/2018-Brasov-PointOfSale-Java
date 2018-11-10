/*  __    __  __  __    __  ___
 * \  \  /  /    \  \  /  /  __/
 *  \  \/  /  /\  \  \/  /  /
 *   \____/__/  \__\____/__/
 *
 * Copyright 2014-2017 Vavr, http://vavr.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.jbrains.pos.test;

public class Display {
    private String text;

    public String getText() {
        return text;
    }

    public void displayProductNotFoundMessage(String barcode) {
        this.text = String.format("Product not found: %s", barcode);
    }

    public void displayEmptyBarcodeMessage() {
        this.text = "Scanning error: empty barcode";
    }

    public void displayPrice(MonetaryAmount monetaryAmount) {
        this.text = monetaryAmount.format();
    }

    public void displayTotal(MonetaryAmount total) {
        this.text = String.format("Total: %s", total.format());
    }
}
