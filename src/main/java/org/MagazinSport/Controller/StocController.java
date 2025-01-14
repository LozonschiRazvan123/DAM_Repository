package org.MagazinSport.Controller;

import jakarta.validation.Valid;
import org.MagazinSport.DTO.StocDTO;
import org.MagazinSport.Model.Stoc;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Repository.ProdusRepository;
import org.MagazinSport.Services.ProdusService;
import org.MagazinSport.Services.StocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stocuri")
public class StocController {

    @Autowired
    private StocService stocService;

    @Autowired
    private ProdusService produsService;

    @GetMapping("/")
    public String viewIndex() {
        return "index"; // Numele fișierului index.html din templates
    }
    @GetMapping("/st")
    public String viewIndex2() {
        return "stocuri"; // Numele fișierului index.html din templates
    }
    @GetMapping
    public String showStocuriPage(Model model) {
        List<Stoc> stocuri = stocService.getAllStocuri();
        List<Produs> produse = produsService.getAllProduse();
        System.out.println("Produse: " + produse);
        model.addAttribute("stocuri", stocuri);
        model.addAttribute("produse", produse);
        return "stocuri";
    }

    @GetMapping("/create")
    public String createStocForm(Model model) {
        model.addAttribute("stoc", new Stoc());
        model.addAttribute("produse",produsService.getAllProduse());
        return "create_stoc";
    }

    @PostMapping("/update/{id}")
    public String updateStoc(@PathVariable Long id, @ModelAttribute Stoc stoc) {
        Stoc updatedStoc = stocService.updateStoc(id, stoc);
        if (updatedStoc != null) {
            return "redirect:/stocuri";
        }
        return "redirect:/stocuri?error=update_failed";
    }

    @PostMapping("/save")
    public String saveStoc(@ModelAttribute Stoc stoc) {
        stocService.addStoc(stoc);
        return "redirect:/stocuri";
    }

    @GetMapping("/edit/{id}")
    public String editStocForm(@PathVariable Long id, Model model) {
        Optional<Stoc> stoc = stocService.getStocById(id);
        if (stoc.isPresent()) {
            model.addAttribute("stoc", stoc.get());
            model.addAttribute("produse",produsService.getAllProduse());
            return "edit_stoc";
        }
        return "redirect:/stocuri";
    }

    @GetMapping("/delete/{id}")
    public String deleteStoc(@PathVariable Long id) {
        Optional<Stoc> stocId = stocService.getStocById(id);
        if (stocId.isPresent()) {
            stocService.deleteStoc(id);
            return "redirect:/stocuri";
        }
        return "redirect:/stocuri?error=delete_failed";
    }
}