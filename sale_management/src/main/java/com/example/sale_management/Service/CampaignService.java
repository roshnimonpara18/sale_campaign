package com.example.sale_management.Service;

import com.example.sale_management.Model.Campaign;
import com.example.sale_management.Model.CampaignDiscount;
import com.example.sale_management.Model.Product;
import com.example.sale_management.Model.ResponseDTO;
import com.example.sale_management.Repository.CampaignRepository;
import com.example.sale_management.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    public ResponseDTO<Campaign> saveCampaign(Campaign campaign){
        try {
            for (CampaignDiscount discount : campaign.getCampaignDiscounts()) {
                discount.setCampaign(campaign);
                Product product = productRepository.findById(discount.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                long futurePrice = product.getCurrentPrice() - (long) (product.getCurrentPrice() * (discount.getDiscount() / 100.0));
                productService.saveHistory(product,futurePrice,campaign.getStartDate(), discount.getDiscount());
            }
            return new ResponseDTO<>(campaignRepository.save(campaign), HttpStatus.OK, "save campaign successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to save " + e.getMessage());
        }
    }


    public ResponseDTO<List<Campaign>> getAllCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAll();
        return new ResponseDTO<>(campaigns, HttpStatus.OK, "get all campaigns successfully");
    }

}
