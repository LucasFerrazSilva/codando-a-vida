package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.dto.ChangePasswordDTO;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.joining;

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
            User user = service.sendNewPasswordLinkByEmail(email);

            redirectAttributes.addFlashAttribute(
                "successMessage",
                "E-mail de recuperação de senha enviado para " + user.getEmail()
            );
        } catch (Exception e) {
            e.printStackTrace();
            view = new ModelAndView("redirect:/forgot-password");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } finally {
            return view;
        }
    }

    @GetMapping("/change-password/{generatedHash}")
    public ModelAndView changePasswordForm(@PathVariable(value="generatedHash") String generatedHash, RedirectAttributes redirectAttributes) {
        ModelAndView view = new ModelAndView("login/change_password_form");

        view.addObject("generatedHash", generatedHash);

        try {
            User user = service.findByGeneratedHash(generatedHash);
            view.addObject("userId", user.getId());
        } catch (NoSuchElementException e) {
            view = new ModelAndView("redirect:/forgot-password");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } finally {
            return view;
        }
    }

    @PostMapping("/change-password/{generatedHash}")
    public ModelAndView changePassword(
        @PathVariable(value = "generatedHash") String generatedHash,
        @Valid ChangePasswordDTO dto,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        ModelAndView view = new ModelAndView("redirect:/login");

        try {
            dto.validate(bindingResult);
            service.changePassword(dto, generatedHash);
            redirectAttributes.addFlashAttribute("successMessage", "Senha redefinida com sucesso.");
        } catch (Exception e) {
            view = new ModelAndView("redirect:" + request.getHeader("Referer"));
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } finally {
            return view;
        }
    }

}
