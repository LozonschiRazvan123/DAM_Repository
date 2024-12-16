package org.MagazinSport.Controller;

import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Services.VanzareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vanzari")
public class VanzareController {

    private final VanzareService vanzareService;

    @Autowired
    public VanzareController(VanzareService vanzareService) {
        this.vanzareService = vanzareService;
    }

    @PostMapping
    public ResponseEntity<Vanzare> createVanzare(@RequestBody Vanzare vanzare) {
        Vanzare savedVanzare = vanzareService.saveVanzare(vanzare);
        return new ResponseEntity<>(savedVanzare, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vanzare>> getAllVanzari() {
        List<Vanzare> vanzari = vanzareService.getAllVanzari();
        return new ResponseEntity<>(vanzari, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vanzare> getVanzareById(@PathVariable Long id) {
        Optional<Vanzare> vanzare = vanzareService.getVanzareById(id);
        return vanzare.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vanzare> updateVanzare(@PathVariable Long id, @RequestBody Vanzare vanzare) {
        Vanzare updatedVanzare = vanzareService.updateVanzare(id, vanzare);
        if (updatedVanzare != null) {
            return new ResponseEntity<>(updatedVanzare, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVanzare(@PathVariable Long id) {
        vanzareService.deleteVanzare(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/after/{date}")
    public ResponseEntity<List<Vanzare>> getVanzariAfterDate(@PathVariable Date date) {
        List<Vanzare> vanzari = vanzareService.findVanzariAfterDate(date);
        return new ResponseEntity<>(vanzari, HttpStatus.OK);
    }
}
