package com.ecommerce.service;

import com.ecommerce.domain.Category;
import com.ecommerce.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author serdardundar
 * @since 21/10/2019
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Running Delivery Cost Calculator Tests")
class DeliveryCostCalculatorTest {

    private final String apple = "apple";
    private final String almond = "almond";
    private final String eggplant = "eggplant";
    private final String cucumber = "cucumber";
    private final Category fruitCategory = new Category("fruit");
    private final Category vegetableCategory = new Category("vegetable");
    private final Product appleProduct = new Product(apple, new BigDecimal(100), fruitCategory);
    private final Product almondProduct = new Product(almond, new BigDecimal(150), fruitCategory);
    private final Product eggplantProduct = new Product(eggplant, new BigDecimal(50), vegetableCategory);
    private final Product cucumberProduct = new Product(cucumber, new BigDecimal(25), vegetableCategory);

    @Autowired
    ShoppingCart shoppingCart;

    @Value("${delivery.cost.per.delivery}")
    private BigDecimal costPerDelivery;
    @Value("${delivery.cost.per.product}")
    private BigDecimal costPerProduct;
    @Value("${delivery.fixed.cost}")
    private BigDecimal fixedCost;
    private DeliveryCostCalculator deliveryCostCalculator;

    @BeforeEach
    void init() {
        shoppingCart.emptyCartItems();
        deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
    }

    @Test
    @DisplayName("Testing delivery cost with no cart item")
    void should_calculate_zero_for_inital_cart() {
        assertEquals(BigDecimal.ZERO, deliveryCostCalculator.calculateFor(shoppingCart));
    }

    @Test
    @DisplayName("Testing delivery cost with single product in cart")
    void should_calculate_delivery_cost_for_single_product() {
        shoppingCart.addItem(appleProduct, 5);
        assertEquals(new BigDecimal("6.57"), deliveryCostCalculator.calculateFor(shoppingCart));
    }

    @Test
    @DisplayName("Testing delivery cost by calling via shopping cart")
    void should_calculate_by_calling_shopping_cart_delivery_cost() {
        shoppingCart.addItem(appleProduct, 5);
        assertEquals(new BigDecimal("6.57"), shoppingCart.getDeliveryCost());
    }

    @Test
    @DisplayName("Calculating delivery cost by two products with same category")
    void should_calculate_delivery_cost_for_two_products_with_same_category() {
        shoppingCart.addItem(appleProduct, 5);
        shoppingCart.addItem(almondProduct, 10);

        BigDecimal costForDelivery = costPerDelivery.multiply(new BigDecimal(1));
        BigDecimal costForProduct = costPerProduct.multiply(new BigDecimal(2));

        assertEquals(costForDelivery.add(costForProduct).add(fixedCost), shoppingCart.getDeliveryCost());
    }

    @Test
    @DisplayName("Calculating delivery cost by multiple products with different categories")
    void should_calculate_delivery_cost_for_two_products_with_different_category() {
        shoppingCart.addItem(appleProduct, 5);
        shoppingCart.addItem(almondProduct, 10);
        shoppingCart.addItem(eggplantProduct, 6);
        shoppingCart.addItem(cucumberProduct, 9);

        BigDecimal costForDelivery = costPerDelivery.multiply(new BigDecimal(2));
        BigDecimal costForProduct = costPerProduct.multiply(new BigDecimal(4));

        assertEquals(costForDelivery.add(costForProduct).add(fixedCost), shoppingCart.getDeliveryCost());
    }
}
