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

import io.vavr.collection.Foldable;

public class EvenMoreFoldable {
    /**
     * A generalization of sum(), which allows the client to compute the sum of a Foldable
     * (usually a collection) by describing identityElement and how to addOperation itemsAsJavaList.
     * <p>
     * You can use this with your Value Objects by writing a Monoid adapter for your type.
     *
     * @param items
     * @param monoid A definition of identityElement and addOperation
     */
    public static <T> T sum(Foldable<T> items, Monoid<T> monoid) {
        return items.fold(monoid.identityElement(), monoid.addOperation());
    }
}
