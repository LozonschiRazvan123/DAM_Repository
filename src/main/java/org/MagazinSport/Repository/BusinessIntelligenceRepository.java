package org.MagazinSport.Repository;

import org.MagazinSport.Model.Produs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BusinessIntelligenceRepository extends JpaRepository<Produs, Long> {

    @Query("SELECT SUM(v.total) FROM Vanzare v WHERE v.data BETWEEN :startDate AND :endDate")
    Double calculateTotalSalesBetween(Date startDate, Date endDate);

    @Query("SELECT vp.produs, SUM(vp.cantitate) as totalCantitate, vp.vanzare.data " +
            "FROM VanzareProdus vp " +
            "WHERE vp.vanzare.data BETWEEN :startDate AND :endDate " +
            "GROUP BY vp.produs, vp.vanzare.data " +
            "ORDER BY totalCantitate DESC")
    List<Object[]> findTopSellingProductsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT SUM((vp.pretUnitate - vp.produs.pretAchizitie) * vp.cantitate) " +
            "FROM VanzareProdus vp " +
            "JOIN vp.vanzare v " +
            "WHERE v.data BETWEEN :startDate AND :endDate " +
            "AND vp.produs.categorie = :categorie")
    Double calculateTotalProfitBetweenAndCategory(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("categorie") String categorie);

    @Query("SELECT vp.produs.id, vp.produs.nume, vp.produs.categorie, p.stoc, SUM(vp.cantitate) FROM VanzareProdus vp " +
            "JOIN vp.vanzare v " +
            "JOIN vp.produs p " +
            "WHERE v.data > :startDate " +
            "AND (:category IS NULL OR p.categorie = :category) " +
            "GROUP BY vp.produs.id, p.categorie, p.stoc")
    List<Object[]> estimateRequiredStock(@Param("startDate") Date startDate, @Param("category") String category);
}
