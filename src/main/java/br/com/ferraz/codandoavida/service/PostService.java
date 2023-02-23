package br.com.ferraz.codandoavida.service;

import br.com.ferraz.codandoavida.model.Post;
import br.com.ferraz.codandoavida.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    final private PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Page<Post> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
