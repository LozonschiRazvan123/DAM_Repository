package org.MagazinSport.Services;

import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Stoc;
import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Repository.BusinessIntelligenceRepository;
import org.MagazinSport.Repository.ProdusRepository;
import org.MagazinSport.Repository.StocRepository;
import org.MagazinSport.Repository.VanzareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusinessIntelligenceService {

    private final ProdusRepository produsRepository;
    private final VanzareRepository vanzareRepository;
    private final StocRepository stocRepository;
    private final BusinessIntelligenceRepository businessIntelligenceRepository;

    @Autowired
    public BusinessIntelligenceService(ProdusRepository produsRepository,
                                       VanzareRepository vanzareRepository,
                                       StocRepository stocRepository, BusinessIntelligenceRepository businessIntelligenceRepository) {
        this.produsRepository = produsRepository;
        this.vanzareRepository = vanzareRepository;
        this.stocRepository = stocRepository;
        this.businessIntelligenceRepository = businessIntelligenceRepository;
    }

    public double calculateTotalSalesBetween(Date startDate, Date endDate) {
        List<Vanzare> vanzari = vanzareRepository.findByDataBetween(startDate, endDate);
        return vanzari.stream().mapToDouble(Vanzare::getTotal).sum();
    }

    public List<Object[]> getTopSellingProducts(Date startDate, Date endDate) {
        return businessIntelligenceRepository.findTopSellingProductsBetweenDates(startDate, endDate);
    }

    public List<Stoc> getStocksBelowMinimum() {
        return stocRepository.findAll().stream()
                .filter(stoc -> stoc.getCantitate() < stoc.getNivelMinim())
                .collect(Collectors.toList());
    }

    public double calculateTotalProfitBetweenAndCategory(Date startDate, Date endDate, String categorie) {
        List<Vanzare> vanzari = vanzareRepository.findByDataBetween(startDate, endDate);

        return vanzari.stream()
                .flatMap(vanzare -> vanzare.getProduseVandute().stream())
                .filter(vp -> vp.getProdus().getCategorie().equals(categorie))  // Filtrare după categorie
                .mapToDouble(vp -> (vp.getPretUnitate() - vp.getProdus().getPretAchizitie()) * vp.getCantitate())
                .sum();
    }

    public List<Map<String, Object>> getStockEstimation(Date startDate, String category) {
        List<Object[]> results = businessIntelligenceRepository.estimateRequiredStock(startDate, category);

        List<Map<String, Object>> estimates = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> estimate = new HashMap<>();
            estimate.put("id", result[0]);
            estimate.put("name", result[1]);
            estimate.put("category", result[2]);
            estimate.put("stock", result[3]);
            estimate.put("estimatedNeed", result[4]);
            estimates.add(estimate);
        }

        return estimates;
    }
}
