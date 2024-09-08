package com.emazon.ms_user.domain.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String name;
    private String lastName;
    private Long idNumber;
    private String number;
    private LocalDate birthDate;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Long id, String name, String lastName, Long idNumber, String number, LocalDate birthDate, String username, String password, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.number = number;
        this.birthDate = birthDate;
        this.username = username;
        this.password = password;
        this.addRoles(roles);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void addRoles(Set<Role> roles) {
        this.roles.addAll(roles);
    }
}
