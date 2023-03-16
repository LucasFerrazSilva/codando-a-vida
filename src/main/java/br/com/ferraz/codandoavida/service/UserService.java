package br.com.ferraz.codandoavida.service;

import br.com.ferraz.codandoavida.dto.ChangePasswordDTO;
import br.com.ferraz.codandoavida.dto.UserDTO;
import br.com.ferraz.codandoavida.enums.ForgottenPasswordStatus;
import br.com.ferraz.codandoavida.enums.Status;
import br.com.ferraz.codandoavida.enums.UserRole;
import br.com.ferraz.codandoavida.model.ForgottenPassword;
import br.com.ferraz.codandoavida.model.User;
import br.com.ferraz.codandoavida.repository.ForgottenPasswordRepository;
import br.com.ferraz.codandoavida.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final ForgottenPasswordRepository forgottenPasswordRepository;
    private final JavaMailSender sender;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, ForgottenPasswordRepository forgottenPasswordRepository, JavaMailSender sender, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.forgottenPasswordRepository = forgottenPasswordRepository;
        this.sender = sender;
        this.passwordEncoder = passwordEncoder;
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
        if (repository.existsByIdIsNotAndEmailIsCustomQuery(obj.getId(), obj.getEmail()))
            throw new IllegalArgumentException("E-mail já está em uso!");

        repository.save(obj);
    }

    @Transactional
    public void inactivate(User user) {
        user.inactivate();
        repository.save(user);
    }

    public List<User> findActiveAdmins() {
        return repository.findActiveAdmins(UserRole.ADMIN, Status.ACTIVE);
    }

    public User findByEmail(String email) {
        return repository
                .findByStatusAndEmail(Status.ACTIVE, email)
                .orElseThrow(() -> new NoSuchElementException("Nenhum usuário encontrado para o e-mail " + email));
    }

    public User sendNewPasswordLinkByEmail(String email) {
        if (!StringUtils.hasText(email))
            throw new IllegalArgumentException("O e-mail não pode estar vazio.");

        User user = findByEmail(email);

        ForgottenPassword forgottenPassword = new ForgottenPassword(user);
        forgottenPasswordRepository.save(forgottenPassword);
        String link = "http://localhost:8080/change-password/" + forgottenPassword.getGeneratedHash();

        // Adicionar lógica de envio de e-mail de recuperação de senha
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("codandoavida@gmail.com");
        message.setTo(email);
        message.setSubject("Recuperação de Senha - Codando a Vida");
        message.setText("Aqui está o link para que você possa redefinir sua senha: " + link);
        sender.send(message);

        return user;
    }

    public User findByGeneratedHash(String generatedHash) {
        ForgottenPassword forgottenPassword =
                forgottenPasswordRepository.findByGeneratedHashAndStatus(generatedHash, ForgottenPasswordStatus.NEW)
                        .orElseThrow(() -> new NoSuchElementException("Parece que o link expirou ou já foi usado. Solicite um novo link."));

        return forgottenPassword.getUser();
    }

    @Transactional
    public void changePassword(ChangePasswordDTO dto, String generatedHash) {
        User user = repository.findById(dto.getUserId()).orElseThrow(() -> new NoSuchElementException("Nenhum usuário encontrado para o id " + dto.getUserId()));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(user);

        Optional<ForgottenPassword> forgottenPassword = forgottenPasswordRepository.findByGeneratedHashAndStatus(generatedHash, ForgottenPasswordStatus.NEW);

        forgottenPassword.ifPresent(obj -> {
            obj.setStatus(ForgottenPasswordStatus.USED);
            forgottenPasswordRepository.save(obj);
        });
    }
}
