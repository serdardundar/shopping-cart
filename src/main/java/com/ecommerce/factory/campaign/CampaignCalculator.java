package com.ecommerce.factory.campaign;

import com.ecommerce.domain.Campaign;
import com.ecommerce.domain.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
public interface CampaignCalculator {

    BigDecimal calculateCampaignDiscountedPrice(Campaign campaign, List<ShoppingCartItem> shoppingCartItems);
}
