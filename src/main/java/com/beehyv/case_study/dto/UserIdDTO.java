package com.beehyv.case_study.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIdDTO {

    @JsonProperty(value = "userId")
    private int userId;

    public UserIdDTO(){

    }

    @JsonCreator
    public UserIdDTO(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserIdDTO{" +
                "userId=" + userId +
                '}';
    }
}
