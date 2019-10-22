package com.ecommerce.factory.coupon;

import com.ecommerce.enumeration.DiscountType;

/**
 * @author serdardundar
 * @since 20/10/2019
 */
public interface CouponFactory {

    CouponCalculator getCouponCalculator(DiscountType discountType);
}
