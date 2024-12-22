package org.MagazinSport.Controller;

import org.MagazinSport.DTO.VanzareDTO;
import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Repository.VanzareRepository;
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
    @Autowired
    private VanzareRepository vanzareRepository;


    @PostMapping("/adauga")
    public ResponseEntity<String> adaugaVanzare(@RequestBody Vanzare vanzare) {
        try {
            System.out.println("Date primite din frontend: " + vanzare);
            vanzareRepository.save(vanzare);
            return ResponseEntity.ok("Vânzare adăugată cu succes!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare la salvarea vânzării: " + e.getMessage());
        }
    }



}
