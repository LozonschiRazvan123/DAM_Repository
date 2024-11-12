package org.example.Services;

import org.example.Model.Produs;
import org.example.Model.Stoc;
import org.example.Model.Vanzare;
import org.example.Repository.ProdusRepository;
import org.example.Repository.StocRepository;
import org.example.Repository.VanzareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessIntelligenceService {

    private final ProdusRepository produsRepository;
    private final VanzareRepository vanzareRepository;
    private final StocRepository stocRepository;

    @Autowired
    public BusinessIntelligenceService(ProdusRepository produsRepository,
                                       VanzareRepository vanzareRepository,
                                       StocRepository stocRepository) {
        this.produsRepository = produsRepository;
        this.vanzareRepository = vanzareRepository;
        this.stocRepository = stocRepository;
    }

    public double calculateTotalSalesBetween(Date startDate, Date endDate) {
        List<Vanzare> vanzari = vanzareRepository.findByDataBetween(startDate, endDate);
        return vanzari.stream().mapToDouble(Vanzare::getTotal).sum();
    }

    public List<Produs> getTopSellingProducts(int limit) {
        List<Vanzare> vanzari = vanzareRepository.findAll();

        return vanzari.stream()
                .flatMap(vanzare -> vanzare.getProduseVandute().stream())
                .collect(Collectors.groupingBy(vp -> vp.getProdus(), Collectors.summingInt(vp -> vp.getCantitate())))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(limit)
                .map(e -> e.getKey())
                .collect(Collectors.toList());
    }

    public List<Stoc> getStocksBelowMinimum() {
        return stocRepository.findAll().stream()
                .filter(stoc -> stoc.getCantitate() < stoc.getNivelMinim())
                .collect(Collectors.toList());
    }

    public double calculateTotalProfitBetween(Date startDate, Date endDate) {
        List<Vanzare> vanzari = vanzareRepository.findByDataBetween(startDate, endDate);

        return vanzari.stream()
                .flatMap(vanzare -> vanzare.getProduseVandute().stream())
                .mapToDouble(vp -> (vp.getPretUnitate() - vp.getProdus().getPretAchizitie()) * vp.getCantitate())
                .sum();
    }

    public int estimateRequiredStock(Produs produs, int perioadaZile) {
        Date startDate = new Date(System.currentTimeMillis() - (long) perioadaZile * 24 * 60 * 60 * 1000);
        List<Vanzare> vanzariRecente = vanzareRepository.findByDataAfter(startDate);

        int totalCantitateVanduta = vanzariRecente.stream()
                .flatMap(vanzare -> vanzare.getProduseVandute().stream())
                .filter(vp -> vp.getProdus().equals(produs))
                .mapToInt(vp -> vp.getCantitate())
                .sum();

        int stocRecomandat = (totalCantitateVanduta / perioadaZile) * 30;
        return stocRecomandat;
    }
}
