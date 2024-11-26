package org.MagazinSport.Services;

import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Repository.VanzareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VanzareService {

    private final VanzareRepository vanzareRepository;

    @Autowired
    public VanzareService(VanzareRepository vanzareRepository) {
        this.vanzareRepository = vanzareRepository;
    }

    // Create
    public Vanzare saveVanzare(Vanzare vanzare) {
        return vanzareRepository.save(vanzare);
    }

    // Read
    public List<Vanzare> getAllVanzari() {
        return vanzareRepository.findAll();
    }

    public Optional<Vanzare> getVanzareById(Long id) {
        return vanzareRepository.findById(id);
    }

    // Update
    public Vanzare updateVanzare(Long id, Vanzare vanzare) {
        if (vanzareRepository.existsById(id)) {
            vanzare.setId(id);
            return vanzareRepository.save(vanzare);
        }
        return null;
    }

    // Delete
    public void deleteVanzare(Long id) {
        vanzareRepository.deleteById(id);
    }

    // Metode suplimentare
    public List<Vanzare> findVanzariAfterDate(Date data) {
        return vanzareRepository.findByDataAfter(data);
    }
}
