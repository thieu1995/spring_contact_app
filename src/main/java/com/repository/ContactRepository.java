/* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories */

/* http://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#repositories.single-repository-behaviour */

package com.repository;

import java.util.List;
import com.domain.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findByNameContaining(String q);

}