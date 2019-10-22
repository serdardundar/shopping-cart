package com.ecommerce.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author serdardundar
 * @since 19/10/2019
 */

class ProductTest {

    @Test
    @DisplayName("Testing constructor for Product")
    void should_create_a_product_and_test_constructor_parameters() {
        Category fruitCategory = new Category("fruit");
        Product appleProduct = new Product("apple", new BigDecimal(100), fruitCategory);
        assertAll(
                () -> assertEquals("apple", appleProduct.getTitle()),
                () -> assertEquals(fruitCategory, appleProduct.getCategory()),
                () -> assertEquals(new BigDecimal("100"), appleProduct.getPrice()));
    }
}
