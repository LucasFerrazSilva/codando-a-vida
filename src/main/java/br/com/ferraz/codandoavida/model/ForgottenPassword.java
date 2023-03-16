package br.com.ferraz.codandoavida.model;

import br.com.ferraz.codandoavida.enums.ForgottenPasswordStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="TB_FORGOTTEN_PASSWORD")
public class ForgottenPassword {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Column(name="GENERATED_HASH")
    private String generatedHash;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", columnDefinition = "ENUM('NEW', 'USED')")
    private ForgottenPasswordStatus status;

    public ForgottenPassword() {}

    public ForgottenPassword(Integer id, User user, String generatedHash, ForgottenPasswordStatus status) {
        this.id = id;
        this.user = user;
        this.generatedHash = generatedHash;
        this.status = status;
    }

    public ForgottenPassword(User user) {
        this.user = user;
        this.generatedHash = new BCryptPasswordEncoder().encode(user.getEmail() + LocalDateTime.now()).replace("/", "");
        this.status = ForgottenPasswordStatus.NEW;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGeneratedHash() {
        return generatedHash;
    }

    public void setGeneratedHash(String generatedHash) {
        this.generatedHash = generatedHash;
    }

    public ForgottenPasswordStatus getStatus() {
        return status;
    }

    public void setStatus(ForgottenPasswordStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForgottenPassword that = (ForgottenPassword) o;
        return id.equals(that.id) && user.equals(that.user) && generatedHash.equals(that.generatedHash) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, generatedHash, status);
    }

}
