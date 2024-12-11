package org.MagazinSport.Repository;

import org.MagazinSport.Model.ComandaAprovizionare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ComandaAprovizionareRepository extends JpaRepository<ComandaAprovizionare, Long> {
    List<ComandaAprovizionare> findByStatus(String status);
    @Query("SELECT c FROM ComandaAprovizionare c WHERE c.dataComanda >= :date")
    List<ComandaAprovizionare> findByDataComandaAfter(Date date);
    List<ComandaAprovizionare> findByFurnizor_IdFurnizor(Long idFurnizor);

}
