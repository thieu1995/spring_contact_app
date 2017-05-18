/* https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-two-crud/ */

/* http://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/CrudRepository.html */

/* http://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repositories.definition */

package com.repository;

import com.domain.User;
import com.repository.base.UserBaseRepository;

import java.util.List;

public interface UserRepository extends UserBaseRepository<User, Integer> {

    // from parent's interface
    /*
        User save(User user);
        Optional<User> findOne(int id);
        User findOneById(int id);
        List<User> findAll();
        void delete(User user);
        void deleteById(int id);
    */

    // Using in BookmarkRestController
    List<User> findAllByEmail(String email);

    // just in this interface. Using for UserDetailsServiceImpl
    User findByEmail(String email);

    // Using for UserService
    List<User> findAllByEmailContaining(String email);
    List<User> findAllByFullnameContaining(String fullname);

    /*   from CrudRepository <User, Integer>

    <S extends T> S save(S var1);
    <S extends T> Iterable<S> save(Iterable<S> var1);

    T findOne(ID var1);

    boolean exists(ID var1);

    Iterable<T> findAll();
    Iterable<T> findAll(Iterable<ID> var1);

    long count();

    void delete(ID var1);
    void delete(T var1);
    void delete(Iterable<? extends T> var1);
    void deleteAll();

    */
}