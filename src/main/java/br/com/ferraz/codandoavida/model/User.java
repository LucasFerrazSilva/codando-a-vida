package br.com.ferraz.codandoavida.model;

import br.com.ferraz.codandoavida.dto.UserDTO;
import br.com.ferraz.codandoavida.enums.Status;
import br.com.ferraz.codandoavida.enums.UserRole;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name="TB_USERS")
public class User implements Serializable, UserDetails {

    public static final long serialVersionUID = 1L;

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

    @Enumerated(EnumType.STRING)
    @Column(name="ROLE", nullable = false, length = 100)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", nullable = false, length = 100)
    private Status status;

    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
    @Column(name="CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;

    public User() {
        this.creationDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    public User(Integer id, String name, String email, String password, UserRole role, Status status,
                LocalDateTime creationDate, LocalDateTime updateDate) {
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
        this.password = Optional.ofNullable(dto.getPassword()).orElse(this.password) ;
        this.role = dto.getRole();
        this.updateDate = LocalDateTime.now();
    }

    public void inactivate() {
        this.status = Status.INACTIVE;
        this.updateDate = LocalDateTime.now();
    }


    // User Details implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.role);
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return (Status.ACTIVE.equals(this.status) ? true : false);
    }
    //

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
