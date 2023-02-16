package br.com.ferraz.codandoavida.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="TB_USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="NAME", nullable = false, length=100)
    private String name;

    @Column(name="PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name="ROLE", nullable = false, length = 100)
    private String role;

    @Column(name="CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password)
                && Objects.equals(role, user.role) && Objects.equals(creationDate, user.creationDate)
                && Objects.equals(updateDate, user.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, role, creationDate, updateDate);
    }

}
