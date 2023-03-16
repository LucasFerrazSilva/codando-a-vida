package br.com.ferraz.codandoavida.dto;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class ChangePasswordDTO {

    @NotNull(message = "usuário não encontrado")
    private Integer userId;
    @NotBlank(message = "a nova senha não pode estar vazia")
    private String password;
    @NotBlank(message = "a confirmação da nova senha não pode estar vazia")
    private String confirmPassword;

    public ChangePasswordDTO() {}

    public ChangePasswordDTO(Integer userId, String password, String confirmPassword) {
        this.userId = userId;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }


    public boolean passwordsDoesNotMatch() {
        return !password.equals(confirmPassword);
    }

    public void validate(BindingResult bindingResult) {
        String errorsMessage = "";

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errorsMessage = errors.stream().map(FieldError::getDefaultMessage).collect(joining(", "));
        }

        if (passwordsDoesNotMatch()) {
            errorsMessage = (
                StringUtils.hasText(errorsMessage) ?
                    errorsMessage + ", as senhas devem ser iguais"
                    : "as senhas devem ser iguais"
            );
        }

        if (StringUtils.hasText(errorsMessage))
            throw new IllegalArgumentException("Erros: " + errorsMessage + ".");
    }
}
