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
        Optional<Furnizor> existingFurnizor = furnizorRepository.findById(id);

        if (existingFurnizor.isPresent()) {
            Furnizor furnizorToUpdate = existingFurnizor.get();
            furnizorToUpdate.setNume(furnizor.getNume());
            furnizorToUpdate.setEmail(furnizor.getEmail());
            furnizorToUpdate.setAdresa(furnizor.getAdresa());
            furnizorToUpdate.setTelefon(furnizor.getTelefon());
            return furnizorRepository.save(furnizorToUpdate);
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
