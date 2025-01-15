package org.MagazinSport.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.MagazinSport.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @Autowired
    private UserService userService;

    @PostMapping("/settings/updateProfile")
    public String updateProfile(HttpServletRequest request,
                                @RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("bio") String bio) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            userService.updateUserProfile(username, name, email, bio);

            request.getSession().setAttribute("successMessage", "Profilul a fost actualizat cu succes.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "A aparut o eroare la actualizarea profilului: " + e.getMessage());
        }

        return "redirect:/settings";
    }



    @PostMapping("/settings/updateTheme")
    public String updateTheme(@RequestParam("theme") String theme, HttpServletRequest request) {
        request.getSession().setAttribute("theme", theme);

        return "redirect:/settings";
    }
   /* @GetMapping("/")
    public String viewIndex() {
        return "index"; // Numele fișierului index.html din templates
    }*/

}
