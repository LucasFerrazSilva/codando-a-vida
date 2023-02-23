package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.dto.CategoryDTO;
import br.com.ferraz.codandoavida.enums.UserRole;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.service.CategoryService;
import br.com.ferraz.codandoavida.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/category")
public class CategoryController {

    final private CategoryService service;
    final private UserService userService;

    public CategoryController(CategoryService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView list(CategoryDTO dto, @PageableDefault(size=5) Pageable pageable) {
        ModelAndView view = new ModelAndView("category/list");

        Page<Category> list = service.findAllActive(dto, pageable);
        view.addObject("categories", list);

        List<User> admins = userService.findActiveAdmins();
        view.addObject("admins", admins);

        view.addObject("dto", dto);

        return view;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView view = new ModelAndView("category/form");

        view.addObject("category", new Category());

        List<User> admins = userService.findActiveAdmins();
        view.addObject("admins", admins);

        return view;
    }

    @GetMapping("/{id}")
    public ModelAndView update(@PathVariable(value="id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            ModelAndView view = new ModelAndView("category/form");

            Category category = service.findById(id);
            view.addObject("category", category);

            List<User> admins = userService.findActiveAdmins();
            view.addObject("admins", admins);

            return view;
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", "Invalid id " + id);
            return new ModelAndView("redirect:/category");
        }
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryDTO dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Category obj = (dto.getId() != null ? service.findById(dto.getId()) : new Category());

        try {
            obj.update(dto);
            dto.validate(bindingResult);
            service.save(obj);

            redirectAttributes.addFlashAttribute("successMessage", "Categoria " + obj.getName() + " salva com sucesso!");
            ModelAndView view = new ModelAndView("redirect:/category");
            return view;
        } catch (IllegalArgumentException e) {
            List<User> admins = userService.findActiveAdmins();

            ModelAndView view = new ModelAndView("category/form");
            view.addObject("message", e.getMessage());
            view.addObject("category", obj);
            view.addObject("admins", admins);
            return view;
        }
    }


}