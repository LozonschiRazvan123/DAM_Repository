package org.MagazinSport.Controller;

import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Services.FurnizorService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/furnizori")
public class FurnizorController {

    private final FurnizorService furnizorService;

    public FurnizorController(FurnizorService furnizorService) {
        this.furnizorService = furnizorService;
    }

    @GetMapping
    public ResponseEntity<List<Furnizor>> getAllFurnizori() {
        List<Furnizor> furnizori = furnizorService.getAllFurnizori();
        return ResponseEntity.ok(furnizori);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Furnizor> getFurnizorById(@PathVariable Long id) {
        return furnizorService.getFurnizorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Furnizor> createFurnizor(@Validated @RequestBody Furnizor furnizor) {
        Furnizor savedFurnizor = furnizorService.saveFurnizor(furnizor);
        return ResponseEntity.ok(savedFurnizor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Furnizor> updateFurnizor(@PathVariable Long id, @Validated @RequestBody Furnizor furnizor) {
        Furnizor updatedFurnizor = furnizorService.updateFurnizor(id, furnizor);
        if (updatedFurnizor != null) {
            return ResponseEntity.ok(updatedFurnizor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFurnizor(@PathVariable Long id) {
        furnizorService.deleteFurnizor(id);
        return ResponseEntity.noContent().build();
    }
}
