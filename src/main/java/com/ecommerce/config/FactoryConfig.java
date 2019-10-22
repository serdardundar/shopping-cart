package com.ecommerce.config;

import com.ecommerce.factory.campaign.CampaignFactory;
import com.ecommerce.factory.coupon.CouponFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
@Configuration
@ComponentScan(basePackages = {"com.ecommerce.factory"})
public class FactoryConfig {

    @Bean
    public FactoryBean campaignFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(CampaignFactory.class);
        return factoryBean;
    }

    @Bean
    public FactoryBean couponFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(CouponFactory.class);
        return factoryBean;
    }
}
