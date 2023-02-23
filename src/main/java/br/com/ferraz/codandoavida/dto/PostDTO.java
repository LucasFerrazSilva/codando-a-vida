package br.com.ferraz.codandoavida.dto;

import br.com.ferraz.codandoavida.enums.PostStatus;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.model.User;

public class PostDTO {

    private Integer id;
    private String title;
    private String body;
    private Category category;
    private PostStatus status;
    private User creationUser;

    public PostDTO() {}

    public PostDTO(Integer id, String title, String body, Category category, PostStatus status, User creationUser) {
        this.id = id;
        this.title = title;
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
}
