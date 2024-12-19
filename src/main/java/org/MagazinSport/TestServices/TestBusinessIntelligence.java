package org.MagazinSport.TestServices;

import jakarta.transaction.Transactional;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Stoc;
import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Repository.ProdusRepository;
import org.MagazinSport.Repository.StocRepository;
import org.MagazinSport.Repository.VanzareRepository;
import org.MagazinSport.Services.BusinessIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestBusinessIntelligence {

    @Autowired
    private BusinessIntelligenceService businessIntelligenceService;

    @Autowired
    private ProdusRepository produsRepository;

    @Autowired
    private VanzareRepository vanzareRepository;

    @Autowired
    private StocRepository stocRepository;

    @Test
    void testCalculateTotalSalesBetween() {
        Date startDate = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000); // ultimele 30 zile
        Date endDate = new Date();
        double totalSales = businessIntelligenceService.calculateTotalSalesBetween(startDate, endDate);

        // Preluăm din baza de date vânzările pentru intervalul specificat pentru verificare
        List<Vanzare> vanzari = vanzareRepository.findByDataBetween(startDate, endDate);
        double expectedTotalSales = vanzari.stream().mapToDouble(Vanzare::getTotal).sum();

        assertEquals(expectedTotalSales, totalSales, "Totalul vânzărilor ar trebui să fie calculat corect.");
    }

    /*@Test
    @Transactional
    void testGetTopSellingProducts() {
        int limit = 3;
        List<Produs> topSellingProducts = businessIntelligenceService.getTopSellingProducts(limit);

        assertNotNull(topSellingProducts, "Lista produselor vândute nu ar trebui să fie null.");
        assertTrue(topSellingProducts.size() <= limit, "Numărul de produse returnate ar trebui să fie cel mult " + limit);

        // Optional: Afișează produsele vândute
        topSellingProducts.forEach(produs -> System.out.println("Produs vândut: " + produs.getNume()));
    }*/
    @Test
    void testGetStocksBelowMinimum() {
        List<Stoc> stocksBelowMinimum = businessIntelligenceService.getStocksBelowMinimum();

        // Preluăm stocurile sub nivel minim direct din baza de date
        List<Stoc> expectedStocks = stocRepository.findAll().stream()
                .filter(stoc -> stoc.getCantitate() < stoc.getNivelMinim())
                .toList();

        assertEquals(expectedStocks.size(), stocksBelowMinimum.size(), "Numărul de stocuri sub nivel minim ar trebui să fie corect.");
    }

    /*@Test
    @Transactional
    void testCalculateTotalProfitBetween() {
        Date startDate = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000); // ultimele 30 zile
        Date endDate = new Date();
        double totalProfit = businessIntelligenceService.calculateTotalProfitBetween(startDate, endDate);

        List<Vanzare> vanzari = vanzareRepository.findByDataBetween(startDate, endDate);
        double expectedProfit = vanzari.stream()
                .flatMap(vanzare -> vanzare.getProduseVandute().stream())
                .mapToDouble(vp -> (vp.getPretUnitate() - vp.getProdus().getPretAchizitie()) * vp.getCantitate())
                .sum();

        assertEquals(expectedProfit, totalProfit, "Profitul total ar trebui să fie calculat corect.");
    }*/


    /*@Test
    @Transactional
    void testEstimateRequiredStock() {
        // Preluăm un produs existent din baza de date
        Produs produs = produsRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Nu există produse în baza de date."));

        int perioadaZile = 7; // Exemplu pentru ultima săptămână
        int estimatedStock = businessIntelligenceService.estimateRequiredStock(produs, perioadaZile);

        // Calculăm stocul estimat manual pentru verificare
        Date startDate = new Date(System.currentTimeMillis() - (long) perioadaZile * 24 * 60 * 60 * 1000);
        List<Vanzare> vanzariRecente = vanzareRepository.findByDataAfter(startDate);

        int totalCantitateVanduta = vanzariRecente.stream()
                .flatMap(vanzare -> vanzare.getProduseVandute().stream())
                .filter(vp -> vp.getProdus().equals(produs))
                .mapToInt(vp -> vp.getCantitate())
                .sum();

        int expectedStock = (totalCantitateVanduta / perioadaZile) * 30;
        assertEquals(expectedStock, estimatedStock, "Stocul estimat ar trebui să fie calculat corect.");
    }
*/
}
