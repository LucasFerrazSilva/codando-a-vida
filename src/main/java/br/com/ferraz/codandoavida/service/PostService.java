package br.com.ferraz.codandoavida.service;

import br.com.ferraz.codandoavida.dto.PostDTO;
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
}
