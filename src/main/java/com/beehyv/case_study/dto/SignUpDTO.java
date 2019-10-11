package com.beehyv.case_study.dto;

import java.util.Objects;

public class SignUpDTO {
    private String email;
    private String name;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return !Objects.isNull(name) && !Objects.isNull(email) && !Objects.isNull(password);
    }
}
