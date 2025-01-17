package com.beehyv.case_study.services;

import com.beehyv.case_study.dto.SignUpDTO;
import com.beehyv.case_study.dto.UserProfileDTO;
import com.beehyv.case_study.entities.Cart;
import com.beehyv.case_study.entities.MyUser;
import com.beehyv.case_study.entities.MyUserCredentials;
import com.beehyv.case_study.repositories.CartRepo;
import com.beehyv.case_study.repositories.MyUserCredentialsRepo;
import com.beehyv.case_study.repositories.MyUserRepo;
import com.beehyv.case_study.security.MyUserDetails;
import com.beehyv.case_study.utilities.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserManager {

    @Autowired
    MyUserRepo myUserRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    MyUserCredentialsRepo myUserCredentialsRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean isAuthorized(long userId) throws UnauthorizedException {

        return userId == getLoggedInUserId();

    }

    public boolean isAdmin() throws UnauthorizedException {
        // Can also return getLoggedInUserId() == 1; Since ADMIN is the first created user in database.
        try {
            return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getAuthorities()
                    .stream()
                    .filter(o -> ((GrantedAuthority) o).getAuthority().equalsIgnoreCase("ADMIN"))
                    .collect(Collectors.toList())
                    .size() == 1;
        } catch (ClassCastException e) {
            throw new UnauthorizedException("User not logged in");
        }
    }

    public boolean existsById(long userId) {
        return myUserRepo.existsByUserId(userId);
    }

    public long addUser(SignUpDTO signUpDTO) {
        if (!signUpDTO.isValid()) {
            return -1;
        }
        if (myUserCredentialsRepo.existsByEmail(signUpDTO.getEmail())) {
            return -1;
        }

        MyUser tmp = new MyUser();
        tmp.setName(signUpDTO.getName());
        tmp.setEmail(signUpDTO.getEmail());
        tmp.setCart(cartRepo.save(new Cart()));
        tmp.setOrders(new ArrayList<>());
        myUserRepo.save(tmp);
        MyUserCredentials myUserCredentials = new MyUserCredentials();
        myUserCredentials.setEmail(signUpDTO.getEmail());
        myUserCredentials.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        myUserCredentials.setAuthorities("USER");
        myUserCredentials.setMyUser(tmp);
        myUserCredentialsRepo.save(myUserCredentials);
        return tmp.getUserId();
    }

    public long getLoggedInUserId() throws UnauthorizedException {

        try {
            return ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMyUserCredentials().getUserId();
        } catch (ClassCastException e) {
            throw new UnauthorizedException("User not logged in");
        }
    }

    public MyUser getUserById(long userId) {
        return myUserRepo.getByUserId(userId);
    }

    public boolean updateUser(UserProfileDTO userProfileDTO) throws UnauthorizedException {
        if (!isAuthorized(userProfileDTO.getUserId())) {
            return false;
        }
        MyUser myUser = getUserById(userProfileDTO.getUserId());
        myUser.setDTO(userProfileDTO);
        myUserRepo.save(myUser);
        return true;
    }

    public void updateUser(MyUser myUser) {
        myUserRepo.save(myUser);
    }

    public UserProfileDTO getUserProfileById(long userId) throws UnauthorizedException {
        if (isAuthorized(userId) && myUserRepo.existsByUserId(userId)) {
            return getUserById(userId).getDTO();
        }
        return null;
    }
}
