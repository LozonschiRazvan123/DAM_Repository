package org.MagazinSport.Repository;

import org.MagazinSport.Model.Produs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BusinessIntelligenceRepository extends JpaRepository<Produs, Long> {

    @Query("SELECT SUM(v.total) FROM Vanzare v WHERE v.data BETWEEN :startDate AND :endDate")
    Double calculateTotalSalesBetween(Date startDate, Date endDate);

    @Query("SELECT vp.produs, SUM(vp.cantitate) as totalCantitate FROM VanzareProdus vp GROUP BY vp.produs ORDER BY totalCantitate DESC")
    List<Object[]> findTopSellingProducts();

    @Query("SELECT s FROM Stoc s WHERE s.cantitate < s.nivelMinim")
    List<Object[]> findStocksBelowMinimum();

    @Query("SELECT SUM((vp.pretUnitate - vp.produs.pretAchizitie) * vp.cantitate) FROM VanzareProdus vp JOIN vp.vanzare v WHERE v.data BETWEEN :startDate AND :endDate")
    Double calculateTotalProfitBetween(Date startDate, Date endDate);

    @Query("SELECT vp.produs.id, SUM(vp.cantitate) FROM VanzareProdus vp JOIN vp.vanzare v WHERE v.data > :startDate GROUP BY vp.produs.id")
    List<Object[]> estimateRequiredStock(Date startDate);
}
