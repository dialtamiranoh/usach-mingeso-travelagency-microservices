package com.travelagency.msusuarios.service;

import com.travelagency.msusuarios.entity.UserEntity;
import com.travelagency.msusuarios.repository.UserRepository;
import com.travelagency.msusuarios.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    public List<UserEntity> findAll() { return userRepository.findAll(); }

    public Optional<UserEntity> findById(Long id) { return userRepository.findById(id); }

    public Optional<UserEntity> findByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity save(UserEntity user) {
        if (user.getStatus() == null) {
            statusRepository.findByNameAndEntityType("ACTIVE", "USER")
                .ifPresent(user::setStatus);
        }
        return userRepository.save(user);
    }

    public UserEntity update(UserEntity user) { return userRepository.save(user); }

    public void deleteById(Long id) {
        UserEntity user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Soft delete - marcar como inactivo
        statusRepository.findByNameAndEntityType("INACTIVE", "USER")
            .ifPresent(user::setStatus);
        userRepository.save(user);
    }

    // Endpoint interno para que otros MS validen si un usuario existe y esta activo
    public boolean isUserActive(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
            .map(u -> u.getStatus() != null && u.getStatus().getName().equals("ACTIVE"))
            .orElse(false);
    }
}
