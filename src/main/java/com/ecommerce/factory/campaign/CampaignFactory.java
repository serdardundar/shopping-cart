package com.ecommerce.factory.campaign;

import com.ecommerce.enumeration.DiscountType;

/**
 * @author serdardundar
 * @since 19/10/2019
 */

public interface CampaignFactory {

    CampaignCalculator getDiscountCalculator(DiscountType discountType);
}
