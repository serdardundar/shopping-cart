package com.ecommerce.factory.coupon;

import com.ecommerce.domain.Coupon;

import java.math.BigDecimal;

/**
 * @author serdardundar
 * @since 20/10/2019
 */
public interface CouponCalculator {

    BigDecimal calculateCouponDiscountedPrice(Coupon coupon, BigDecimal totalPriceAfterCampaignDiscount);
}
