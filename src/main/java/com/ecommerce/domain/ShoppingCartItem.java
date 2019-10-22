package com.ecommerce.domain;

import lombok.Getter;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@Getter
public class ShoppingCartItem {

    private Product product;
    private int quantity;

    public ShoppingCartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}
