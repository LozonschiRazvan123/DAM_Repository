package org.example.Repository;

import org.example.Model.ComandaProdus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaProdusRepository extends JpaRepository<ComandaProdus, Long> {
    List<ComandaProdus> findByComandaAprovizionareId(Long comandaId);
    List<ComandaProdus> findByProdusId(Long produsId);
}