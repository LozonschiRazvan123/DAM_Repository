/*
package org.example.TestServices;

import org.example.Model.Produs;
import org.example.Model.Stoc;
import org.example.Model.Vanzare;
import org.example.Model.VanzareProdus;
import org.example.Repository.BusinessIntelligenceRepository;
import org.example.Repository.ProdusRepository;
import org.example.Repository.StocRepository;
import org.example.Repository.VanzareRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BusinessIntelligenceTest {

    @Autowired
    private BusinessIntelligenceRepository businessIntelligenceRepository;

    @Autowired
    private VanzareRepository vanzareRepository;

    @Autowired
    private StocRepository stocRepository;

    @Autowired
    private ProdusRepository produsRepository;

    @BeforeEach
    void setUp() {
        Produs produs1 = new Produs(1L,"Racheta Tenis", "Echipament Sportiv", 300.0, 150.0, 10);
        Produs produs2 = new Produs(2L,"Minge Fotbal", "Echipament Sportiv", 100.0, 50.0, 20);
        Vanzare vanzare1 = new Vanzare();
        vanzare1.setData(new Date(System.currentTimeMillis() - 86400000));
        vanzare1.setTotal(1500.0);
        vanzareRepository.save(vanzare1);

        VanzareProdus vanzareProdus1 = new VanzareProdus();
        vanzareProdus1.setProdus(produs1);
        vanzareProdus1.setCantitate(5);
        vanzareProdus1.setPretUnitate(300.0);
        vanzareProdus1.setVanzare(vanzare1);

        Vanzare vanzare2 = new Vanzare();
        vanzare2.setData(new Date(System.currentTimeMillis() - 43200000));
        vanzare2.setTotal(1000.0);
        vanzareRepository.save(vanzare2);

        VanzareProdus vanzareProdus2 = new VanzareProdus();
        vanzareProdus2.setProdus(produs2);
        vanzareProdus2.setCantitate(10);
        vanzareProdus2.setPretUnitate(100.0);
        vanzareProdus2.setVanzare(vanzare2);

        // Salvăm vânzările și produsele
        vanzareRepository.saveAll(List.of(vanzare1, vanzare2));
    }

    @Test
    public void testCalculateTotalSalesBetween() {
        Date startDate = new Date(System.currentTimeMillis() - 172800000);
        Date endDate = new Date();

        Double totalSales = businessIntelligenceRepository.calculateTotalSalesBetween(startDate, endDate);
        Assertions.assertEquals(2500.0, totalSales);
    }

    @Test
    public void testCalculateTotalSalesBetween_OutOfRange() {
        Date startDate = new Date(System.currentTimeMillis() - 259200000);
        Date endDate = new Date(System.currentTimeMillis() - 172800000);

        Double totalSales = businessIntelligenceRepository.calculateTotalSalesBetween(startDate, endDate);
        Assertions.assertEquals(0.0, totalSales);
    }

    @Test
    public void testFindTopSellingProducts() {
        List<Object[]> topSellingProducts = businessIntelligenceRepository.findTopSellingProducts();

        Assertions.assertTrue(!topSellingProducts.isEmpty());
        Assertions.assertTrue(topSellingProducts.get(0)[0] instanceof Produs);

        Produs topProduct = (Produs) topSellingProducts.get(0)[0];
        Long topQuantity = (Long) topSellingProducts.get(0)[1];

        Assertions.assertEquals("Minge Fotbal", topProduct.getNume());
        Assertions.assertEquals(10L, topQuantity);
    }

    @Test
    public void testFindStocksBelowMinimum() {
        Produs produs1 = produsRepository.findAll().get(0);
        Produs produs2 = produsRepository.findAll().get(1);

        Stoc stoc1 = new Stoc();
        stoc1.setProdus(produs1);
        stoc1.setCantitate(2);
        stoc1.setNivelMinim(5);

        Stoc stoc2 = new Stoc();
        stoc2.setProdus(produs2);
        stoc2.setCantitate(15);
        stoc2.setNivelMinim(10);

        stocRepository.saveAll(List.of(stoc1, stoc2));

        List<Object[]> stocksBelowMinimum = businessIntelligenceRepository.findStocksBelowMinimum();
        Assertions.assertTrue(!stocksBelowMinimum.isEmpty());
        Assertions.assertTrue(((Stoc) stocksBelowMinimum.get(0)[0]).getCantitate() < 5);
    }

    @Test
    public void testCalculateTotalProfitBetween() {
        Date startDate = new Date(System.currentTimeMillis() - 172800000);
        Date endDate = new Date();

        Double totalProfit = businessIntelligenceRepository.calculateTotalProfitBetween(startDate, endDate);
        Assertions.assertEquals(1250.0, totalProfit);
    }

    @Test
    public void testEstimateRequiredStock() {
        Date startDate = new Date(System.currentTimeMillis() - 172800000);
        List<Object[]> estimatedStock = businessIntelligenceRepository.estimateRequiredStock(startDate);

        Assertions.assertTrue(!estimatedStock.isEmpty());

        for (Object[] result : estimatedStock) {
            Long productId = (Long) result[0];
            Long requiredQuantity = (Long) result[1];
            Assertions.assertTrue(requiredQuantity > 0);
        }
    }
}
*/
