package com.ecommerce.service;

import com.ecommerce.domain.ShoppingCartItem;

import java.math.BigDecimal;

/**
 * @author serdardundar
 * @since 21/10/2019
 */
class DeliveryCostCalculator {

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;
    private BigDecimal fixedCost;

    DeliveryCostCalculator(BigDecimal costPerDelivery, BigDecimal costPerProduct, BigDecimal fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    BigDecimal calculateFor(ShoppingCart shoppingCart) {
        if (shoppingCart.getShoppingCartItems().isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            long numberOfDeliveries = shoppingCart.getShoppingCartItems()
                    .stream()
                    .map(i -> i.getProduct().getCategory())
                    .distinct()
                    .count();
            long numberOfProducts = shoppingCart.getShoppingCartItems()
                    .stream()
                    .mapToInt(ShoppingCartItem::getQuantity)
                    .count();
            return costPerDelivery
                    .multiply(BigDecimal.valueOf(numberOfDeliveries))
                    .add(costPerProduct.multiply(BigDecimal.valueOf(numberOfProducts)))
                    .add(fixedCost);
        }
    }
}
