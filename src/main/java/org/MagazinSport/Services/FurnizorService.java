package org.MagazinSport.Services;

import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Repository.FurnizorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FurnizorService {

    private final FurnizorRepository furnizorRepository;

    @Autowired
    public FurnizorService(FurnizorRepository furnizorRepository) {
        this.furnizorRepository = furnizorRepository;
    }

    // Create
    public Furnizor saveFurnizor(Furnizor furnizor) {
        return furnizorRepository.save(furnizor);
    }

    // Read
    public List<Furnizor> getAllFurnizori() {
        return furnizorRepository.findAll();
    }

    public Optional<Furnizor> getFurnizorById(Long id) {
        return furnizorRepository.findById(id);
    }

    // Update
    public Furnizor updateFurnizor(Long id, Furnizor furnizor) {
        if (furnizorRepository.existsById(id)) {
            furnizor.setIdFurnizor(id);
            return furnizorRepository.save(furnizor);
        }
        return null;
    }

    // Delete
    public void deleteFurnizor(Long id) {
        furnizorRepository.deleteById(id);
    }

    // Metode suplimentare
    public Optional<Furnizor> findFurnizorByNume(String nume) {
        return furnizorRepository.findByNume(nume);
    }

    public Optional<Furnizor> findFurnizorByEmail(String email) {
        return furnizorRepository.findByEmail(email);
    }
}
