package com.service;

import com.domain.User;

import java.util.List;

public interface UserService {

    Iterable<User> findAll();

    List<User> searchByEmail(String email);

    List<User> searchByFullname(String fullname);

    User findOneById(int id);

    User findOneByEmail(String email);

    void save(User user);

    void deleteById(int id);
}