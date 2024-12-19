package org.MagazinSport.Services;

import org.MagazinSport.Model.AlerteStoc;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Stoc;
import org.MagazinSport.Repository.AlerteStocRepository;
import org.MagazinSport.Repository.ProdusRepository;
import org.MagazinSport.Repository.StocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AlerteStocService {

    @Autowired
    private AlerteStocRepository alerteStocRepository;

    @Autowired
    private StocRepository stocRepository;

    @Autowired
    private ProdusRepository produsRepository;

    @Transactional
    public void generateAlerteForLowStock() {
        List<Stoc> stocuri = stocRepository.findAll();

        for (Stoc stoc : stocuri) {
            Produs produs = stoc.getProdus();
            if (stoc.isBelowMinimumLevel()) {
                boolean alertExist = alerteStocRepository.existsByProdus(produs);
                if (!alertExist) {
                    AlerteStoc alerta = new AlerteStoc(produs, true, new Date());
                    alerteStocRepository.save(alerta);
                }
            }
        }
    }

    public List<AlerteStoc> getAllAlerteStoc() {
        return alerteStocRepository.findAll();
    }

    public List<AlerteStoc> getActiveAlerteStoc() {
        return alerteStocRepository.findByActivTrue();
    }

    public String generateAlerts() {
        AlerteStoc alertaNoua = new AlerteStoc();
        alertaNoua.setActiv(true);
        Optional<Produs> produs = produsRepository.findById(1L);
        if (produs != null) {
            alertaNoua.setProdus(produs.get());
        } else {
            return "Produsul nu a fost găsit!";
        }
        alertaNoua.setDataAlerta(new Date(2024/12/17));

        alerteStocRepository.save(alertaNoua);

        return "Alerta de stoc a fost generată cu succes!";
    }
    public AlerteStoc findById(Long id) {
        Optional<AlerteStoc> alerta = alerteStocRepository.findById(id);
        return alerta.orElse(null);  // Returnăm alerta sau null dacă nu o găsim
    }

    // Alte metode pentru gestionarea alertelor de stoc

    public List<AlerteStoc> getAlerteActive() {
        return alerteStocRepository.findByActiv(true);
    }

    public void saveAlert(AlerteStoc alerta) {
        alerteStocRepository.save(alerta);  // Salvăm alerta actualizată
    }
}