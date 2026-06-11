package com.rescue.management.service;

import com.rescue.management.model.User;
import com.rescue.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    public Optional<User> authenticate(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<User> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            if (updatedUser.getName() != null) user.setName(updatedUser.getName());
            if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
            if (updatedUser.getContact() != null) user.setContact(updatedUser.getContact());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) user.setPassword(updatedUser.getPassword());
            if (updatedUser.getRole() != null) user.setRole(updatedUser.getRole());
            if (updatedUser.getTeamId() != null) user.setTeamId(updatedUser.getTeamId());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
