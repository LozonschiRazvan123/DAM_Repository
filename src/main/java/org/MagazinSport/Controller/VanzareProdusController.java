package org.MagazinSport.Controller;

import org.MagazinSport.DTO.VanzareProdusDTO;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Model.VanzareProdus;
import org.MagazinSport.Services.ProdusService;
import org.MagazinSport.Services.VanzareProdusService;
import org.MagazinSport.Services.VanzareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vanzare-produse")
public class VanzareProdusController {

    private final VanzareProdusService vanzareProdusService;
    private final ProdusService produsService;
    private final VanzareService vanzareService;

    @Autowired
    public VanzareProdusController(VanzareProdusService vanzareProdusService, ProdusService produsService, VanzareService vanzareService) {
        this.vanzareProdusService = vanzareProdusService;
        this.produsService = produsService;
        this.vanzareService = vanzareService;
    }

    @PostMapping
    public ResponseEntity<VanzareProdusDTO> createVanzareProdus(@RequestBody VanzareProdusDTO vanzareProdusDTO) {
        Produs produs = produsService.getProdusById(vanzareProdusDTO.getProdusId())
                .orElseThrow(() -> new IllegalArgumentException("Produs not found"));

        Vanzare vanzare = vanzareService.getVanzareById(vanzareProdusDTO.getVanzareId());


        VanzareProdus vanzareProdus = new VanzareProdus(
                vanzare,
                produs,
                vanzareProdusDTO.getCantitate(),
                vanzareProdusDTO.getPretUnitate()
        );

        VanzareProdus savedVanzareProdus = vanzareProdusService.saveVanzareProdus(vanzareProdus);
        return ResponseEntity.ok(new VanzareProdusDTO(
                savedVanzareProdus.getId(),
                savedVanzareProdus.getVanzare() != null ? savedVanzareProdus.getVanzare().getId() : null,
                savedVanzareProdus.getProdus() != null ? savedVanzareProdus.getProdus().getIdProdus() : null,
                savedVanzareProdus.getCantitate(),
                savedVanzareProdus.getPretUnitate()
        ));
    }

    @GetMapping
    public ResponseEntity<List<VanzareProdusDTO>> getAllVanzareProduse() {
        List<VanzareProdusDTO> vanzareProduse = vanzareProdusService.getAllVanzareProduse()
                .stream()
                .map(vanzareProdus -> new VanzareProdusDTO(
                        vanzareProdus.getId(),
                        vanzareProdus.getVanzare() != null ? vanzareProdus.getVanzare().getId() : null,
                        vanzareProdus.getProdus() != null ? vanzareProdus.getProdus().getIdProdus() : null,
                        vanzareProdus.getCantitate(),
                        vanzareProdus.getPretUnitate()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(vanzareProduse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VanzareProdusDTO> getVanzareProdusById(@PathVariable Long id) {
        return vanzareProdusService.getVanzareProdusById(id)
                .map(vanzareProdus -> new ResponseEntity<>(new VanzareProdusDTO(
                        vanzareProdus.getId(),
                        vanzareProdus.getVanzare() != null ? vanzareProdus.getVanzare().getId() : null,
                        vanzareProdus.getProdus() != null ? vanzareProdus.getProdus().getIdProdus() : null,
                        vanzareProdus.getCantitate(),
                        vanzareProdus.getPretUnitate()
                ), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VanzareProdusDTO> updateVanzareProdus(@PathVariable Long id, @RequestBody VanzareProdusDTO vanzareProdusDTO) {
        Produs produs = produsService.getProdusById(vanzareProdusDTO.getProdusId())
                .orElseThrow(() -> new IllegalArgumentException("Produs not found"));

        Vanzare vanzare = vanzareService.getVanzareById(vanzareProdusDTO.getVanzareId());

        VanzareProdus vanzareProdus = new VanzareProdus(
                vanzare,
                produs,
                vanzareProdusDTO.getCantitate(),
                vanzareProdusDTO.getPretUnitate()
        );

        VanzareProdus updatedVanzareProdus = vanzareProdusService.updateVanzareProdus(id, vanzareProdus);
        if (updatedVanzareProdus != null) {
            return new ResponseEntity<>(new VanzareProdusDTO(
                    updatedVanzareProdus.getId(),
                    updatedVanzareProdus.getVanzare() != null ? updatedVanzareProdus.getVanzare().getId() : null,
                    updatedVanzareProdus.getProdus() != null ? updatedVanzareProdus.getProdus().getIdProdus() : null,
                    updatedVanzareProdus.getCantitate(),
                    updatedVanzareProdus.getPretUnitate()
            ), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVanzareProdus(@PathVariable Long id) {
        vanzareProdusService.deleteVanzareProdus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/vanzare/{vanzareId}")
    public ResponseEntity<List<VanzareProdusDTO>> getProduseByVanzareId(@PathVariable Long vanzareId) {
        List<VanzareProdusDTO> produse = vanzareProdusService.getProduseByVanzareId(vanzareId)
                .stream()
                .map(vanzareProdus -> new VanzareProdusDTO(
                        vanzareProdus.getId(),
                        vanzareProdus.getVanzare() != null ? vanzareProdus.getVanzare().getId() : null,
                        vanzareProdus.getProdus() != null ? vanzareProdus.getProdus().getIdProdus() : null,
                        vanzareProdus.getCantitate(),
                        vanzareProdus.getPretUnitate()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(produse);
    }

    @GetMapping("/produs/{produsId}")
    public ResponseEntity<List<VanzareProdusDTO>> getProduseByProdusId(@PathVariable Long produsId) {
        List<VanzareProdusDTO> produse = vanzareProdusService.getProduseByProdusId(produsId)
                .stream()
                .map(vanzareProdus -> new VanzareProdusDTO(
                        vanzareProdus.getId(),
                        vanzareProdus.getVanzare() != null ? vanzareProdus.getVanzare().getId() : null,
                        vanzareProdus.getProdus() != null ? vanzareProdus.getProdus().getIdProdus() : null,
                        vanzareProdus.getCantitate(),
                        vanzareProdus.getPretUnitate()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(produse);
    }
}
