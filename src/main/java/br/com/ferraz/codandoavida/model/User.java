package br.com.ferraz.codandoavida.model;

import br.com.ferraz.codandoavida.dto.UserDTO;

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

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name="ROLE", nullable = false, length = 100)
    private String role;

    @Column(name="STATUS", nullable = false, length = 100)
    private String status;

    @Column(name="CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;

    public User() {
        this.creationDate = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    public User(Integer id, String name, String email, String password, String role, String status, LocalDateTime creationDate, LocalDateTime updateDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public void update(UserDTO dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.role = dto.getRole();
        this.updateDate = LocalDateTime.now();
    }

    public void inactivate() {
        this.status = "INACTIVE";
        this.updateDate = LocalDateTime.now();
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return id.equals(user.id) && name.equals(user.name) && email.equals(user.email) && password.equals(user.password) && role.equals(user.role) && status.equals(user.status) && creationDate.equals(user.creationDate) && Objects.equals(updateDate, user.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, role, status, creationDate, updateDate);
    }
}
