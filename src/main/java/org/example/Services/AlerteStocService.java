package org.example.Services;

import org.example.Model.AlerteStoc;
import org.example.Model.Produs;
import org.example.Repository.AlerteStocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AlerteStocService {

    private final AlerteStocRepository alerteStocRepository;

    @Autowired
    public AlerteStocService(AlerteStocRepository alerteStocRepository) {
        this.alerteStocRepository = alerteStocRepository;
    }

    // Create
    public AlerteStoc saveAlerteStoc(AlerteStoc alerta) {
        return alerteStocRepository.save(alerta);
    }

    // Read
    public List<AlerteStoc> getAllAlerteStoc() {
        return alerteStocRepository.findAll();
    }

    public Optional<AlerteStoc> getAlerteStocById(Long id) {
        return alerteStocRepository.findById(id);
    }

    // Update
    public AlerteStoc updateAlerteStoc(Long id, AlerteStoc alerta) {
        if (alerteStocRepository.existsById(id)) {
            alerta.setIdAlerteStoc(id);
            return alerteStocRepository.save(alerta);
        }
        return null;
    }

    // Delete
    public void deleteAlerteStoc(Long id) {
        alerteStocRepository.deleteById(id);
    }

    // Metode suplimentare
    public List<AlerteStoc> findActiveAlerte() {
        return alerteStocRepository.findByActiv(true);
    }

    public List<AlerteStoc> findAlerteByProdusId(Long produsId) {
        return alerteStocRepository.findByProdusId(produsId);
    }
    public AlerteStoc createAlertForProduct(Produs produs) {
        AlerteStoc alerta = new AlerteStoc();
        alerta.setProdus(produs);
        alerta.setDataAlerta(new Date());
        alerta.setActiv(true);
        return alerteStocRepository.save(alerta);
    }
}
