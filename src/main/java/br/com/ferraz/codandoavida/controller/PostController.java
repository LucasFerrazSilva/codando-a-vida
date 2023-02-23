package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.dto.PostDTO;
import br.com.ferraz.codandoavida.enums.PostStatus;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.model.Post;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.service.CategoryService;
import br.com.ferraz.codandoavida.service.PostService;
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
@RequestMapping("/post")
public class PostController {

    final private PostService service;
    final private CategoryService categoryService;
    final private UserService userService;

    public PostController(PostService service, CategoryService categoryService, UserService userService) {
        this.service = service;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView list(PostDTO dto, @PageableDefault(size=5)Pageable pageable) {
        ModelAndView view = new ModelAndView("post/list");
        view.addObject("dto", dto);

        Page<Post> list = service.findAll(dto, pageable);
        view.addObject("list", list);

        List<Category> categories = categoryService.findAllActive();
        view.addObject("categories", categories);

        view.addObject("postStatus", PostStatus.values());

        List<User> authors = service.findAuthors();
        view.addObject("authors", authors);

        return view;
    }

}
