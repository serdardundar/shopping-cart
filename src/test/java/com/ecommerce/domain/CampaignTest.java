package com.ecommerce.domain;

import com.ecommerce.enumeration.DiscountType;
import com.ecommerce.service.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CampaignTest {

    private final String apple = "apple";
    private final String almond = "almond";

    private final Category fruitCategory = new Category("fruit");
    private final Product appleProduct = new Product(apple, new BigDecimal(100), fruitCategory);
    private final Product almondProduct = new Product(almond, new BigDecimal(150), fruitCategory);

    @Autowired
    ShoppingCart shoppingCart;

    @BeforeEach
    void init() {
        shoppingCart.emptyCartItems();
    }

    @Test
    @DisplayName("Testing Campaign with Rate Discount")
    @Order(1)
    void should_create_campaign_with_category_rate_item_count_and_discount_type() {
        Campaign campaign1 = new Campaign(fruitCategory, new BigDecimal(20.0), 3, DiscountType.Rate);
        Campaign campaign2 = new Campaign(fruitCategory, new BigDecimal(50.0), 5, DiscountType.Rate);
        Campaign campaign3 = new Campaign(fruitCategory, new BigDecimal(80), 3, DiscountType.Amount);

        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(campaign1);
        campaigns.add(campaign2);
        campaigns.add(campaign3);

        shoppingCart.addItem(appleProduct, 5);
        assertEquals(new BigDecimal(500), shoppingCart.getTotalPrice());
        shoppingCart.applyDiscounts(campaigns);
        assertEquals(new BigDecimal(400), shoppingCart.getTotalPrice().subtract(shoppingCart.getCampaignDiscount()));
    }

    @Test
    @DisplayName("Testing Campaign with Amount Discount")
    @Order(2)
    void should_create_campaign_with_category_amount_item_count_and_discount_type() {

        Campaign campaign1 = new Campaign(fruitCategory, new BigDecimal(1), 5, DiscountType.Rate);
        Campaign campaign2 = new Campaign(fruitCategory, new BigDecimal(150), 6, DiscountType.Amount);

        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(campaign1);
        campaigns.add(campaign2);

        shoppingCart.addItem(appleProduct, 5);
        shoppingCart.addItem(almondProduct, 5);
        assertEquals(new BigDecimal(1250), shoppingCart.getTotalPrice());
        shoppingCart.applyDiscounts(campaigns);
        assertEquals(new BigDecimal(1100), shoppingCart.getTotalPrice().subtract(shoppingCart.getCampaignDiscount()));
    }
}
