package com.service;

import com.domain.Contact;
import java.util.List;

/* https://spring.io/guides/tutorials/bookmarks/  */

public interface ContactService {

    Iterable<Contact> findAll();

    List<Contact> search(String q);

    Contact findOne(int id);

    void save(Contact contact);

    void delete(int id);
}