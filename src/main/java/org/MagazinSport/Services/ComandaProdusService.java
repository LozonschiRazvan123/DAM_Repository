package org.MagazinSport.Services;

import org.MagazinSport.Model.ComandaProdus;
import org.MagazinSport.Repository.ComandaProdusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComandaProdusService {

    private final ComandaProdusRepository comandaProdusRepository;

    @Autowired
    public ComandaProdusService(ComandaProdusRepository comandaProdusRepository) {
        this.comandaProdusRepository = comandaProdusRepository;
    }

    // Create
    public void saveComandaProdus(List<ComandaProdus> comandaProduse) {
        comandaProdusRepository.saveAll(comandaProduse); // Folosim saveAll pentru a salva lista de produse
    }

    // Read - Get All
    public List<ComandaProdus> getAllComandaProduse() {
        return comandaProdusRepository.findAll();
    }

    // Read - Get by ID
    public Optional<ComandaProdus> getComandaProdusById(Long id) {
        return comandaProdusRepository.findById(id);
    }

    // Update
    public ComandaProdus updateComandaProdus(Long id, ComandaProdus comandaProdus) {
        if (comandaProdusRepository.existsById(id)) {
            comandaProdus.setId(id);
            return comandaProdusRepository.save(comandaProdus);
        }
        return null;
    }

    // Delete
    public void deleteComandaProdus(Long id) {
        comandaProdusRepository.deleteById(id);
    }

    // Custom Query - Get by ComandaAprovizionare ID
    public List<ComandaProdus> getComandaProduseByComandaId(Long comandaId) {
        return comandaProdusRepository.findByComandaAprovizionare_IdComandaAprovizionare(comandaId);
    }

    // Custom Query - Get by Produs ID
    public List<ComandaProdus> getComandaProduseByProdusId(Long produsId) {
        return comandaProdusRepository.findByProdus_IdProdus(produsId);
    }
}
