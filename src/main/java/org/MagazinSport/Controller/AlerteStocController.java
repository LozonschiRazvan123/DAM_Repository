package org.MagazinSport.Controller;
import org.MagazinSport.DTO.AlerteStocDTO;
import org.MagazinSport.Model.AlerteStoc;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Model.Stoc;
import org.MagazinSport.Services.AlerteStocService;
import org.MagazinSport.Services.ProdusService;
import org.MagazinSport.Services.StocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/alerte-stoc")
public class AlerteStocController {

    @Autowired
    private AlerteStocService alerteStocService;
    @Autowired
    private StocService stocService;
    @GetMapping("/")
    public String viewIndex() {
        return "index"; // Numele fișierului index.html din templates
    }
    @GetMapping("/active")
    public String getAlerteActive(Model model) {
        List<AlerteStoc> alerte = alerteStocService.getAlerteActive();
        model.addAttribute("alerte", alerte);
        model.addAttribute("pageTitle", "Alerte Stoc Active");
        return "active";
    }

    // Formularul pentru rezolvarea unei alerte
    @GetMapping("/rezolva/{id}")
    public String resolveAlertForm(@PathVariable("id") Long id, Model model) {
        AlerteStoc alerta = alerteStocService.findById(id);
        if (alerta == null || !alerta.isActiv()) {
            return "redirect:/alerte-stoc/active";  // Dacă alerta nu există sau nu este activă, redirecționează
        }

        // Obținem stocul asociat produsului din alerta
        Optional<Stoc> optionalStoc = stocService.findByProdus(alerta.getProdus());
        if (optionalStoc.isEmpty()) {
            model.addAttribute("message", "Stocul pentru produsul cu ID-ul " + alerta.getProdus().getIdProdus() + " nu a fost găsit.");
            return "error-page";  // Poți crea o pagină de eroare pentru cazul în care stocul nu este găsit
        }

        Stoc stoc = optionalStoc.get();  // Obținem stocul din Optional
        model.addAttribute("alerta", alerta);
        model.addAttribute("stoc", stoc);  // Detalii stoc produs
        return "formular-rezolvare-alerta";  // Formularul de actualizare al stocului
    }

    // Rezolvarea alertei și actualizarea stocului
    @PostMapping("/rezolva/{id}")
    public String resolveAlert(@PathVariable("id") Long id, @RequestParam int cantitateAdaugata, Model model) {
        AlerteStoc alerta = alerteStocService.findById(id);
        if (alerta == null || !alerta.isActiv()) {
            model.addAttribute("message", "Alerta nu există sau nu este activă.");
            return "redirect:/alerte-stoc/active";
        }

        // Obținem stocul asociat produsului
        Optional<Stoc> optionalStoc = stocService.findByProdus(alerta.getProdus());
        if (optionalStoc.isPresent()) {
            Stoc stoc = optionalStoc.get();
            stoc.setCantitate(stoc.getCantitate() + cantitateAdaugata);
            stocService.saveStoc(stoc);  // Salvăm stocul actualizat

            // Verificăm dacă stocul a ajuns la nivelul minim și dezactivăm alerta
            if (stoc.getCantitate() >= stoc.getNivelMinim()) {
                alerta.setActiv(false);  // Dezactivăm alerta
                alerteStocService.saveAlert(alerta);  // Salvăm alerta actualizată
            }

            model.addAttribute("message", "Alerta a fost rezolvată cu succes!");
        } else {
            model.addAttribute("message", "Stocul pentru produsul cu ID-ul " + alerta.getProdus().getIdProdus() + " nu a fost găsit.");
        }

        return "redirect:/alerte-stoc/active";  // Redirecționăm la pagina cu alertele active
    }
}