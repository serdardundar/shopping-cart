package com.ecommerce.domain;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@Getter
public class Product {

    private String title;
    private BigDecimal price;
    private Category category;

    public Product(String title, BigDecimal price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }
}
