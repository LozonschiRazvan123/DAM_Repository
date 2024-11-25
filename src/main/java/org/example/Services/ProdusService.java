package org.example.Services;

import org.example.Model.Produs;
import org.example.Repository.ProdusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdusService {

    private final ProdusRepository produsRepository;

    @Autowired
    public ProdusService(ProdusRepository produseRepository) {
        this.produsRepository = produseRepository;
    }

    // Create
    public Produs saveProduse(Produs produse) {
        return produsRepository.save(produse);
    }

    // Read
    public List<Produs> getAllProduse() {
        return produsRepository.findAll();
    }

    public Optional<Produs> getProduseById(Long id) {
        return produsRepository.findById(id);
    }

    // Update
    public Produs updateProduse(Long id, Produs produse) {
        if (produsRepository.existsById(id)) {
            produse.setIdProdus(id);
            return produsRepository.save(produse);
        }
        return null;
    }

    public List<Produs> findProduseByFurnizorId(Long idFurnizor) {
        return produsRepository.findByFurnizor_IdFurnizor(idFurnizor);
    }

    // Delete
    public void deleteProduse(Long id) {
        produsRepository.deleteById(id);
    }

    // Metode suplimentare
    public List<Produs> findProduseByCategorie(String categorie) {
        return produsRepository.findByCategorie(categorie);
    }

    public List<Produs> findProduseByStocLessThan(int stoc) {
        return produsRepository.findByStocLessThan(stoc);
    }
}
