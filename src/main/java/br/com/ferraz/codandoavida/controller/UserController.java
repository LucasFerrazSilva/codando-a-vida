package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.dto.UserDTO;
import br.com.ferraz.codandoavida.enums.UserRole;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ModelAndView list(UserDTO dto, @PageableDefault(size=5) Pageable pageable) {
        ModelAndView view = new ModelAndView("user/list");
        Page<User> page = service.findAllActive(dto, pageable);
        view.addObject("users", page);
        view.addObject("dto", dto);
        view.addObject("userRoles", UserRole.values());
        return view;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView view = new ModelAndView("user/form");
        view.addObject("user", new User());
        view.addObject("userRoles", UserRole.values());
        return view;
    }

    @GetMapping("/{id}")
    public ModelAndView updateRoleForm(@PathVariable(value="id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            User user = service.findById(id);
            ModelAndView view = new ModelAndView("user/edit_role_form");
            view.addObject("user", user);
            view.addObject("userRoles", UserRole.values());
            return view;
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", "Invalid id " + id);
            return new ModelAndView("redirect:/user");
        }
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid UserDTO dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User obj = (dto.getId() != null ? service.findById(dto.getId()) : new User());

        try {
            dto.encodePassword(passwordEncoder);
            obj.create(dto);
            dto.validate(bindingResult);
            service.save(obj);

            ModelAndView view = new ModelAndView(UserRole.ADMIN.equals(obj.getRole()) ? "redirect:/user" : "redirect:/");
            redirectAttributes.addFlashAttribute("successMessage", "Usuário " + obj.getName() + " salvo com sucesso!");
            return view;
        } catch (IllegalArgumentException e) {
            ModelAndView view = new ModelAndView("user/form");
            view.addObject("message", e.getMessage());
            view.addObject("user", obj);
            view.addObject("userRoles", UserRole.values());
            return view;
        }
    }

    @PostMapping("/save/{id}")
    public ModelAndView update(@Valid UserDTO dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User obj = service.findById(dto.getId());

        try {
            dto.encodePassword(passwordEncoder);
            obj.update(dto);
            service.save(obj);

            ModelAndView view = new ModelAndView("redirect:/user");
            redirectAttributes.addFlashAttribute("successMessage", "Usuário " + obj.getName() + " atualizado com sucesso!");
            return view;
        } catch (IllegalArgumentException e) {
            ModelAndView view = new ModelAndView("user/edit_role_form");
            view.addObject("message", e.getMessage());
            view.addObject("user", obj);
            view.addObject("userRoles", UserRole.values());
            return view;
        }
    }

    @GetMapping("/delete/{id}")
    public String inactivate(@PathVariable(value="id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            User user = service.findById(id);
            service.inactivate(user);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário " + user.getName() + " inativado com sucesso!");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", "ID inválido " + id);
        }
        finally {
            return "redirect:/user";
        }
    }

}
