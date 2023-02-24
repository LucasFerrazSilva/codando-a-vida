package br.com.ferraz.codandoavida.dto;

import br.com.ferraz.codandoavida.enums.PostStatus;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class PostDTO {

    private Integer id;
    @NotBlank(message="o campo Título não pode estar vazio")
    private String title;
    @NotBlank(message="o campo Descrição não pode estar vazio")
    private String description;
    @NotBlank(message="o campo Corpo não pode estar vazio")
    private String body;
    @NotNull(message="o campo Categoria não pode estar vazio")
    private Category category;
    @NotNull(message="o campo Status não pode estar vazio")
    private PostStatus status;
    @NotNull(message="o campo Autor não pode estar vazio")
    private User creationUser;

    public PostDTO() {}

    public PostDTO(Integer id, String title, String description, String body, Category category, PostStatus status,
                   User creationUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.body = body;
        this.category = category;
        this.status = status;
        this.creationUser = creationUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
