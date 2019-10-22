package com.ecommerce.domain;

import com.ecommerce.enumeration.DiscountType;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@Getter
public class Campaign {

    private Category category;
    private BigDecimal discountProfit;
    private int minCartItemCount;
    private DiscountType discountType;

    public Campaign(Category category, BigDecimal discountProfit, int minCartItemCount, DiscountType discountType) {
        this.category = category;
        this.discountProfit = discountProfit;
        this.minCartItemCount = minCartItemCount;
        this.discountType = discountType;
    }
}
