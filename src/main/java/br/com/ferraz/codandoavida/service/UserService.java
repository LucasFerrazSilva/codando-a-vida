package br.com.ferraz.codandoavida.service;

import br.com.ferraz.codandoavida.dto.UserDTO;
import br.com.ferraz.codandoavida.enums.Status;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<User> findAllActive() {
        return repository.findByStatusIs("ACTIVE");
    }

    public Page<User> findAllActive(UserDTO dto, Pageable pageable) {
        return repository.findByStatusAndNameAndEmailAndRole(
            Status.ACTIVE, dto.getName(), dto.getEmail(), dto.getRole(), pageable
        );
    }

    public User findById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(User obj) {
        repository.save(obj);
    }

    @Transactional
    public void inactivate(User user) {
        user.inactivate();
        repository.save(user);
    }
}
