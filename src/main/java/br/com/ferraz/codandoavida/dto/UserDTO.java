package br.com.ferraz.codandoavida.dto;

import br.com.ferraz.codandoavida.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class UserDTO {

    private Integer id;
    @NotBlank(message="o campo Nome não pode estar vazio")
    private String name;
    @NotBlank(message="o campo E-mail não pode estar vazio")
    private String email;
    private String password;
    @NotNull(message="o campo Permissões não pode estar vazio")
    private UserRole role;

    public UserDTO() {}

    public UserDTO(Integer id, String name, String email, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            String errorsMessage = errors.stream().map(FieldError::getDefaultMessage).collect(joining(", "));
            String formattedErrorMessage = "Erros: " + errorsMessage + ".";
            throw new IllegalArgumentException(formattedErrorMessage);
        }
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        if (this.password != null)
            this.password = passwordEncoder.encode(this.password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}
