package org.MagazinSport.Controller;

import jakarta.validation.Valid;
import org.MagazinSport.DTO.StocDTO;
import org.MagazinSport.Model.Stoc;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Services.StocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocuri")
public class StocController {

    private final StocService stocService;

    @Autowired
    public StocController(StocService stocService) {
        this.stocService = stocService;
    }

    @PostMapping
    public ResponseEntity<StocDTO> createStoc(@Valid @RequestBody StocDTO stocDTO) {
        // Verificăm dacă ID-ul stocului nu este setat
        if (stocDTO.getIdStoc() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // ID-ul trebuie să fie null pentru creație
        }

        // Creăm obiectul Produs folosind ID-ul primit în DTO
        Produs produs = new Produs();
        produs.setIdProdus(stocDTO.getProdusId());

        // Căutăm sau creăm un stoc pentru produsul respectiv
        Stoc stoc = stocService.findOrCreateStoc(produs);
        stoc.setCantitate(stocDTO.getCantitate());
        stoc.setNivelMinim(stocDTO.getNivelMinim());
        stoc.setDataUltimeiModificari(stocDTO.getDataUltimeiModificari());

        // Salvăm stocul și returnăm obiectul salvat
        Stoc savedStoc = stocService.saveStoc(stoc);

        // Returnăm un răspuns cu stocul salvat și statusul 201 (Created)
        return new ResponseEntity<>(convertToDTO(savedStoc), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StocDTO>> getAllStocuri() {
        List<StocDTO> stocuri = stocService.getAllStocuri()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(stocuri, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StocDTO> getStocById(@PathVariable Long id) {
        return stocService.getStocById(id)
                .map(stoc -> new ResponseEntity<>(convertToDTO(stoc), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StocDTO> updateStoc(@PathVariable Long id, @Valid @RequestBody StocDTO stocDTO) {
        if (stocDTO.getIdStoc() != null && !stocDTO.getIdStoc().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // ID-ul din DTO trebuie să coincidă cu cel din URL
        }
        if (!stocService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Produs produs = new Produs();
        produs.setIdProdus(stocDTO.getProdusId());

        Stoc stoc = stocService.findOrCreateStoc(produs);
        stoc.setIdStoc(id);
        stoc.setCantitate(stocDTO.getCantitate());
        stoc.setNivelMinim(stocDTO.getNivelMinim());
        stoc.setDataUltimeiModificari(stocDTO.getDataUltimeiModificari());

        Stoc updatedStoc = stocService.updateStoc(id, stoc);
        return new ResponseEntity<>(convertToDTO(updatedStoc), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoc(@PathVariable Long id) {
        if (!stocService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        stocService.deleteStoc(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/below/{cantitate}")
    public ResponseEntity<List<StocDTO>> getStocuriBelowQuantity(@PathVariable int cantitate) {
        if (cantitate < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<StocDTO> stocuri = stocService.findStocBelowQuantity(cantitate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(stocuri, HttpStatus.OK);
    }

    // Helper methods for conversion
    private StocDTO convertToDTO(Stoc stoc) {
        return new StocDTO(
                stoc.getIdStoc(),
                stoc.getProdus().getIdProdus(),
                stoc.getProdus().getNume(),
                stoc.getCantitate(),
                stoc.getNivelMinim(),
                stoc.getDataUltimeiModificari()
        );
    }

    private Stoc convertToEntity(StocDTO stocDTO) {
        Stoc stoc = new Stoc();
        if (stocDTO.getIdStoc() != null) {
            stoc.setIdStoc(stocDTO.getIdStoc()); // ID-ul poate fi setat doar pentru update
        }
        stoc.setCantitate(stocDTO.getCantitate());
        stoc.setNivelMinim(stocDTO.getNivelMinim());
        stoc.setDataUltimeiModificari(stocDTO.getDataUltimeiModificari());

        Produs produs = new Produs();
        produs.setIdProdus(stocDTO.getProdusId());
        stoc.setProdus(produs);

        return stoc;
    }
}
