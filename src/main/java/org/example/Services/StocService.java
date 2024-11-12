package org.example.Services;

import org.example.Model.Produs;
import org.example.Model.Stoc;
import org.example.Repository.StocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Stoc findOrCreateStoc(Produs produs) {
        return stocRepository.findByProdus(produs)
                .orElseGet(() -> {
                    Stoc stoc = new Stoc();
                    stoc.setProdus(produs);
                    stoc.setCantitate(0);
                    stoc.setNivelMinim(10);
                    stoc.setDataUltimeiModificari(new Date());
                    return stocRepository.save(stoc);
                });
    }

    public void incrementStock(Produs produs, int cantitate) {
        Stoc stoc = findOrCreateStoc(produs);
        stoc.incrementStock(cantitate);
        stoc.setDataUltimeiModificari(new Date());
        stocRepository.save(stoc);
    }

    public void decrementStock(Produs produs, int cantitate) {
        Stoc stoc = findOrCreateStoc(produs);
        stoc.decrementStock(cantitate);
        stoc.setDataUltimeiModificari(new Date());
        stocRepository.save(stoc);
    }
    public void updateStock(Produs produs, int newQuantity) {
        Stoc stoc = findOrCreateStoc(produs);
        stoc.updateStock(newQuantity);
        stoc.setDataUltimeiModificari(new Date());
        stocRepository.save(stoc);
    }
    public boolean isBelowMinimumLevel(Produs produs) {
        Stoc stoc = findOrCreateStoc(produs);
        return stoc.isBelowMinimumLevel();
    }

    public Stoc getFirstStocByProdusId(Long produsId) {
        Optional<Stoc> stocList = stocRepository.findByProdusId(produsId);
        if (!stocList.isEmpty()) {
            return stocList.get();
        } else {
            throw new NoSuchElementException("Stoc not found for product ID: " + produsId);
        }
    }
}
