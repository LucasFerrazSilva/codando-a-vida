package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.dto.UserDTO;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView list(UserDTO dto, @PageableDefault(size=5) Pageable pageable) {
        ModelAndView view = new ModelAndView("user/list");
        Page<User> page = service.findAllActive(dto, pageable);
        view.addObject("users", page);
        view.addObject("dto", dto);
        return view;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView view = new ModelAndView("user/form");
        view.addObject("user", new User());
        return view;
    }

    @GetMapping("/{id}")
    public ModelAndView update(@PathVariable(value="id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            User user = service.findById(id);
            ModelAndView view = new ModelAndView("user/form");
            view.addObject("user", user);
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
            obj.update(dto);
            dto.validate(bindingResult);
            service.save(obj);

            ModelAndView view = new ModelAndView("redirect:/user");
            redirectAttributes.addFlashAttribute("successMessage", "Usuário " + obj.getName() + " salvo com sucesso!");
            return view;
        } catch (IllegalArgumentException e) {
            ModelAndView view = new ModelAndView("user/form");
            view.addObject("message", e.getMessage());
            view.addObject("user", obj);
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
