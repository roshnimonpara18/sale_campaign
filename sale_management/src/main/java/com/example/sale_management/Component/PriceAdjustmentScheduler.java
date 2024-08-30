package com.example.sale_management.Component;

import com.example.sale_management.Model.Campaign;
import com.example.sale_management.Model.CampaignDiscount;
import com.example.sale_management.Model.PriceHistory;
import com.example.sale_management.Model.Product;
import com.example.sale_management.Repository.CampaignRepository;
import com.example.sale_management.Repository.PriceHistoryRepository;
import com.example.sale_management.Repository.ProductRepository;
import com.example.sale_management.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PriceAdjustmentScheduler {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    PriceHistoryRepository priceHistoryRepository;

    @Scheduled(cron = "0 40 10 * * *")
    public void adjustProductPrices() {
        System.out.println("Scheduled task running ");
        
        LocalDate today = LocalDate.now();
        List<Campaign> activeSales = campaignRepository.findAllByStartDate(today);

        for (Campaign campaign : activeSales) {
            List<CampaignDiscount> discounts = campaign.getCampaignDiscounts();
            for (CampaignDiscount discount : discounts) {
                Product product = productRepository.findById(discount.getProductId()).orElse(null);
                if (product != null) {
                    float discountAmount = (product.getCurrentPrice() * (discount.getDiscount() / 100));
                    double newPrice = (product.getCurrentPrice() - discountAmount);

                    if (newPrice >= 0) {
                        product.setCurrentPrice((long) newPrice);
                        product.setDiscount(discount.getDiscount());
                        productRepository.save(product);
                        productService.saveHistory(product, (long) newPrice, LocalDate.now(), discountAmount);
                    }
                }
            }
        }
    }


    @Scheduled(cron = "0 41 10 * * *")
    public void revertPrice(){
        System.out.println("Scheduled task end");
        LocalDate today = LocalDate.now();
        List<Campaign> endedSales = campaignRepository.findAllByEndDate(today);

        for (Campaign campaign : endedSales) {
            List<CampaignDiscount> discounts = campaign.getCampaignDiscounts();
            for (CampaignDiscount discount : discounts) {
                Product product = productRepository.findById(discount.getProductId()).orElse(null);
                if (product != null) {

                    LocalDate date = campaign.getStartDate();
                    PriceHistory priceHistory = priceHistoryRepository.findTopByProductIdAndDate(product.getId(), date);
                    if (priceHistory != null) {
                        double previousPrice = priceHistory.getDiscountPrice();
                        product.setCurrentPrice((long) (priceHistory.getPrice() + previousPrice));
                        productRepository.save(product);
                        productService.saveHistory(product, (long) (priceHistory.getPrice() + previousPrice), LocalDate.now(), priceHistory.getDiscountPrice());
                    }

                }
            }
        }
    }
}
