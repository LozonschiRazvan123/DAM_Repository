package org.MagazinSport.Controller;

import jakarta.validation.Valid;
import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Services.FurnizorService;
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

    public FurnizorController(FurnizorService furnizorService) {
        this.furnizorService = furnizorService;
    }

    @GetMapping
    public String showFurnizoriPage(Model model) {
        // Obține lista furnizorilor din baza de date
        List<Furnizor> furnizori = furnizorService.getAllFurnizori();
        System.out.println("Furnizori: " + furnizori); // Debugging - afișează furnizorii în consolă

        // Adaugă lista de furnizori în model pentru Thymeleaf
        model.addAttribute("furnizori", furnizori);

        // Returnează pagina Thymeleaf
        return "furnizori"; // Numele fișierului Thymeleaf
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
    @GetMapping("/edit/{id}")
    public String editFurnizor(@PathVariable Long id, Model model) {
        Optional<Furnizor> furnizor = furnizorService.getFurnizorById(id);
        if (furnizor.isPresent()) {
            model.addAttribute("furnizor", furnizor.get());
            return "edit_furnizor"; // Numele paginii Thymeleaf
        }

        return "redirect:/furnizori?error=not_found"; // Redirecționează dacă furnizorul nu este găsit
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
        return "redirect:/furnizori"; // Redirecționează către lista de furnizori
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
            return "furnizori"; // Reafișează formularul cu erori
        }
        furnizorService.saveFurnizor(furnizor);
        return "redirect:/furnizori";
    }


    @GetMapping("/delete/{id}")
    public String deleteFurnizor(@PathVariable Long id) {
        Optional<Furnizor> furnizor = furnizorService.getFurnizorById(id);
        if (furnizor.isPresent()) {
            furnizorService.deleteFurnizor(id);
            return "redirect:/furnizori"; // Redirecționează la pagina furnizorilor
        }
        return "redirect:/furnizori?error=delete_failed"; // Redirecționează cu un mesaj de eroare
    }

}
