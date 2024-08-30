package com.example.sale_management.Controller;

import com.example.sale_management.Model.Campaign;
import com.example.sale_management.Model.ResponseDTO;
import com.example.sale_management.Service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("campaigns")
public class CampaignController {

    @Autowired
    CampaignService campaignService;

    @PostMapping("saveCampaign")
    public ResponseDTO<Campaign> saveCampaign(@RequestBody Campaign campaign) {
        return campaignService.saveCampaign(campaign);
    }

    @GetMapping("getCampaign")
    public ResponseDTO<List<Campaign>> getCampaign() {
        return campaignService.getAllCampaigns();
    }
}
