package com.beehyv.case_study.controller;

import com.beehyv.case_study.dto.ResultDTO;
import com.beehyv.case_study.dto.UserProfileDTO;
import com.beehyv.case_study.services.UserManager;
import com.beehyv.case_study.utilities.ObjectMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserProfileController {

    @Autowired
    UserManager userManager;

    @GetMapping("/getProfile/{userId}")
    ResponseEntity getUserProfile(@PathVariable String userId){
        UserProfileDTO userProfileDTO = userManager.getUserProfileById(Integer.parseInt(userId));
        if (userProfileDTO != null) {
            return ResponseEntity.ok().body(userProfileDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateProfile")
    ResponseEntity updateUserProfile(@RequestBody String json){
        try {
            UserProfileDTO userProfileDTO = ObjectMapperImpl.getObjectFromJson(json, UserProfileDTO.class);
            if(userManager.updateUser(userProfileDTO)){
                return ResponseEntity.ok().body(new ResultDTO("success"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(new ResultDTO("failure"));
    }
}
