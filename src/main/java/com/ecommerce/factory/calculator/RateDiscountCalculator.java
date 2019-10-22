package com.ecommerce.factory.calculator;

import com.ecommerce.domain.Campaign;
import com.ecommerce.domain.Coupon;
import com.ecommerce.domain.ShoppingCartItem;
import com.ecommerce.factory.campaign.CampaignCalculator;
import com.ecommerce.factory.coupon.CouponCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@Component
public class RateDiscountCalculator implements CampaignCalculator, CouponCalculator {

    @Override
    public BigDecimal calculateCampaignDiscountedPrice(Campaign campaign, List<ShoppingCartItem> shoppingCartItems) {
        long itemCount = shoppingCartItems.stream()
                .filter(s -> s.getProduct().getCategory().equals(campaign.getCategory()))
                .mapToInt(ShoppingCartItem::getQuantity).sum();

        if (itemCount > campaign.getMinCartItemCount()) {
            BigDecimal discountableItemTotalCost =
                    shoppingCartItems
                            .stream()
                            .filter(i -> i.getProduct().getCategory().equals(campaign.getCategory()))
                            .map(i -> i.getProduct().getPrice().multiply(new BigDecimal(i.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            return campaign.getDiscountProfit().multiply(discountableItemTotalCost)
                    .divide(new BigDecimal(100), RoundingMode.UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calculateCouponDiscountedPrice(Coupon coupon, BigDecimal totalPriceAfterCampaignDiscount) {
        if (totalPriceAfterCampaignDiscount.compareTo(coupon.getMinPurchaseAmount()) > 0) {
            return coupon.getDiscountProfit().multiply(totalPriceAfterCampaignDiscount)
                    .divide(new BigDecimal(100), RoundingMode.UP);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
