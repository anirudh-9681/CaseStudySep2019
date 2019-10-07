package com.beehyv.case_study.services;

import com.beehyv.case_study.dto.SignUpDTO;
import com.beehyv.case_study.dto.UserProfileDTO;
import com.beehyv.case_study.entities.MyUser;
import com.beehyv.case_study.entities.MyUserCredentials;
import com.beehyv.case_study.repositories.MyUserCredentialsRepo;
import com.beehyv.case_study.repositories.MyUserRepo;
import com.beehyv.case_study.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

    @Autowired
    MyUserRepo myUserRepo;
    @Autowired
    MyUserCredentialsRepo myUserCredentialsRepo;
    @Autowired
    PasswordEncoder passwordEncoder;


    public int addUser(SignUpDTO signUpDTO) {
        if(signUpDTO == null){
            return -1;
        }
        if (myUserCredentialsRepo.existsByEmail(signUpDTO.getEmail())) {
            return -1;
        }
        MyUser tmp = new MyUser();
        tmp.setName(signUpDTO.getName());
        tmp.setEmail(signUpDTO.getEmail());
        tmp = myUserRepo.save(tmp);
        MyUserCredentials myUserCredentials = new MyUserCredentials();
        myUserCredentials.setEmail(signUpDTO.getEmail());
        //TODO make sure javascript encodes into base64 and password is never decoded from base64
        myUserCredentials.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        myUserCredentials.setAuthorities("USER");
        myUserCredentials.setMyUser(tmp);
        myUserCredentialsRepo.save(myUserCredentials);
        return tmp.getUserId();
    }

    public int getLoggedInUserId(){
        return ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMyUserCredentials().getUserId();
    }

    public boolean updateUser(UserProfileDTO userProfileDTO){
        if (userProfileDTO.getUserId() != getLoggedInUserId()){
            return false;
        }
        MyUser myUser = myUserRepo.getByUserId(userProfileDTO.getUserId());
        myUser.setDTO(userProfileDTO);
        myUserRepo.save(myUser);
        return true;
    }
    public UserProfileDTO getUserProfileById(int id){
        if(myUserRepo.existsByUserId(id)){
            return myUserRepo.getByUserId(id).getDTO();
        }
        return null;
    }
}
