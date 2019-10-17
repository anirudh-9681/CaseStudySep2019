package com.beehyv.case_study.controller;


import com.beehyv.case_study.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageProvider {

    @Autowired
    UserManager userManager;

    @GetMapping("/")
    String getIndexPage() {
        return "index.html";
    }


    @GetMapping("/login")
    String getLoginPage() {
        return "loginPage.html";
    }

    @GetMapping("/signUp")
    String getSignUpPage() {
        return "signUpPage.html";
    }

    @GetMapping("/getProfile")
    String getProfilePage() {
        return "profile.html";
    }

    @GetMapping("/search")
    String getSearchPage() {
        return "searchPage.html";
    }
}
