package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    final private UserService service;

    public LoginController(UserService service) {
        this.service = service;
    }


    @GetMapping("/login")
    public String login() {
        return "login/login.html";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login/login.html";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "login/forgot_password_form.html";
    }

    @PostMapping("/forgot-password")
    public ModelAndView submitForgotPassword(String email, RedirectAttributes redirectAttributes) {
        ModelAndView view = new ModelAndView("redirect:/login");

        try {
            if (!StringUtils.hasText(email))
                throw new IllegalArgumentException("O e-mail não pode estar vazio.");

            User user = service.findByEmail(email);

            // Adicionar lógica de envio de e-mail de recuperação de senha

            redirectAttributes.addFlashAttribute(
                "successMessage",
                "E-mail de recuperação de senha enviado para " + user.getEmail()
            );
        } catch (Exception e) {
            view = new ModelAndView("redirect:/forgot-password");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } finally {
            return view;
        }
    }

}
