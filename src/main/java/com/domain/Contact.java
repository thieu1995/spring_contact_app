package com.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.custom.ValidEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @ValidEmail
    @Column(name = "email")
    private String email;

    @NotEmpty
    @Column(name = "phone")
    private String phone;

    @ManyToMany(mappedBy = "contacts")
    private Set<User> users;

    public Contact() {
        super();
    }

    public Contact(int id, String name, String email, String phone) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}