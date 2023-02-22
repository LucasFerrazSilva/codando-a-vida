package br.com.ferraz.codandoavida.dto;

import br.com.ferraz.codandoavida.model.User;

public class CategoryDTO {

    private String name;
    private User creationUser;

    public CategoryDTO() {}

    public CategoryDTO(String name, User creationUser) {
        this.name = name;
        this.creationUser = creationUser;
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
}
