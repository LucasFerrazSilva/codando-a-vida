package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.model.Post;
import br.com.ferraz.codandoavida.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
public class HomeController {

    final private PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView index(String search, @PageableDefault(size=5) Pageable pageable) {
        ModelAndView view = new ModelAndView("index");

        Page<Post> posts = postService.findAllPublishedByDate(pageable, search);
        view.addObject("posts", posts);
        view.addObject("search", search);

        return view;
    }

}
