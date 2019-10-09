package com.beehyv.case_study.entities;

import com.beehyv.case_study.dto.UserProfileDTO;
import com.beehyv.case_study.utilities.Address;
import com.beehyv.case_study.utilities.AddressFormatter;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String name;
    private String email;
    private String phone;

    @Convert(converter = AddressFormatter.class)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserProfileDTO getDTO(){
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUserId(userId);
        userProfileDTO.setName(name);
        userProfileDTO.setEmail(email);
        userProfileDTO.setPhone(phone);
        userProfileDTO.setAddress(address);
        return userProfileDTO;
    }

    public void setDTO(UserProfileDTO userProfileDTO){
//        userId = userProfileDTO.getUserId(); // never change userId even though in this case, replacing with same value
        if (Objects.nonNull(userProfileDTO.getName())) name = userProfileDTO.getName();
        if (Objects.nonNull(userProfileDTO.getEmail())) email = userProfileDTO.getEmail();
        if (Objects.nonNull(userProfileDTO.getPhone())) phone = userProfileDTO.getPhone();
        if (Objects.nonNull(userProfileDTO.getAddress())) address = userProfileDTO.getAddress();
    }
}
