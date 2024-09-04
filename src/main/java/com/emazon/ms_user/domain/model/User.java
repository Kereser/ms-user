package com.emazon.ms_user.domain.model;

import com.emazon.ms_user.infra.out.jpa.entity.RoleEnum;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String lastName;
    private Long idNumber;
    private String number;
    private LocalDate birthDate;
    private String email;
    private String password;
    private RoleEnum role;

    public User() {
    }

    public User(Long id, String name, String lastName, Long idNumber, String number, LocalDate birthDate, String email, String password, RoleEnum role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.number = number;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
