package org.example.Services;

import org.example.Model.Stoc;
import org.example.Repository.StocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StocService {

    private final StocRepository stocRepository;

    @Autowired
    public StocService(StocRepository stocRepository) {
        this.stocRepository = stocRepository;
    }

    // Create
    public Stoc saveStoc(Stoc stoc) {
        return stocRepository.save(stoc);
    }

    // Read
    public List<Stoc> getAllStocuri() {
        return stocRepository.findAll();
    }

    public Optional<Stoc> getStocById(Long id) {
        return stocRepository.findById(id);
    }

    // Update
    public Stoc updateStoc(Long id, Stoc stoc) {
        if (stocRepository.existsById(id)) {
            stoc.setIdStoc(id);
            return stocRepository.save(stoc);
        }
        return null;
    }

    // Delete
    public void deleteStoc(Long id) {
        stocRepository.deleteById(id);
    }

    // Metode suplimentare
    public List<Stoc> findStocBelowQuantity(int cantitate) {
        return stocRepository.findByCantitateLessThan(cantitate);
    }
}
