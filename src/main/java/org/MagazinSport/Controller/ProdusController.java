package org.MagazinSport.Controller;

import org.MagazinSport.Model.Produs;
import org.MagazinSport.Services.ProdusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produse")
public class ProdusController {

    private final ProdusService produsService;

    public ProdusController(ProdusService produsService) {
        this.produsService = produsService;
    }

    @GetMapping
    public ResponseEntity<List<Produs>> getAllProduse() {
        List<Produs> produse = produsService.getAllProduse();
        return ResponseEntity.ok(produse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produs> getProdusById(@PathVariable Long id) {
        return produsService.getProdusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produs> createProdus(@RequestBody Produs produs) {
        Produs savedProdus = produsService.saveProdus(produs);
        return ResponseEntity.ok(savedProdus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produs> updateProdus(@PathVariable Long id, @RequestBody Produs produs) {
        try {
            Produs updatedProdus = produsService.updateProdus(id, produs);
            return ResponseEntity.ok(updatedProdus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdus(@PathVariable Long id) {
        System.out.println("Request pentru ștergerea produsului cu ID-ul: " + id);
        try {
            produsService.deleteProdus(id);
            System.out.println("Produsul cu ID-ul " + id + " a fost șters.");
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            System.err.println("Eroare la ștergerea produsului: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    // Căutare produse după furnizor
    @GetMapping("/furnizor/{furnizorId}")
    public ResponseEntity<List<Produs>> getProduseByFurnizor(@PathVariable Long furnizorId) {
        List<Produs> produse = produsService.findProduseByFurnizorId(furnizorId);
        return ResponseEntity.ok(produse);
    }

    // Căutare produse după categorie
    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<Produs>> getProduseByCategorie(@PathVariable String categorie) {
        List<Produs> produse = produsService.findProduseByCategorie(categorie);
        return ResponseEntity.ok(produse);
    }

    // Căutare produse cu stoc mai mic decât un anumit prag
    @GetMapping("/stoc/{threshold}")
    public ResponseEntity<List<Produs>> getProduseByStocLessThan(@PathVariable int threshold) {
        List<Produs> produse = produsService.findProduseByStocLessThan(threshold);
        return ResponseEntity.ok(produse);
    }
}
