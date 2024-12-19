package org.MagazinSport.Controller;

import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Services.VanzareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/api/vanzari")
public class VanzareController {

    @Autowired
    private VanzareService vanzareService;

    @GetMapping("/daily")
    public Map<String, Object> getDailySales() {
        return vanzareService.getDailySales();
    }

    @GetMapping("/monthly")
    public Map<String, Object> getMonthlySales() {
        return vanzareService.getMonthlySales();
    }

    @GetMapping("/yearly")
    public Map<String, Object> getYearlySales() {
        return vanzareService.getYearlySales();
    }

    @PostMapping("/adauga")
    public ResponseEntity<Void> adaugaVanzare(@RequestBody Vanzare vanzare) {
        vanzareService.saveVanzare(vanzare);
        return ResponseEntity.ok().build();
    }
}
