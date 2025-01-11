package org.MagazinSport.Services;

import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Stoc;
import org.MagazinSport.Repository.StocRepository;
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

    public boolean existsById(Long id) {
        return stocRepository.existsById(id);
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
        } else {
            throw new NoSuchElementException("Stoc with ID " + id + " does not exist.");
        }
    }

    // Delete
    public void deleteStoc(Long id) {
        if (stocRepository.existsById(id)) {
            stocRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Stoc with ID " + id + " does not exist.");
        }
    }

    // Metode suplimentare
    public List<Stoc> findStocBelowQuantity(int cantitate) {
        return stocRepository.findByCantitateLessThan(cantitate);
    }

    public Stoc addStoc(Stoc stoc) {
        return stocRepository.save(stoc);
    }

    public boolean isBelowMinimumLevel(Stoc stoc) {
        Stoc stock = addStoc(stoc);
        return stock.isBelowMinimumLevel();
    }

    public Stoc getFirstStocByProdusId(Long produsId) {
        return stocRepository.findByProdus_IdProdus(produsId)
                .orElseThrow(() -> new NoSuchElementException("Stoc not found for product ID: " + produsId));
    }

    public Optional<Stoc> findByProdus(Produs produs) {
        return stocRepository.findByProdus(produs);
    }
    public Stoc saveStoc(Stoc stoc) {
        return stocRepository.save(stoc);
    }
}
