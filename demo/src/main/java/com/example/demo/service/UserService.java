package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public List<User> updateAll(List<User> users) {
        return users.stream().map(user -> {
            Optional<User> existingUser = userRepository.findById(user.getId());
            if (existingUser.isPresent()) {
                User updatedUser = existingUser.get();
                updatedUser.setName(user.getName());
                updatedUser.setPosition(user.getPosition());
                updatedUser.setSalary(user.getSalary());
                return userRepository.save(updatedUser);
            } else {
                throw new RuntimeException("User Id tidak temukan : " + user.getId());
            }
        }).toList();
    }

    
    @Transactional
    public List<User> saveAll(List<User> users) {
        return userRepository.saveAll(users);
    }

    @Transactional
    public void deleteAll(List<Long> ids) {
        ids.forEach(userRepository::deleteById);
    }
}
