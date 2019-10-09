package com.beehyv.case_study.dto;

import com.beehyv.case_study.utilities.Address;

import java.util.Objects;

public class UserProfileDTO{
    private int userId;
    private String name;
    private String email;
    private String phone;
    private Address address;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isValid(){
        return !Objects.isNull(name) && !Objects.isNull(email) && !Objects.isNull(phone) && !Objects.isNull(address);
    }
}
