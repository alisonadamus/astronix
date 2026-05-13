package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.dto.RegisterDto;
import com.alisonadamus.astronix.model.Role;
import com.alisonadamus.astronix.model.User;
import com.alisonadamus.astronix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

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

    public List<User> getAllUsers(String search) {
        if (search != null && !search.isEmpty()) {
            return userRepository.findByLoginContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
        }
        return userRepository.findAll();
    }

    public void updateRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));
        user.setRole(role);
        userRepository.save(user);
    }
}