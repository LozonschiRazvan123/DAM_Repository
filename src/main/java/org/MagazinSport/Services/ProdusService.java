package org.MagazinSport.Services;

import org.MagazinSport.Model.Produs;
import org.MagazinSport.Repository.ProdusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdusService {

    private final ProdusRepository produsRepository;

    @Autowired
    public ProdusService(ProdusRepository produsRepository) {
        this.produsRepository = produsRepository;
    }

    // Create
    public Produs saveProdus(Produs produs) {
        // Validare suplimentară înainte de salvare, dacă este necesar
        if (produs.getStoc() < 0) {
            throw new IllegalArgumentException("Stocul nu poate fi negativ!");
        }
        return produsRepository.save(produs);
    }

    // Read
    public List<Produs> getAllProduse() {
        return produsRepository.findAll();
    }

    public Optional<Produs> getProdusById(Long id) {
        return produsRepository.findById(id);
    }

    // Update
    public Produs updateProdus(Long id, Produs produs) {
        if (!produsRepository.existsById(id)) {
            throw new IllegalArgumentException("Produsul cu ID-ul " + id + " nu a fost găsit!");
        }

        // Actualizează doar câmpurile care se pot modifica
        produs.setIdProdus(id);

        // Validare suplimentară înainte de salvare
        if (produs.getStoc() < 0) {
            throw new IllegalArgumentException("Stocul nu poate fi negativ!");
        }

        return produsRepository.save(produs);
    }

    // Delete
    public void deleteProdus(Long id) {
        if (!produsRepository.existsById(id)) {
            throw new IllegalArgumentException("Produsul cu ID-ul " + id + " nu există!");
        }
        produsRepository.deleteById(id);
    }

    // Căutări suplimentare
    public List<Produs> findProduseByFurnizorId(Long idFurnizor) {
        return produsRepository.findByFurnizor_IdFurnizor(idFurnizor);
    }

    public List<Produs> findProduseByCategorie(String categorie) {
        return produsRepository.findByCategorie(categorie);
    }

    public List<Produs> findProduseByStocLessThan(int stoc) {
        return produsRepository.findByStocLessThan(stoc);
    }
}