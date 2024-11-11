package org.example.Repository;

import org.example.Model.Produs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
    @Repository
    public interface ProdusRepository extends JpaRepository<Produs, Long> {
        List<Produs> findByCategorie(String categorie);
        List<Produs> findByStocLessThan(int stoc);
        List<Produs> findByFurnizorId(Long furnizorId);
    }