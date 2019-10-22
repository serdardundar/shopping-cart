package com.ecommerce.service;

import com.ecommerce.domain.Campaign;
import com.ecommerce.domain.Category;
import com.ecommerce.domain.Coupon;
import com.ecommerce.domain.Product;
import com.ecommerce.enumeration.DiscountType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author serdardundar
 * @since 21/10/2019
 */
@SpringBootTest
public class ShoppingCartPrinterTest {

    @Autowired
    ShoppingCart shoppingCart;

    private final String apple = "apple";
    private final String almond = "almond";
    private final Category fruitCategory = new Category("fruit");
    private final Category vegetableCategory = new Category("vegetable");
    private final Category nutsCategory = new Category("nuts");
    private final Product appleProduct = new Product( "apple", new BigDecimal(100), fruitCategory);
    private final Product orangeProduct = new Product("orange", new BigDecimal(150), fruitCategory);
    private final Product eggplantProduct = new Product("eggplant", new BigDecimal(50), vegetableCategory);
    private final Product cucumberProduct = new Product("cucumber", new BigDecimal(25), vegetableCategory);
    private final Product almondProduct = new Product("almond", new BigDecimal(25), nutsCategory);
    private final Product peanutProduct = new Product("peanut", new BigDecimal(25), nutsCategory);

    @Test
    @DisplayName("Print shopping cart in well formatted")
    void print_given_shopping_cart(){
        shoppingCart.addItem(appleProduct,5);
        shoppingCart.addItem(cucumberProduct, 14);
        shoppingCart.addItem(almondProduct, 8);
        shoppingCart.addItem(eggplantProduct, 9);
        shoppingCart.addItem(orangeProduct, 5);
        shoppingCart.addItem(peanutProduct, 10);
        Campaign fruitCampaign = new Campaign(fruitCategory, new BigDecimal(1), 5, DiscountType.Rate);
        Coupon coupon = new Coupon(new BigDecimal(100.0), new BigDecimal(10), DiscountType.Rate);
        shoppingCart.applyDiscounts(Collections.singletonList(fruitCampaign));
        shoppingCart.applyCoupon(coupon);
        shoppingCart.print();
    }
}
