package com.alisonadamus.astronix.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank(message = "Email не може бути порожнім")
    @Email(message = "Невірний формат email")
    private String email;

    @NotBlank
    @Size(min = 3, max = 30, message = "Логін має бути від 3 до 30 символів")
    private String login;

    @NotBlank
    @Size(min = 6, message = "Пароль має містити мінімум 6 символів")
    private String password;

    @NotBlank(message = "Підтвердження паролю не може бути порожнім")
    private String confirmPassword;
}