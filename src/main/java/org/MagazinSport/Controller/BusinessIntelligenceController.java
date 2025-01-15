package org.MagazinSport.Controller;

import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Services.BusinessIntelligenceService;
import org.MagazinSport.Services.ProdusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/business-intelligence")
public class  BusinessIntelligenceController {

    private final BusinessIntelligenceService businessIntelligenceService;
    private final ProdusService produsService;

    @Autowired
    public BusinessIntelligenceController(BusinessIntelligenceService businessIntelligenceService,
                                          ProdusService produsService) {
        this.businessIntelligenceService = businessIntelligenceService;
        this.produsService = produsService;
    }
    @GetMapping("/")
    public String viewIndex() {
        return "index";
    }
/*
    @GetMapping
    public String showBusinessIntelligencePage() {
        return "business-intelligence";
    }
*/

    @GetMapping("")
    public String showDashboard(Model model) {
        model.addAttribute("view", "business-intelligence");
        return "business-intelligence";
    }

    @GetMapping("/total-sales")
    public String showTotalSales(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                 Model model) {

        if (startDate != null && endDate != null) {
            double totalSales = businessIntelligenceService.calculateTotalSalesBetween(startDate, endDate);
            model.addAttribute("totalSales", totalSales);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        }

        model.addAttribute("view", "totalSales");
        return "business-intelligence";
    }

    @GetMapping("/top-selling-products")
    public String getTopSellingProducts(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model) {

        if (startDate == null || endDate == null) {
            model.addAttribute("error", "Selectați o perioadă pentru a vizualiza produsele top vânzări.");
            model.addAttribute("view", "topProducts");
            return "business-intelligence";
        }

        List<Object[]> topProducts = businessIntelligenceService.getTopSellingProducts(startDate, endDate);
        model.addAttribute("view", "topProducts");
        model.addAttribute("topProducts", topProducts);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "business-intelligence";
    }


    // Pagina pentru Stocuri Scăzute
    /*@GetMapping("/low-stock")
    public String showLowStock(@RequestParam(required = false) Integer threshold,
                               Model model) {
        if (threshold != null) {
            List<Stoc> lowStockItems = businessIntelligenceService.getStocksBelowMinimum(threshold);
            model.addAttribute("lowStockItems", lowStockItems);
            model.addAttribute("threshold", threshold);
        }
        model.addAttribute("view", "lowStock");
        return "business-intelligence";
    }*/

    @GetMapping("/total-profit")
    public String showTotalProfit(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                  @RequestParam(required = false) String categorie, // Adăugăm parametru pentru categorie
                                  Model model) {
        if (startDate != null && endDate != null && categorie != null) {
            double totalProfit = businessIntelligenceService.calculateTotalProfitBetweenAndCategory(startDate, endDate, categorie);
            List<String> categories = produsService.getCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("totalProfit", totalProfit);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("categorie", categorie);
        } else {
            List<String> categories = produsService.getCategories();
            model.addAttribute("categories", categories);
        }

        model.addAttribute("view", "totalProfit");
        return "business-intelligence";
    }


    @GetMapping("/estimate-stock")
    public String showEstimateStock(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "category", required = false) String category,
            Model model) {

        List<Map<String, Object>> estimatedStock = businessIntelligenceService.getStockEstimation(startDate, category);

        List<String> categories = produsService.getCategories();

        model.addAttribute("estimatedStock", estimatedStock);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("view", "estimateStock");
        return "business-intelligence";
    }
}
