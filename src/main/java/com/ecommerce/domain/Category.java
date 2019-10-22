package com.ecommerce.domain;

import lombok.Getter;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@Getter
public class Category {

    private String title;
    private Category parentCategory;

    public Category(String title) {
        this.title = title;
    }

    public Category(String title, Category parentCategory) {
        this.title = title;
        this.parentCategory = parentCategory;
    }
}
