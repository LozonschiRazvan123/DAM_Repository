package org.example.Services;

import org.example.Model.VanzareProdus;
import org.example.Repository.VanzareProdusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VanzareProdusService {

    private final VanzareProdusRepository vanzareProdusRepository;

    @Autowired
    public VanzareProdusService(VanzareProdusRepository vanzareProdusRepository) {
        this.vanzareProdusRepository = vanzareProdusRepository;
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
        return vanzareProdusRepository.findByProdusId(produsId);
    }
}
