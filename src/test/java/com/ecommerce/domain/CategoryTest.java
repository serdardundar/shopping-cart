package com.ecommerce.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
class CategoryTest {

    private String fruit = "fruit";

    @Test
    @DisplayName("Testing constructor without parent for Category")
    void should_create_category_without_parent_category() {
        Category fruitCategory = new Category(fruit);
        assertEquals("fruit", fruitCategory.getTitle());
    }

    @Test
    @DisplayName("Testing constructor with parent for Category")
    void shpuld_create_category_with_parent_category() {
        String food = "food";
        Category foodCategory = new Category(food);
        Category fruitCategory = new Category(fruit, foodCategory);

        assertAll(() -> assertEquals(fruitCategory.getParentCategory(), foodCategory),
                () -> assertEquals(fruitCategory.getTitle(), fruit));
    }
}
