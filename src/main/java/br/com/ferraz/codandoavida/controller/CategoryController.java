package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.dto.CategoryDTO;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.service.CategoryService;
import br.com.ferraz.codandoavida.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

}
