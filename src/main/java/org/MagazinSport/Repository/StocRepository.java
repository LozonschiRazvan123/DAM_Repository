package org.MagazinSport.Repository;

import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Stoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StocRepository extends JpaRepository<Stoc, Long> {
    List<Stoc> findByCantitateLessThan(int cantitate);
    Optional<Stoc> findByProdus_IdProdus(Long idProdus);
    Optional<Stoc> findByProdus(Produs produs);
    @Query("SELECT s FROM Stoc s WHERE s.cantitate < s.nivelMinim")
    List<Stoc> findStocuriCuNivelScazut();
}
