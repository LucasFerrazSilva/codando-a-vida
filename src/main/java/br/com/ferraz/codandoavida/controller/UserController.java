package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView list() {
        ModelAndView view = new ModelAndView("user/list");
        view.addObject("users", service.findAll());
        return view;
    }

}
