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

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView view = buildFormModelAndView(new Post());

        return view;
    }

    @GetMapping("/{id}")
    public ModelAndView update(@PathVariable(value="id")Integer id, RedirectAttributes redirectAttributes) {
        try {
            ModelAndView view = buildFormModelAndView(service.findById(id));

            return view;
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", "ID inválido: " + id);
            return new ModelAndView("redirect:/post");
        }
    }

    private ModelAndView buildFormModelAndView(Post obj) {
        ModelAndView view = new ModelAndView("post/form");
        view.addObject("obj", obj);

        List<Category> categories = categoryService.findAllActive();
        view.addObject("categories", categories);

        List<User> admins = userService.findActiveAdmins();
        view.addObject("admins", admins);

        view.addObject("postStatus", PostStatus.values());

        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid PostDTO dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Post obj = (dto.getId() != null ? service.findById(dto.getId()) : new Post());

        try {
            obj.update(dto);
            dto.validate(bindingResult);
            service.save(obj);
            redirectAttributes.addFlashAttribute("successMessage", "Post " + obj.getTitle() + " salvo com sucesso!");
            return new ModelAndView("redirect:/post");
        } catch (Exception e) {
            ModelAndView view = buildFormModelAndView(obj);
            view.addObject("message", e.getMessage());
            return view;
        }
    }

    @GetMapping("/delete/{id}")
    public ModelAndView inactivate(@PathVariable(value="id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            Post obj = service.inactivate(id);
            redirectAttributes.addFlashAttribute("successMessage", "Postagem " + obj.getTitle() + " inativada com sucesso!");
            return new ModelAndView("redirect:/post");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", "ID inválido " + id);
            return new ModelAndView("redirect:/post");
        }
    }

    @GetMapping("/{categoryName}/{postTitle}")
    public ModelAndView showPost(@PathVariable(value="categoryName") String categoryName,
                                 @PathVariable(value="postTitle") String postTitle, RedirectAttributes redirectAttributes) {
        try {
            Post obj = service.findByTitle(postTitle);
            ModelAndView view = new ModelAndView("post/read");
            view.addObject("post", obj);
            return view;
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", "URL inválida");
            return new ModelAndView("redirect:/");
        }
    }

}
