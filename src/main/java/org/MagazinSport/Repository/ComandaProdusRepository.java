package org.MagazinSport.Repository;

import org.MagazinSport.Model.ComandaProdus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaProdusRepository extends JpaRepository<ComandaProdus, Long> {
    List<ComandaProdus> findByComandaAprovizionare_IdComandaAprovizionare(Long comandaAprovizionareId);
    List<ComandaProdus> findByProdus_IdProdus(Long produsId);
}
