package com.beehyv.case_study.controller;

import com.beehyv.case_study.dto.SignUpDTO;
import com.beehyv.case_study.dto.UserIdDTO;
import com.beehyv.case_study.services.UserManager;
import com.beehyv.case_study.utilities.ObjectMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    @Autowired
    UserManager userManager;

    @PostMapping("/signup")
    ResponseEntity commitNewUser(@RequestBody String json) {
        SignUpDTO signUpDTO;
        try {
            signUpDTO = ObjectMapperImpl.getObjectFromJson(json,SignUpDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        int id = userManager.addUser(signUpDTO);
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(new UserIdDTO(id));
    }
}
