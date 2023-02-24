package br.com.ferraz.codandoavida.service;

import br.com.ferraz.codandoavida.dto.PostDTO;
import br.com.ferraz.codandoavida.enums.PostStatus;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.model.Post;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostService {

    final private PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Page<Post> findAll(PostDTO dto, Pageable pageable) {
        return this.repository.findAll(dto.getTitle(), dto.getCategory(), dto.getStatus(), dto.getCreationUser(), pageable);
    }

    public List<User> findAuthors() {
        return repository.findAuthors();
    }

    public Post findById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(Post obj) {
        repository.save(obj);
    }

    public Post inactivate(Integer id) {
        Post obj = findById(id);
        obj.inactivate();
        save(obj);
        return obj;
    }

    public Page<Post> findAllPublishedByDate(Pageable pageable) {
        return repository.findByStatusOrderByPublishDateDesc(PostStatus.PUBLISHED, pageable);
    }

    public Post findByTitle(String title) {
        title = title.replace("-", " ");
        return repository.findByTitle(title).orElseThrow();
    }

    public List<Post> getRelated(Integer id) {
        Post obj = findById(id);
        List<Post> list = repository.findTop3ByCategoryAndStatusAndIdIsNotOrderByPublishDateDesc(obj.getCategory(), PostStatus.PUBLISHED, id);
        return list;
    }

    public List<Post> findByCategory(Category category) {
        return repository.findByCategoryAndStatusOrderByPublishDateDesc(category, PostStatus.PUBLISHED);
    }
}
