package br.com.ferraz.codandoavida.controller;

import br.com.ferraz.codandoavida.model.Post;
import br.com.ferraz.codandoavida.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/post")
public class PostController {

    final private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView list(@PageableDefault(size=5)Pageable pageable) {
        ModelAndView view = new ModelAndView("post/list");

        Page<Post> list = service.findAll(pageable);
        view.addObject("list", list);

        return view;
    }

}
