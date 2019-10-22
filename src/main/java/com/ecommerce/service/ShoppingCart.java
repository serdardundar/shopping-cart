package com.ecommerce.service;

import com.ecommerce.domain.Campaign;
import com.ecommerce.domain.Coupon;
import com.ecommerce.domain.Product;
import com.ecommerce.domain.ShoppingCartItem;
import com.ecommerce.factory.campaign.CampaignCalculator;
import com.ecommerce.factory.campaign.CampaignFactory;
import com.ecommerce.factory.coupon.CouponCalculator;
import com.ecommerce.factory.coupon.CouponFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * This class is aggregate root for a simple ShoppingCart implementation
 *
 * @author serdardundar
 * @since 19/10/2019
 */
@Component
@RequiredArgsConstructor
@Scope("prototype")
public class ShoppingCart {

    private final CampaignFactory campaignFactory;
    private final CouponFactory couponFactory;
    private final ShoppingCartPrinter shoppingCartPrinter;
    private DeliveryCostCalculator deliveryCostCalculator;
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal campaignDiscount = BigDecimal.ZERO;
    private BigDecimal couponDiscount = BigDecimal.ZERO;
    private List<Campaign> campaignList = new ArrayList<>();
    private List<BigDecimal> campaignDiscountList = new ArrayList<>();

    @Value("${delivery.cost.per.delivery}")
    private BigDecimal costPerDelivery;

    @Value("${delivery.cost.per.product}")
    private BigDecimal costPerProduct;

    @Value("${delivery.fixed.cost}")
    private BigDecimal fixedCost;

    @PostConstruct
    private void init() {
        deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
    }

    public void addItem(Product item, int quantity) {
        if (productExistsInCart(item)) {
            increaseQuantityOfProductInCart(item, quantity);
        } else {
            shoppingCartItems.add(new ShoppingCartItem(item, quantity));
        }
        totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
        prepareCurrentCampaignDiscount();
    }

    public BigDecimal getCampaignDiscount() {
        return campaignDiscount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal getCouponDiscount() {
        return couponDiscount;
    }

    public BigDecimal getTotalAmountAfterDiscounts() {
        return getTotalPrice().subtract(getCampaignDiscount()).subtract(getCouponDiscount()).add(getDeliveryCost());
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCostCalculator.calculateFor(this);
    }

    private boolean productExistsInCart(Product item) {
        return shoppingCartItems.stream().anyMatch(p -> p.getProduct().getTitle().equals(item.getTitle()));
    }

    private void increaseQuantityOfProductInCart(Product item, int quantity) {
        shoppingCartItems.stream().filter(p -> p.getProduct().equals(item)).forEach(t -> t.increaseQuantity(quantity));
    }

    public void applyDiscounts(List<Campaign> campaigns) {
        campaignList.addAll(campaigns);
        findBestDiscountForCampaigns(campaigns);
    }

    public void applyCoupon(Coupon coupon) {
        CouponCalculator couponCalculator = couponFactory.getCouponCalculator(coupon.getDiscountType());
        couponDiscount = couponCalculator.calculateCouponDiscountedPrice(coupon, totalPrice.subtract(campaignDiscount));
    }

    public void emptyCartItems() {
        shoppingCartItems.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private void findBestDiscountForCampaigns(List<Campaign> campaigns) {
        campaigns.forEach(campaign -> {
            CampaignCalculator campaignCalculator = campaignFactory.getDiscountCalculator(campaign.getDiscountType());
            BigDecimal discountPrice = campaignCalculator.calculateCampaignDiscountedPrice(campaign, shoppingCartItems);
            campaignDiscountList.add(discountPrice);
        });

        Optional<BigDecimal> highestDiscount = campaignDiscountList.stream().max(Comparator.naturalOrder());
        highestDiscount.ifPresent(bigDecimal -> campaignDiscount = bigDecimal);
    }

    private void prepareCurrentCampaignDiscount() {
        campaignDiscountList.clear();
        findBestDiscountForCampaigns(campaignList);
    }

    void print() {
        shoppingCartPrinter.printCart(this);
    }
}
