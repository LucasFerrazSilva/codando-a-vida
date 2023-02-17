package br.com.ferraz.codandoavida.service;

import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(User obj) {
        repository.save(obj);
    }
}
