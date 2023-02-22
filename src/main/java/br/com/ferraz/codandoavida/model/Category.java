package br.com.ferraz.codandoavida.model;

import br.com.ferraz.codandoavida.enums.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="TB_CATEGORIES")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="NAME", nullable = false, length=100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name="CREATION_USER_ID")
    private User creationUser;

    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
    @Column(name="CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;

    public Category() {}

    public Category(Integer id, String name, Status status, User creationUser, LocalDateTime creationDate,
                    LocalDateTime updateDate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.creationUser = creationUser;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
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
        Category category = (Category) o;
        return id.equals(category.id) && name.equals(category.name) && status == category.status && Objects.equals(creationUser, category.creationUser) && creationDate.equals(category.creationDate) && Objects.equals(updateDate, category.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, creationUser, creationDate, updateDate);
    }

}
