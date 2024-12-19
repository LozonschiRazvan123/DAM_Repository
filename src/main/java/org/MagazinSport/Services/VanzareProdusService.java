package org.MagazinSport.Services;

import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Model.VanzareProdus;
import org.MagazinSport.Repository.VanzareProdusRepository;
import org.MagazinSport.Repository.VanzareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VanzareProdusService {

    private final VanzareProdusRepository vanzareProdusRepository;
    private final VanzareRepository vanzareRepository;

    @Autowired
    public VanzareProdusService(VanzareProdusRepository vanzareProdusRepository, VanzareRepository vanzareRepository) {
        this.vanzareProdusRepository = vanzareProdusRepository;
        this.vanzareRepository = vanzareRepository;
    }

    public Vanzare getVanzareById(Long id) {
        return vanzareRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vanzare not found"));
    }

    // Create
    public VanzareProdus saveVanzareProdus(VanzareProdus vanzareProdus) {
        return vanzareProdusRepository.save(vanzareProdus);
    }

    // Read - Get All
    public List<VanzareProdus> getAllVanzareProduse() {
        return vanzareProdusRepository.findAll();
    }

    // Read - Get by ID
    public Optional<VanzareProdus> getVanzareProdusById(Long id) {
        return vanzareProdusRepository.findById(id);
    }

    // Update
    public VanzareProdus updateVanzareProdus(Long id, VanzareProdus vanzareProdus) {
        if (vanzareProdusRepository.existsById(id)) {
            vanzareProdus.setId(id);
            return vanzareProdusRepository.save(vanzareProdus);
        }
        return null;
    }

    // Delete
    public void deleteVanzareProdus(Long id) {
        vanzareProdusRepository.deleteById(id);
    }

    // Custom Query - Get by Vanzare ID
    public List<VanzareProdus> getProduseByVanzareId(Long vanzareId) {
        return vanzareProdusRepository.findByVanzareId(vanzareId);
    }

    // Custom Query - Get by Produs ID
    public List<VanzareProdus> getProduseByProdusId(Long produsId) {
        return vanzareProdusRepository.findByProdus_IdProdus(produsId);
    }
}
