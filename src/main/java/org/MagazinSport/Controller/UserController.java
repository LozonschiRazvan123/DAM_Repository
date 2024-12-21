package org.MagazinSport.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.MagazinSport.DTO.LoginDTO;
import org.MagazinSport.DTO.RegisterDTO;
import org.MagazinSport.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
}
