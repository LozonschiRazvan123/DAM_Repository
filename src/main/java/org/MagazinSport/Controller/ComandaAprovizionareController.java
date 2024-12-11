package org.MagazinSport.Controller;
import org.MagazinSport.DTO.ComandaAprovizionareDTO;
import org.MagazinSport.Model.ComandaAprovizionare;
import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Services.ComandaAprovizionareService;
import org.MagazinSport.Services.FurnizorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comenzi-aprovizionare")
public class ComandaAprovizionareController {

    private final ComandaAprovizionareService comandaService;
    private final FurnizorService furnizorService;

    @Autowired
    public ComandaAprovizionareController(ComandaAprovizionareService comandaService, FurnizorService furnizorService) {
        this.comandaService = comandaService;
        this.furnizorService = furnizorService;
    }

    @PostMapping
    public ResponseEntity<ComandaAprovizionare> createComanda(@RequestBody ComandaAprovizionareDTO comandaDTO) {
        Furnizor furnizor = furnizorService.getFurnizorById(comandaDTO.getFurnizorId())
                .orElseThrow(() -> new IllegalArgumentException("Furnizor not found"));

        ComandaAprovizionare comanda = new ComandaAprovizionare(
                comandaDTO.getDataComanda(),
                comandaDTO.getDataLivrareEstimata(),
                comandaDTO.getStatus(),
                furnizor
        );
        return ResponseEntity.ok(comandaService.saveComandaAprovizionare(comanda));
    }

    @GetMapping
    public List<ComandaAprovizionare> getAllComenzi() {
        return comandaService.getAllComenziAprovizionare();
    }
}
