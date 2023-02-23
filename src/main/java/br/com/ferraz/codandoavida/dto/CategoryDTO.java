package br.com.ferraz.codandoavida.dto;

import br.com.ferraz.codandoavida.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class CategoryDTO {

    private Integer id;

    @NotBlank(message="o campo Nome não pode estar vazio")
    private String name;

    @NotNull(message="o campo Criador não pode estar vazio")
    private User creationUser;

    public CategoryDTO() {}

    public CategoryDTO(Integer id, String name, User creationUser) {
        this.id = id;
        this.name = name;
        this.creationUser = creationUser;
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

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
    }

    public void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            String errorsMessage = errors.stream().map(FieldError::getDefaultMessage).collect(joining(", "));
            String formattedErrorMessage = "Erros: " + errorsMessage + ".";
            throw new IllegalArgumentException(formattedErrorMessage);
        }
    }
}
