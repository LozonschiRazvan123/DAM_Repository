package org.example.Repository;

import org.example.Model.ComandaAprovizionare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ComandaAprovizionareRepository extends JpaRepository<ComandaAprovizionare, Long> {
    List<ComandaAprovizionare> findByStatus(String status);
    List<ComandaAprovizionare> findByDataComandaAfter(Date date);
    List<ComandaAprovizionare> findByFurnizorId(Long furnizorId);
}
