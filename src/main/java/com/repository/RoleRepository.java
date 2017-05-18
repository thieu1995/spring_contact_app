package com.repository;

import com.domain.Role;
import com.repository.base.RoleBaseRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends RoleBaseRepository<Role, Integer> {

    // just in this interface
    Role findByName(String name);

    // Using for UserServiceImpl
    Set<Role> findAllByNameContaining(String name);

    Set<Role> findAllByName(String name);
}