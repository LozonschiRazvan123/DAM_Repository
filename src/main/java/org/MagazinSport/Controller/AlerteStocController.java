package org.MagazinSport.Controller;
import org.MagazinSport.DTO.AlerteStocDTO;
import org.MagazinSport.Model.AlerteStoc;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Services.AlerteStocService;
import org.MagazinSport.Services.ProdusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alerte-stoc")
public class AlerteStocController {

    private final AlerteStocService alerteStocService;
    private final ProdusService produsService;

    @Autowired
    public AlerteStocController(AlerteStocService alerteStocService, ProdusService produsService) {
        this.alerteStocService = alerteStocService;
        this.produsService = produsService;
    }

    @PostMapping
    public ResponseEntity<AlerteStoc> createAlerteStoc(@RequestBody AlerteStocDTO alerteStocDTO) {
        Produs produs = produsService.getProdusById(alerteStocDTO.getProdusId())
                .orElseThrow(() -> new IllegalArgumentException("Produs not found"));

        AlerteStoc alerta = new AlerteStoc(produs, alerteStocDTO.getActiv(), alerteStocDTO.getDataAlerta());
        return ResponseEntity.ok(alerteStocService.saveAlerteStoc(alerta));
    }

    @GetMapping
    public List<AlerteStocDTO> getAllAlerteStoc() {
        return alerteStocService.getAllAlerteStoc().stream().map(alerta -> {
            AlerteStocDTO dto = new AlerteStocDTO();
            dto.setId(alerta.getIdAlerteStoc());
            dto.setProdusId(alerta.getProdus().getIdProdus());
            dto.setActiv(alerta.getActiv());
            dto.setDataAlerta(alerta.getDataAlerta());
            return dto;
        }).collect(Collectors.toList());
    }
}