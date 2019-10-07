package com.beehyv.case_study.security;

import com.beehyv.case_study.entities.MyUserCredentials;
import com.beehyv.case_study.repositories.MyUserCredentialsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    MyUserCredentialsRepo myUserCredentialsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MyUserCredentials> myUserCredentials = myUserCredentialsRepo.getByEmail(username);
        myUserCredentials.orElseThrow(() -> {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        });
        MyUserCredentials myUC = myUserCredentials.get();
        return new MyUserDetails(myUC);
    }
}
