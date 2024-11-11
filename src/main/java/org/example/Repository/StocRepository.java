package org.example.Repository;

import org.example.Model.Stoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocRepository extends JpaRepository<Stoc, Long> {
    List<Stoc> findByCantitateLessThan(int cantitate);
    List<Stoc> findByProdusId(Long produsId);
}
