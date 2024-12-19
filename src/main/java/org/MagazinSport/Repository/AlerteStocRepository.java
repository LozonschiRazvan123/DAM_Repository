package org.MagazinSport.Repository;

import org.MagazinSport.Model.AlerteStoc;
import org.MagazinSport.Model.Produs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlerteStocRepository extends JpaRepository<AlerteStoc, Long> {
    List<AlerteStoc> findByActivTrue();
    List<AlerteStoc> findByActiv(Boolean activ);

    boolean existsByProdus(Produs produs);
}