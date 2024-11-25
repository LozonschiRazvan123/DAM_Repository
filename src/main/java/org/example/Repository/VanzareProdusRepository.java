package org.example.Repository;

import org.example.Model.VanzareProdus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VanzareProdusRepository extends JpaRepository<VanzareProdus, Long> {
    List<VanzareProdus> findByVanzareId(Long vanzareId);
    List<VanzareProdus> findByProdus_IdProdus(Long produsId);
}
