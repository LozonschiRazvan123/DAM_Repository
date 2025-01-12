package org.MagazinSport.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.MagazinSport.DTO.LoginDTO;
import org.MagazinSport.DTO.RegisterDTO;
import org.MagazinSport.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request) {
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        model.addAttribute("errorMessage", errorMessage);
        request.getSession().removeAttribute("errorMessage");
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }
    @PostMapping("/settings/changePassword")
    public String changePassword(HttpServletRequest request,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        try {
            // Verifică dacă parolele noi coincid
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("errorMessage", "New passwords do not match.");
                return "settings";
            }

            // Obține utilizatorul curent
            String username = request.getUserPrincipal().getName();
            userService.changePassword(username, currentPassword, newPassword);

            model.addAttribute("successMessage", "Password updated successfully.");
            return "settings";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "settings";
        }
    }
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDTO registerDTO, Model model) {
        try {
            if (!registerDTO.password.equals(registerDTO.confirmedPassword)) {
                model.addAttribute("errorMessage", "Passwords do not match.");
                return "register";
            }

            userService.registerUser(registerDTO);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/settings")
    public String settingsPage(HttpServletRequest request, Model model) {
        // Obține username-ul utilizatorului curent din contextul de securitate
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Găsește utilizatorul din baza de date
        org.MagazinSport.Model.User user = userService.getUserByUsername(username);

        // Verifică dacă bio-ul este gol și setează un mesaj predefinit
        String bio = user.getBio();
        if (bio == null || bio.trim().isEmpty()) {
            bio = "Acest cont e pentru administrarea Sporthub.";
        }

        // Adaugă datele utilizatorului în model
        model.addAttribute("name", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("bio", bio);

        // Adaugă mesajele de succes sau eroare din sesiune
        Object successMessage = request.getSession().getAttribute("successMessage");
        Object errorMessage = request.getSession().getAttribute("errorMessage");
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
            request.getSession().removeAttribute("successMessage");
        }
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }

        return "settings"; // Returnează șablonul Thymeleaf
    }



    @PostMapping("/login")
    public String authenticate(@ModelAttribute LoginDTO loginDTO, Model model) {
        try {
            userService.authenticateUser(loginDTO);
            return "redirect:/";
        } catch (IllegalArgumentException | UsernameNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }

   /* @PostMapping("/settings/updateTheme")
    public String updateTheme(@RequestParam("theme") String theme, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        userService.updateUserTheme(username, theme);

        // Salvează tema în sesiune
        request.getSession().setAttribute("theme", theme);

        return "redirect:/settings";
    }*/

}
