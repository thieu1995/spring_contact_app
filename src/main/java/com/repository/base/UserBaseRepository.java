package com.repository.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository<User, Integer extends Serializable> extends Repository<User, Integer> {
    User save(User user);
    Optional<User> findOne(int id);
    User findOneById(int id);
    List<User> findAll();
    void delete(User user);
    void deleteById(int id);
}
