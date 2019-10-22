package com.ecommerce.service;

import com.ecommerce.domain.ShoppingCartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;

/**
 * @author serdardundar
 * @since 21/10/2019
 */
@Component
public class ShoppingCartPrinter {

    private static final String TABLE_BORDER = "+---------------+--------------+----------+------------+-------------+%n";
    private static final NumberFormat formatter = new DecimalFormat("0.00########");
    private static final String LINE_FORMATTER = "|%54s| %11s | %n";


    void printCart(ShoppingCart shoppingCart) {
        String productRowFormat = "| %-13s | %-12s | %-8d | %-10s | %11s |%n";

        shoppingCart.getShoppingCartItems().sort(
                Comparator.comparing((ShoppingCartItem o) -> o.getProduct().getCategory().getTitle()));

        System.out.format(TABLE_BORDER);
        System.out.format("| Category Name | Product Name | Quantity | Unit Price | Total Price |%n");
        System.out.format(TABLE_BORDER);
        shoppingCart.getShoppingCartItems().forEach(shoppingCartItem -> {
            System.out.format(productRowFormat,
                    shoppingCartItem.getProduct().getCategory().getTitle(),
                    shoppingCartItem.getProduct().getTitle(),
                    shoppingCartItem.getQuantity(),
                    formatter.format(shoppingCartItem.getProduct().getPrice()),
                    formatter.format(shoppingCartItem.getProduct().getPrice().multiply(new BigDecimal(shoppingCartItem.getQuantity()))));
        });
        System.out.format(TABLE_BORDER);

        System.out.format(LINE_FORMATTER, "(Coupon Discount)", formatter.format(shoppingCart.getCouponDiscount().negate()));
        System.out.format(LINE_FORMATTER, "(Campaign Discount)", formatter.format(shoppingCart.getCampaignDiscount().negate()));
        System.out.format(TABLE_BORDER);
        System.out.format(LINE_FORMATTER, "Total Discount",
                formatter.format(shoppingCart.getCampaignDiscount().add(shoppingCart.getCouponDiscount()).negate()));
        System.out.format(LINE_FORMATTER, "Total Price", formatter.format(
                shoppingCart.getTotalPrice().subtract(shoppingCart.getCouponDiscount())
                        .subtract(shoppingCart.getCampaignDiscount())));
        System.out.format(LINE_FORMATTER, "Delivery Cost", formatter.format(
                shoppingCart.getDeliveryCost()));
        System.out.format(LINE_FORMATTER, "Total Amount", formatter.format(
                shoppingCart.getTotalAmountAfterDiscounts()));
        System.out.format(TABLE_BORDER);
    }
}
