package org.MagazinSport.Repository;

import org.MagazinSport.Model.ComandaProdus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaProdusRepository extends JpaRepository<ComandaProdus, Long> {
    List<ComandaProdus> findByComandaAprovizionare_IdComandaAprovizionare(Long comandaAprovizionareId);
    List<ComandaProdus> findByProdus_IdProdus(Long produsId);
    @Modifying
    @Query("UPDATE ComandaProdus cp SET cp.produs = null WHERE cp.produs.idProdus = :idProdus")
    void removeProductFromOrders(@Param("idProdus") Long idProdus);
}
