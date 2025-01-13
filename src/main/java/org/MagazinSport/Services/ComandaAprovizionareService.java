package org.MagazinSport.Services;

import org.MagazinSport.Model.ComandaAprovizionare;
import org.MagazinSport.Repository.ComandaAprovizionareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComandaAprovizionareService {

    private final ComandaAprovizionareRepository comandaAprovizionareRepository;

    @Autowired
    public ComandaAprovizionareService(ComandaAprovizionareRepository comandaAprovizionareRepository) {
        this.comandaAprovizionareRepository = comandaAprovizionareRepository;
    }

    @Transactional
    public ComandaAprovizionare saveComandaAprovizionare(ComandaAprovizionare comandaAprovizionare) {
        return comandaAprovizionareRepository.save(comandaAprovizionare);
    }

    // Read
    public List<ComandaAprovizionare> getAllComenziAprovizionare() {
        return comandaAprovizionareRepository.findAll();
    }

    public Optional<ComandaAprovizionare> getComandaAprovizionareById(Long id) {
        return comandaAprovizionareRepository.findById(id);
    }

    @Transactional
    public ComandaAprovizionare updateComandaAprovizionare(Long id, ComandaAprovizionare comandaAprovizionare) {
        if (comandaAprovizionareRepository.existsById(id)) {
            comandaAprovizionare.setIdComandaAprovizionare(id);
            return comandaAprovizionareRepository.save(comandaAprovizionare);
        }
        return null;
    }

    public void deleteComandaAprovizionare(Long id) {
        comandaAprovizionareRepository.deleteById(id);
    }

    public List<ComandaAprovizionare> findComenziByStatus(String status) {
        return comandaAprovizionareRepository.findByStatus(status);
    }

    public List<ComandaAprovizionare> findComenziAfterDate(Date date) {
        return comandaAprovizionareRepository.findByDataComandaAfter(date);
    }

    public List<ComandaAprovizionare> findComenziByFurnizorId(Long furnizorId) {
        return comandaAprovizionareRepository.findByFurnizor_IdFurnizor(furnizorId);
    }

    public ComandaAprovizionare findById(Long id) {
        return comandaAprovizionareRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comanda cu ID-ul " + id + " nu a fost găsită!"));
    }
}
