package com.ecommerce.factory.calculator;

import com.ecommerce.domain.Campaign;
import com.ecommerce.domain.ShoppingCartItem;
import com.ecommerce.factory.campaign.CampaignCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@Component
public class AmountDiscountCalculator implements CampaignCalculator {

    @Override
    public BigDecimal calculateCampaignDiscountedPrice(Campaign campaign, List<ShoppingCartItem> shoppingCartItems) {
        long itemCount = shoppingCartItems.stream()
                .filter(s -> s.getProduct().getCategory().equals(campaign.getCategory()))
                .mapToInt(ShoppingCartItem::getQuantity).sum();

        if (itemCount > campaign.getMinCartItemCount()) {
            return campaign.getDiscountProfit();
        } else {
            return BigDecimal.ZERO;
        }
    }
}
