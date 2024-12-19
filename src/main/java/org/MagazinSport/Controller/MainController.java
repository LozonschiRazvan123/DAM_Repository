package org.MagazinSport.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/produse")
    public String produse(Model model) {
        model.addAttribute("pageTitle", "Produse");
        return "produse";
    }

    @GetMapping("/stocuri")
    public String stocuri(Model model) {
        model.addAttribute("pageTitle", "Stocuri");
        return "stocuri";
    }

    @GetMapping("/alerte-stoc")
    public String alerteStoc(Model model) {
        model.addAttribute("pageTitle", "Alerte Stoc");
        return "alerte-stoc";
    }

    @GetMapping("/comenzi")
    public String comenzi(Model model) {
        model.addAttribute("pageTitle", "Comenzi");
        return "comenzi";
    }

    @GetMapping("/vanzari")
    public String vanzari(Model model) {
        model.addAttribute("pageTitle", "Vânzări");
        return "vanzari";
    }

    @GetMapping("/furnizori")
    public String furnizori(Model model) {
        model.addAttribute("pageTitle", "Furnizori");
        return "furnizori";
    }
}
