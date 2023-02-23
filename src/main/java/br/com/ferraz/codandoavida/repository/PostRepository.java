package br.com.ferraz.codandoavida.repository;

import br.com.ferraz.codandoavida.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {



}
