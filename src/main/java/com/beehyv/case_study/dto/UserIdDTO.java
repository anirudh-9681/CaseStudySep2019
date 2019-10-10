package com.beehyv.case_study.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIdDTO {

    @JsonProperty(value = "userId")
    private long userId;

    public UserIdDTO(){

    }

    @JsonCreator
    public UserIdDTO(long userId){
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserIdDTO{" +
                "userId=" + userId +
                '}';
    }
}
