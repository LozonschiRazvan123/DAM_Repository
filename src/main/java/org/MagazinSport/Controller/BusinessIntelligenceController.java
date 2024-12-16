package org.MagazinSport.Controller;

import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Stoc;
import org.MagazinSport.Services.BusinessIntelligenceService;
import org.MagazinSport.Services.ProdusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/business-intelligence")
public class  BusinessIntelligenceController {

    private final BusinessIntelligenceService businessIntelligenceService;
    private final ProdusService produsService;

    @Autowired
    public BusinessIntelligenceController(BusinessIntelligenceService businessIntelligenceService,
                                          ProdusService produsService) {
        this.businessIntelligenceService = businessIntelligenceService;
        this.produsService = produsService;
    }

    @GetMapping("/total-sales")
    public double calculateTotalSales(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return businessIntelligenceService.calculateTotalSalesBetween(startDate, endDate);
    }

    @GetMapping("/top-selling-products")
    public List<Produs> getTopSellingProducts(@RequestParam(value = "limit", defaultValue = "5") int limit) {
        return businessIntelligenceService.getTopSellingProducts(limit);
    }

    @GetMapping("/low-stock")
    public List<Stoc> getStocksBelowMinimum() {
        return businessIntelligenceService.getStocksBelowMinimum();
    }

    @GetMapping("/total-profit")
    public double calculateTotalProfit(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return businessIntelligenceService.calculateTotalProfitBetween(startDate, endDate);
    }

    @GetMapping("/estimate-stock")
    public int estimateRequiredStock(
            @RequestParam("produsId") Long produsId,
            @RequestParam("perioadaZile") int perioadaZile) {
        Produs produs = produsService.getProdusById(produsId)
                .orElseThrow(() -> new IllegalArgumentException("Produs not found"));
        return businessIntelligenceService.estimateRequiredStock(produs, perioadaZile);
    }
}
