package org.MagazinSport.Repository;

import org.MagazinSport.Model.AlerteStoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlerteStocRepository extends JpaRepository<AlerteStoc, Long> {
    List<AlerteStoc> findByActiv(boolean activ);
    List<AlerteStoc> findByProdus_IdProdus(Long produsId);
    List<AlerteStoc> findByDataAlertaAfter(Date data);
}