package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.model.Post;
import br.com.ferraz.codandoavida.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    final private PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView index(@PageableDefault Pageable pageable) {
        ModelAndView view = new ModelAndView("index");

        Page<Post> posts = postService.findAllPublishedByDate(pageable);
        view.addObject("posts", posts);

        return view;
    }

}
