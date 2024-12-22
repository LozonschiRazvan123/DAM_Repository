package org.MagazinSport.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @GetMapping("/settings")
    public String showSettings(HttpSession session, Model model) {
        // Load current theme from session or default to "light"
        String theme = (String) session.getAttribute("theme");
        if (theme == null) {
            theme = "light";
        }
        // Add attributes for the settings page
        model.addAttribute("name", "John Doe");
        model.addAttribute("email", "john.doe@example.com");
        model.addAttribute("bio", "Welcome to your profile!");
        model.addAttribute("notifications", true);
        model.addAttribute("emailNotifications", true);
        model.addAttribute("smsNotifications", false);
        model.addAttribute("theme", theme);

        return "settings";
    }

    @PostMapping("/settings/updateTheme")
    public String updateTheme(@RequestParam("theme") String theme, HttpSession session, Model model) {
        // Store the selected theme in the session
        session.setAttribute("theme", theme);
        System.out.println("Theme updated to: " + theme);

        // Reload settings page with updated theme
        return "redirect:/settings";
    }
}
