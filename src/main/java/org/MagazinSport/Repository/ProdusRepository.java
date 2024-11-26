package org.MagazinSport.Repository;

import org.MagazinSport.Model.Produs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
    @Repository
    public interface ProdusRepository extends JpaRepository<Produs, Long> {
        List<Produs> findByCategorie(String categorie);
        List<Produs> findByStocLessThan(int stoc);
        List<Produs> findByFurnizor_IdFurnizor(Long idFurnizor);
    }