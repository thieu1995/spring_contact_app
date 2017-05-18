package com.repository.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RoleBaseRepository<Role, Integer extends Serializable> extends Repository<Role, Integer> {
    Role save(Role Role);
    Role findOneById(int id);
    List<Role> findAll();
    void delete(Role Role);
}
