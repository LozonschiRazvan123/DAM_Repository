package org.MagazinSport.Controller;
import org.MagazinSport.DTO.ComandaAprovizionareDTO;
import org.MagazinSport.Model.ComandaAprovizionare;
import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Services.ComandaAprovizionareService;
import org.MagazinSport.Services.FurnizorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/status/{status}")
    public List<ComandaAprovizionare> getComenziByStatus(@PathVariable String status) {
        return comandaService.findComenziByStatus(status);
    }

    @GetMapping("/after-date")
    public List<ComandaAprovizionare> getComenziAfterDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        System.out.println(date);
        return comandaService.findComenziAfterDate(date);
    }

    @GetMapping("/furnizor/{furnizorId}")
    public List<ComandaAprovizionare> getComenziByFurnizorId(@PathVariable Long furnizorId) {
        return comandaService.findComenziByFurnizorId(furnizorId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComandaAprovizionare> updateComanda(
            @PathVariable Long id,
            @RequestBody ComandaAprovizionareDTO comandaDTO) {
        Furnizor furnizor = furnizorService.getFurnizorById(comandaDTO.getFurnizorId())
                .orElseThrow(() -> new IllegalArgumentException("Furnizor not found"));

        ComandaAprovizionare updatedComanda = new ComandaAprovizionare(
                comandaDTO.getDataComanda(),
                comandaDTO.getDataLivrareEstimata(),
                comandaDTO.getStatus(),
                furnizor
        );
        ComandaAprovizionare result = comandaService.updateComandaAprovizionare(id, updatedComanda);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComanda(@PathVariable Long id) {
        if (comandaService.getComandaAprovizionareById(id).isPresent()) {
            comandaService.deleteComandaAprovizionare(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
