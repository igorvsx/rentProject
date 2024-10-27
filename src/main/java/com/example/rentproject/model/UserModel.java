package com.example.rentproject.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
    @Pattern(regexp = ".*\\d.*", message = "Пароль должен содержать хотя бы одну цифру")
    @Pattern(regexp = ".*\\W.*", message = "Пароль должен содержать хотя бы один специальный символ")
    private String password;

    private boolean active;

    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> roles;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public @NotBlank(message = "Имя пользователя не может быть пустым") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Имя пользователя не может быть пустым") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Email не может быть пустым") @Email(message = "Некорректный формат email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email не может быть пустым") @Email(message = "Некорректный формат email") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Пароль не может быть пустым") @Size(min = 8, message = "Пароль должен быть не менее 8 символов") @Pattern(regexp = ".*\\d.*", message = "Пароль должен содержать хотя бы одну цифру") @Pattern(regexp = ".*\\W.*", message = "Пароль должен содержать хотя бы один специальный символ") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Пароль не может быть пустым") @Size(min = 8, message = "Пароль должен быть не менее 8 символов") @Pattern(regexp = ".*\\d.*", message = "Пароль должен содержать хотя бы одну цифру") @Pattern(regexp = ".*\\W.*", message = "Пароль должен содержать хотя бы один специальный символ") String password) {
        this.password = password;
    }

    public Set<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
