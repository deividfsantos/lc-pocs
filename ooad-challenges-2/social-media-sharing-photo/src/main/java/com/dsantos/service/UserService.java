package com.dsantos.service;

import com.dsantos.domain.User;
import com.dsantos.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String email) {
        return userRepository.save(new User(username, email));
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updateBio(String userId, String bio) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.setBio(bio);
        return userRepository.save(user);
    }

    public User updateProfilePicture(String userId, String url) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.setProfilePictureUrl(url);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.delete(id);
    }
}

