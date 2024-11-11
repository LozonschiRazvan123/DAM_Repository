package org.example.Repository;

import org.example.Model.Furnizor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FurnizorRepository extends JpaRepository<Furnizor, Long> {
    Optional<Furnizor> findByNume(String nume);
    Optional<Furnizor> findByEmail(String email);
}
