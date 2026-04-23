package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.dto.RegisterDto;
import com.alisonadamus.astronix.model.Role;
import com.alisonadamus.astronix.model.User;
import com.alisonadamus.astronix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email вже використовується");
        }
        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new IllegalArgumentException("Логін вже зайнятий");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Паролі не збігаються");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getLogin());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
    }
}