package com.ecommerce.domain;

import com.ecommerce.service.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@SpringBootTest
@DisplayName("Running Shopping Cart Item Tests")
class ShoppingCartItemTest {

    private final String apple = "apple";
    private final String almond = "almond";
    @Autowired
    ShoppingCart shoppingCart;
    private Category fruitCategory = new Category("fruit");
    private Category nutsCategory = new Category("nuts");
    private Product appleProduct = new Product(apple, new BigDecimal(100), fruitCategory);
    private Product almondProduct = new Product(almond, new BigDecimal(150), nutsCategory);

    @BeforeEach
    void init() {
        shoppingCart.emptyCartItems();
    }

    @Test
    @DisplayName("Testing constructor for ShoppingCartItem")
    void should_create_a_shopping_cart_item_and_test_constructor_parameters() {
        ShoppingCartItem appleCartItem = new ShoppingCartItem(appleProduct, 3);
        ShoppingCartItem almondCartItem = new ShoppingCartItem(almondProduct, 1);

        assertAll(() -> assertEquals(appleCartItem.getProduct().getTitle(), apple),
                () -> assertEquals(3, appleCartItem.getQuantity()),
                () -> assertEquals(almondCartItem.getProduct().getTitle(), almond),
                () -> assertEquals(1, almondCartItem.getQuantity()));
    }

    @Test
    @DisplayName("Adding new cart item to ShoppingCart")
    void should_add_a_new_cart_item_to_shopping_cart() {
        shoppingCart.addItem(appleProduct, 3);
        assertTrue(shoppingCart.getShoppingCartItems().stream().anyMatch(p -> p.getProduct().equals(appleProduct)));
    }

    @Test
    @DisplayName("Adding new product to existing ShoppingCartItem")
    void should_add_item_to_current_product_in_shopping_cart() {
        shoppingCart.addItem(appleProduct, 3);
        assertTrue(shoppingCart.getShoppingCartItems().stream().anyMatch(p -> p.getProduct().equals(appleProduct)));

        shoppingCart.addItem(appleProduct, 5);
        assertEquals(8, shoppingCart.getShoppingCartItems().stream()
                .filter(p -> p.getProduct().equals(appleProduct))
                .mapToInt(ShoppingCartItem::getQuantity).sum());
    }
}
