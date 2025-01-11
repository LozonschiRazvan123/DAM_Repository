package org.MagazinSport.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.GrantedAuthority;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        addUserDetailsToModel(model, authentication);
        return "index";
    }
    private void addUserDetailsToModel(Model model, Authentication authentication) {
        String userName = authentication.getName();

        String userRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("N/A");

        model.addAttribute("userName", userName);
        model.addAttribute("userRole", userRole);
    }

    @GetMapping("/produs")
    public String produse(Model model) {
        model.addAttribute("pageTitle", "Produse");
        return "produse";
    }

    @GetMapping("/stoc")
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

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
