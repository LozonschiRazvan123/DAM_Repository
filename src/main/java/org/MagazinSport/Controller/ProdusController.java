package org.MagazinSport.Controller;

import org.MagazinSport.DTO.ComandaProdusDTO;
import org.MagazinSport.Model.ComandaProdus;
import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Repository.ComandaProdusRepository;
import org.MagazinSport.Services.ComandaProdusService;
import org.MagazinSport.Services.FurnizorService;
import org.MagazinSport.Services.ProdusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produse")
public class ProdusController {

    private final ProdusService produsService;
    private final FurnizorService furnizorService;

    public ProdusController(ProdusService produsService, FurnizorService furnizorService) {
        this.produsService = produsService;
        this.furnizorService = furnizorService;
    }

    @GetMapping
    public String showProdusePage(Model model) {
        List<Produs> produse = produsService.getAllActiveProduse();
        List<Furnizor> furnizori = furnizorService.getAllFurnizori();
        model.addAttribute("produse", produse);
        model.addAttribute("furnizori", furnizori);
        return "produse";
    }

    @GetMapping("/create")
    public String createProdusForm(Model model) {
        model.addAttribute("produs", new Produs());
        model.addAttribute("furnizori", furnizorService.getAllFurnizori());
        return "create_produs";
    }

    @GetMapping("/edit/{id}")
    public String editProdusForm(@PathVariable Long id, Model model) {
        Produs produs = produsService.getProdusById(id).orElse(null);
        if (produs != null) {
            model.addAttribute("produs", produs);
            model.addAttribute("furnizori", furnizorService.getAllFurnizori());
            return "edit_produs";
        }
        return "redirect:/produse";
    }

    @PostMapping("/save")
    public String saveProdus(@ModelAttribute Produs produs) {
        produsService.saveProdus(produs);
        return "redirect:/produse";
    }
    @GetMapping("/new")
    public String showCreateComandaPage(Model model) {
        List<Produs> produse = produsService.getAllProduse();
        model.addAttribute("produse", produse);
        model.addAttribute("comandaProdus", new ComandaProdusDTO());
        return "comenzi";
    }
    @GetMapping("/")
    public String viewIndex() {
        return "index";
    }
    @GetMapping("/delete/{id}")
    public String deleteProdus(@PathVariable Long id) {
        try {
            produsService.deactivateProdus(id);
        } catch (Exception e) {
            return "redirect:/produse?error=delete_failed";
        }
        return "redirect:/produse";
    }
}
