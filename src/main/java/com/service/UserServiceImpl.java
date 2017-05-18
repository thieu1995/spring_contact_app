package com.service;

import com.domain.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> searchByEmail(String email) {
        return userRepository.findAllByEmailContaining(email);
    }

    @Override
    public List<User> searchByFullname(String fullname) {
        return userRepository.findAllByFullnameContaining(fullname);
    }

    @Override
    public User findOneById(int id) {
        return userRepository.findOneById(id);
    }

    @Override
    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findAllByName("ROLE_MEMBER"));
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}