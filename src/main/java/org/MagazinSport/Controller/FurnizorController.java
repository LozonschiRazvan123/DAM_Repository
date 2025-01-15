package org.MagazinSport.Controller;

import jakarta.validation.Valid;
import org.MagazinSport.DTO.ProdusDTO;
import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Services.FurnizorService;
import org.MagazinSport.Services.ProdusService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/furnizori")
public class FurnizorController {

    private final FurnizorService furnizorService;
    private final ProdusService produsService;

    public FurnizorController(FurnizorService furnizorService, ProdusService produsService) {
        this.furnizorService = furnizorService;
        this.produsService = produsService;
    }

    @GetMapping
    public String showFurnizoriPage(Model model) {
        List<Furnizor> furnizori = furnizorService.getAllFurnizori();
        System.out.println("Furnizori: " + furnizori);

        model.addAttribute("furnizori", furnizori);

        return "furnizori";
    }


    @GetMapping("/{id}")
    public ResponseEntity<Furnizor> getFurnizorById(@PathVariable Long id) {
        return furnizorService.getFurnizorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

/*    @PostMapping
    public ResponseEntity<Furnizor> createFurnizor(@Validated @RequestBody Furnizor furnizor) {
        Furnizor savedFurnizor = furnizorService.saveFurnizor(furnizor);
        return ResponseEntity.ok(savedFurnizor);
    }*/
    @GetMapping("/")
    public String viewIndex() {
    return "index";
}
    @GetMapping("/edit/{id}")
    public String editFurnizor(@PathVariable Long id, Model model) {
        Optional<Furnizor> furnizor = furnizorService.getFurnizorById(id);
        if (furnizor.isPresent()) {
            model.addAttribute("furnizor", furnizor.get());
            return "edit_furnizor";
        }

        return "redirect:/furnizori?error=not_found";
    }
    @PostMapping("/update/{id}")
    public String updateFurnizor(@PathVariable Long id, @ModelAttribute Furnizor furnizor) {
        Optional<Furnizor> existingFurnizor = furnizorService.getFurnizorById(id);
        if (existingFurnizor.isPresent()) {
            Furnizor updatedFurnizor = existingFurnizor.get();
            updatedFurnizor.setNume(furnizor.getNume());
            updatedFurnizor.setAdresa(furnizor.getAdresa());
            updatedFurnizor.setTelefon(furnizor.getTelefon());
            updatedFurnizor.setEmail(furnizor.getEmail());
            furnizorService.saveFurnizor(updatedFurnizor);
        }
        return "redirect:/furnizori";
    }

  /*  @PutMapping("/{id}")
    public ResponseEntity<Furnizor> updateFurnizor(@PathVariable Long id, @Validated @RequestBody Furnizor furnizor) {
        Furnizor updatedFurnizor = furnizorService.updateFurnizor(id, furnizor);
        if (updatedFurnizor != null) {
            return ResponseEntity.ok(updatedFurnizor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
*/

    @PostMapping("/save")
    public String saveFurnizor(@Valid @ModelAttribute Furnizor furnizor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("furnizori", furnizorService.getAllFurnizori());
            return "furnizori";
        }
        furnizorService.saveFurnizor(furnizor);
        return "redirect:/furnizori";
    }


    @GetMapping("/delete/{id}")
    public String deleteFurnizor(@PathVariable Long id) {
        Optional<Furnizor> furnizor = furnizorService.getFurnizorById(id);
        if (furnizor.isPresent()) {
            furnizorService.deleteFurnizor(id);
            return "redirect:/furnizori";
        }
        return "redirect:/furnizori?error=delete_failed";
    }

    @GetMapping("/comanda/{id}")
    public String viewProductsForOrder(@PathVariable Long id, Model model) {
        Optional<Furnizor> furnizorOptional = furnizorService.getFurnizorById(id);
        if (furnizorOptional.isPresent()) {
            Furnizor furnizor = furnizorOptional.get();
            model.addAttribute("furnizor", furnizor);
            model.addAttribute("produse", furnizor.getProduse());
            return "comanda_produse";
        }
        return "redirect:/furnizori?error=not_found";
    }

    /*@PostMapping
    public String placeOrder(@ModelAttribute("produse") List<ProdusDTO> produseComandate, Model model) {
        for (ProdusDTO produsDTO : produseComandate) {
            if (produsDTO.getStoc() > 0) {
                Optional<Produs> produsOptional = produsService.getProdusById(produsDTO.getIdProdus());
                if (produsOptional.isPresent()) {
                    Produs produs = produsOptional.get();
                    if (produs.getStoc() >= produsDTO.getStoc()) {
                        produs.setStoc(produs.getStoc() - produsDTO.getStoc());
                        produsService.saveProdus(produs);
                    }
                }
            }
        }
        return "redirect:/comenzi";
    }*/
}
