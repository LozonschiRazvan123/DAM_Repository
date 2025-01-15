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
        return "index";
    }
    @GetMapping("/active")
    public String getAlerteActive(Model model) {
        List<AlerteStoc> alerte = alerteStocService.getAlerteActive();
        model.addAttribute("alerte", alerte);
        model.addAttribute("pageTitle", "Alerte Stoc Active");
        return "active";
    }

    @GetMapping("/rezolva/{id}")
    public String resolveAlertForm(@PathVariable("id") Long id, Model model) {
        AlerteStoc alerta = alerteStocService.findById(id);
        if (alerta == null || !alerta.isActiv()) {
            return "redirect:/alerte-stoc/active";  // Dacă alerta nu există sau nu este activă, redirecționează
        }

        Optional<Stoc> optionalStoc = stocService.findByProdus(alerta.getProdus());
        if (optionalStoc.isEmpty()) {
            model.addAttribute("message", "Stocul pentru produsul cu ID-ul " + alerta.getProdus().getIdProdus() + " nu a fost găsit.");
            return "error-page";
        }

        Stoc stoc = optionalStoc.get();
        model.addAttribute("alerta", alerta);
        model.addAttribute("stoc", stoc);
        return "formular-rezolvare-alerta";
    }

    @PostMapping("/rezolva/{id}")
    public String resolveAlert(@PathVariable("id") Long id, @RequestParam int cantitateAdaugata, Model model) {
        AlerteStoc alerta = alerteStocService.findById(id);
        if (alerta == null || !alerta.isActiv()) {
            model.addAttribute("message", "Alerta nu exista sau nu este activa.");
            return "redirect:/alerte-stoc/active";
        }

        Optional<Stoc> optionalStoc = stocService.findByProdus(alerta.getProdus());
        if (optionalStoc.isPresent()) {
            Stoc stoc = optionalStoc.get();
            stoc.setCantitate(stoc.getCantitate() + cantitateAdaugata);
            stocService.saveStoc(stoc);

            if (stoc.getCantitate() >= stoc.getNivelMinim()) {
                alerta.setActiv(false);
                alerteStocService.saveAlert(alerta);
            }

            model.addAttribute("message", "Alerta a fost rezolvată cu succes!");
        } else {
            model.addAttribute("message", "Stocul pentru produsul cu ID-ul " + alerta.getProdus().getIdProdus() + " nu a fost găsit.");
        }

        return "redirect:/alerte-stoc/active";
    }
}