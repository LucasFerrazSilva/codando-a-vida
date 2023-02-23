package br.com.ferraz.codandoavida.model;

import br.com.ferraz.codandoavida.enums.PostStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="TB_POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="TITLE", nullable = false)
    private String title;

    @Column(name="BODY", nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", columnDefinition = "ENUM('DRAFT', 'PUBLISHED', 'INACTIVE')")
    private PostStatus status;

    @ManyToOne
    @JoinColumn(name="CREATION_USER_ID")
    private User creationUser;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name="CREATION_DATE")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name="UPDATE_USER_ID")
    private User updateUser;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;

    public Post() {
        this.creationDate = LocalDateTime.now();
        this.status = PostStatus.DRAFT;
    }

    public Post(Integer id, String title, String body, Category category, PostStatus status, User creationUser,
                LocalDateTime creationDate, User updateUser, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.status = status;
        this.creationUser = creationUser;
        this.creationDate = creationDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
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
        Post post = (Post) o;
        return id.equals(post.id) && title.equals(post.title) && body.equals(post.body) && category.equals(post.category) && status == post.status && creationUser.equals(post.creationUser) && creationDate.equals(post.creationDate) && Objects.equals(updateUser, post.updateUser) && Objects.equals(updateDate, post.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, category, status, creationUser, creationDate, updateUser, updateDate);
    }
}
