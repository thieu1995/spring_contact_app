/*  http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#types-custom  */

package com.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Email
    @NotEmpty
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "fullname", nullable = false)
    private String fullname;


    @Column(name = "gender", nullable = false)
    private String gender;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;


    @Column(name = "phone", nullable = false)
    private String phone;

    @NotEmpty
    @Column(name = "status", nullable = false)
    private String status;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    private Date createdAt;


    @ManyToMany()
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


    @ManyToMany
    @JoinTable(
            name = "user_contact",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private Set<Contact> contacts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Bookmark> bookmarks;


    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }
    public void setBookmarks(Set<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public User() {}   // jpa only

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equals("") | status.equals(null)) {
            status = "active";
        }
        this.status = status;
        System.out.println("Status is: " + status);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        if(createdAt == null) {
            createdAt = new Date();
        }
        this.createdAt = createdAt;
        System.out.println("Date is: " + createdAt);
    }
}