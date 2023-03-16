package br.com.ferraz.codandoavida.repository;

import br.com.ferraz.codandoavida.enums.ForgottenPasswordStatus;
import br.com.ferraz.codandoavida.model.ForgottenPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgottenPasswordRepository extends JpaRepository<ForgottenPassword, Integer> {

    Optional<ForgottenPassword> findByGeneratedHashAndStatus(String generatedHash, ForgottenPasswordStatus status);

}
