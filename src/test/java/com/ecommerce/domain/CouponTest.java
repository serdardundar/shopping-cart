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
 * @since 20/10/2019
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CouponTest {

    private final String apple = "apple";
    private final Category fruitCategory = new Category("fruit");
    private final Product appleProduct = new Product(apple, new BigDecimal(100), fruitCategory);

    @Autowired
    ShoppingCart shoppingCart;

    @BeforeEach
    void init() {
        shoppingCart.emptyCartItems();
    }

    @Test
    @DisplayName("Testing Coupon with Rate Discount")
    void should_create_coupon_without_campaign_and_apply_the_expected_discount() {
        Coupon coupon = new Coupon(new BigDecimal(100.0), new BigDecimal(10), DiscountType.Rate);

        shoppingCart.addItem(appleProduct, 5);
        assertEquals(new BigDecimal(500), shoppingCart.getTotalPrice());
        shoppingCart.applyCoupon(coupon);
        assertEquals(new BigDecimal(450), shoppingCart.getTotalPrice().subtract(shoppingCart.getCouponDiscount()));
    }

    @Test
    @DisplayName("Testing Campaign with Rate Discount After Applying Campaign Discount")
    void should_create_coupon_after_campaign_apply_both_discounts() {

        Campaign campaign = new Campaign(fruitCategory, new BigDecimal(10), 4, DiscountType.Rate);

        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(campaign);

        shoppingCart.addItem(appleProduct, 5);
        assertEquals(new BigDecimal(500), shoppingCart.getTotalPrice());
        shoppingCart.applyDiscounts(campaigns);
        assertEquals(new BigDecimal(450), shoppingCart.getTotalPrice().subtract(shoppingCart.getCampaignDiscount()));

        Coupon coupon = new Coupon(new BigDecimal(100.0), new BigDecimal(10), DiscountType.Rate);
        shoppingCart.applyCoupon(coupon);
        assertEquals(new BigDecimal(405), shoppingCart.getTotalPrice()
                .subtract(shoppingCart.getCampaignDiscount())
                .subtract(shoppingCart.getCouponDiscount()));
    }
}
