package org.MagazinSport.Controller;

import org.MagazinSport.DTO.ComandaProdusDTO;
import org.MagazinSport.Model.ComandaAprovizionare;
import org.MagazinSport.Model.ComandaProdus;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Services.ComandaAprovizionareService;
import org.MagazinSport.Services.ComandaProdusService;
import org.MagazinSport.Services.ProdusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comanda-produs")
public class ComandaProdusController {

    private final ComandaProdusService comandaProdusService;
    private final ProdusService produsService;
    private final ComandaAprovizionareService comandaAprovizionareService;

    @Autowired
    public ComandaProdusController(ComandaProdusService comandaProdusService, ProdusService produsService, ComandaAprovizionareService comandaAprovizionareService) {
        this.comandaProdusService = comandaProdusService;
        this.produsService = produsService;
        this.comandaAprovizionareService = comandaAprovizionareService;
    }

    @PostMapping
    public ResponseEntity<ComandaProdus> createComandaProdus(@RequestBody ComandaProdusDTO comandaProdusDTO) {
        Produs produs = produsService.getProdusById(comandaProdusDTO.getProdusId())
                .orElseThrow(() -> new IllegalArgumentException("Produs not found"));

        ComandaAprovizionare comandaAprovizionare = comandaAprovizionareService.getComandaAprovizionareById(comandaProdusDTO.getComandaAprovizionareId())
                .orElseThrow(() -> new IllegalArgumentException("ComandaAprovizionare not found"));

        ComandaProdus comanda = new ComandaProdus(
                comandaAprovizionare,
                produs,
                comandaProdusDTO.getCantitate(),
                comandaProdusDTO.getPretUnitate(),
                comandaProdusDTO.getDataComanda()
        );
        return ResponseEntity.ok(comandaProdusService.saveComandaProdus(comanda));
    }

    @PutMapping
    public ResponseEntity<ComandaProdus> updateComandaProdus(@RequestBody ComandaProdusDTO comandaProdusDTO) {
        // Obține produsul din baza de date
        Produs produs = produsService.getProdusById(comandaProdusDTO.getProdusId())
                .orElseThrow(() -> new IllegalArgumentException("Produs not found"));

        // Obține comanda de aprovizionare
        ComandaAprovizionare comandaAprovizionare = comandaAprovizionareService.getComandaAprovizionareById(comandaProdusDTO.getComandaAprovizionareId())
                .orElseThrow(() -> new IllegalArgumentException("ComandaAprovizionare not found"));

        // Caută dacă există deja un ComandaProdus
        ComandaProdus comanda = comandaProdusService.getComandaProduseByComandaId(comandaAprovizionare.getIdComandaAprovizionare()).stream()
                .filter(cp -> cp.getProdus().getIdProdus().equals(produs.getIdProdus()))
                .findFirst()
                .orElse(new ComandaProdus(comandaAprovizionare, produs, 0, 0.0, comandaProdusDTO.getDataComanda()));

        // Actualizează datele
        comanda.setCantitate(comandaProdusDTO.getCantitate());
        comanda.setPretUnitate(comandaProdusDTO.getPretUnitate());
        comanda.setDataComanda(comandaProdusDTO.getDataComanda());

        return ResponseEntity.ok(comandaProdusService.saveComandaProdus(comanda));
    }

    @GetMapping
    public ResponseEntity<List<ComandaProdusDTO>> getAllComandaProduse() {
        List<ComandaProdusDTO> produse = comandaProdusService.getAllComandaProduse().stream().map(comanda -> {
            ComandaProdusDTO dto = new ComandaProdusDTO();
            dto.setId(comanda.getId());
            dto.setProdusId(comanda.getProdus().getIdProdus());
            dto.setCantitate(comanda.getCantitate());
            dto.setPretUnitate(comanda.getPretUnitate());
            dto.setDataComanda(comanda.getDataComanda());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(produse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComandaProdusDTO> getComandaProdusById(@PathVariable Long id) {
        ComandaProdus comanda = comandaProdusService.getComandaProdusById(id)
                .orElseThrow(() -> new IllegalArgumentException("ComandaProdus not found"));
        ComandaProdusDTO dto = new ComandaProdusDTO();
        dto.setId(comanda.getId());
        dto.setProdusId(comanda.getProdus().getIdProdus());
        dto.setCantitate(comanda.getCantitate());
        dto.setPretUnitate(comanda.getPretUnitate());
        dto.setDataComanda(comanda.getDataComanda());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/comanda/{comandaId}")
    public ResponseEntity<List<ComandaProdusDTO>> getComandaProduseByComandaId(@PathVariable Long comandaId) {
        List<ComandaProdusDTO> produse = comandaProdusService.getComandaProduseByComandaId(comandaId).stream().map(comanda -> {
            ComandaProdusDTO dto = new ComandaProdusDTO();
            dto.setId(comanda.getId());
            dto.setProdusId(comanda.getProdus().getIdProdus());
            dto.setCantitate(comanda.getCantitate());
            dto.setPretUnitate(comanda.getPretUnitate());
            dto.setDataComanda(comanda.getDataComanda());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(produse);
    }

    @GetMapping("/produs/{produsId}")
    public ResponseEntity<List<ComandaProdusDTO>> getComandaProduseByProdusId(@PathVariable Long produsId) {
        List<ComandaProdusDTO> produse = comandaProdusService.getComandaProduseByProdusId(produsId).stream().map(comanda -> {
            ComandaProdusDTO dto = new ComandaProdusDTO();
            dto.setId(comanda.getId());
            dto.setProdusId(comanda.getProdus().getIdProdus());
            dto.setCantitate(comanda.getCantitate());
            dto.setPretUnitate(comanda.getPretUnitate());
            dto.setDataComanda(comanda.getDataComanda());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(produse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComandaProdus(@RequestBody ComandaProdusDTO comandaProdusDTO) {
        // Obține produsul din baza de date
        Produs produs = produsService.getProdusById(comandaProdusDTO.getProdusId())
                .orElseThrow(() -> new IllegalArgumentException("Produs not found"));

        // Obține comanda de aprovizionare
        ComandaAprovizionare comandaAprovizionare = comandaAprovizionareService.getComandaAprovizionareById(comandaProdusDTO.getComandaAprovizionareId())
                .orElseThrow(() -> new IllegalArgumentException("ComandaAprovizionare not found"));

        // Găsește ComandaProdus
        ComandaProdus comanda = comandaProdusService.getComandaProduseByComandaId(comandaAprovizionare.getIdComandaAprovizionare()).stream()
                .filter(cp -> cp.getProdus().getIdProdus().equals(produs.getIdProdus()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ComandaProdus not found"));

        // Șterge ComandaProdus
        comandaProdusService.deleteComandaProdus(comanda.getId());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/new")
    public String showCreateComandaPage(Model model) {
        List<Produs> produse = produsService.getAllProduse();
        System.out.println("Produse disponibile: " + produse); // Log pentru depanare

        if (produse.isEmpty()) {
            System.out.println("Nu există produse disponibile!");
        }

        model.addAttribute("produse", produse);
        return "comenzi"; // Numele fișierului Thymeleaf
    }




    @PostMapping("/save")
    public String saveComanda(ComandaProdusDTO comandaProdusDTO) {
        Produs produs = produsService.getProdusById(comandaProdusDTO.getProdusId())
                .orElseThrow(() -> new IllegalArgumentException("Produs not found"));

        ComandaProdus comandaProdus = new ComandaProdus(
                null, // Fără comandaAprovizionare pentru simplitate
                produs,
                comandaProdusDTO.getCantitate(),
                comandaProdusDTO.getPretUnitate(),
                comandaProdusDTO.getDataComanda()
        );

        comandaProdusService.saveComandaProdus(comandaProdus);
        return "redirect:/api/comanda-produs/new";
    }




//    {
//        "comandaAprovizionareId": 1,
//            "produsId": 2,
//            "cantitate": 15,
//            "pretUnitate": 50.5,
//            "dataComanda": "2024-12-12"
//    }

}
