package org.MagazinSport.Repository;

import org.MagazinSport.Model.Vanzare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VanzareRepository extends JpaRepository<Vanzare, Long> {
    List<Vanzare> findByData(Date data);
    List<Vanzare> findByDataAfter(Date data);
    List<Vanzare> findByDataBetween(Date startDate, Date endDate);

}
